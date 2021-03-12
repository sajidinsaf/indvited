package com.ef.dataaccess.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.core.ForumCache;
import com.ef.dataaccess.event.EventCriteriaMetadataCache;
import com.ef.model.event.EventCriteriaMetadata;
import com.ef.model.member.MemberCriteriaData;

@Component(value = "queryMemberCriteriaDataByMemberId")
public class QueryMemberCriteriaDataByMemberId implements Query<Integer, List<MemberCriteriaData>> {

  private final String SELECT_MEMBER_CRITERIA_DATA = "select * from member_criteria_data where member_id=%d";

  private final JdbcTemplate jdbcTemplate;
  private final EventCriteriaMetadataCache eventCriteriaMetadataCache;
  private final ForumCache forumCache;

  @Autowired
  public QueryMemberCriteriaDataByMemberId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventCriteriaMetadataCache eventCriteriaMetadataCache, ForumCache forumCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventCriteriaMetadataCache = eventCriteriaMetadataCache;
    this.forumCache = forumCache;
  }

  @Override
  public List<MemberCriteriaData> data(Integer memberId) {

    List<MemberCriteriaData> memberCriteriaDataList = jdbcTemplate.query(
        String.format(SELECT_MEMBER_CRITERIA_DATA, memberId),
        (rs, rowNum) -> new MemberCriteriaData(rs.getInt("ID"), memberId,
            eventCriteriaMetadataCache.getEventCriteria(rs.getInt("criteria_meta_id")),
            rs.getInt("member_criteria_value")));

    for (MemberCriteriaData memberCriteriaData : memberCriteriaDataList) {
      EventCriteriaMetadata eventCriteriaMetadata = memberCriteriaData.getCriteriaMetadata();
      int forumId = eventCriteriaMetadata.getForumId();
      eventCriteriaMetadata.setForum(forumCache.getForum(forumId));
    }

    // sort based on the forum id before returning. This will allow the View to see
    // the criteria in the order of their forums
    memberCriteriaDataList
        .sort((MemberCriteriaData m1, MemberCriteriaData m2) -> ((Integer) m1.getCriteriaMetadata().getForumId())
            .compareTo((Integer) m2.getCriteriaMetadata().getForumId()));

    return memberCriteriaDataList;
  }

}
