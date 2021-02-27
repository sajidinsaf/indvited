package com.ef.dataaccess.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.event.EventCriteriaMetadataCache;
import com.ef.model.member.MemberCriteriaData;

@Component(value = "queryMemberCriteriaDataByMemberId")
public class QueryMemberCriteriaDataByMemberId implements Query<Integer, List<MemberCriteriaData>> {

  private final String SELECT_MEMBER_CRITERIA_DATA = "select * from member_criteria_data where member_id=%d";

  private final JdbcTemplate jdbcTemplate;
  private final EventCriteriaMetadataCache eventCriteriaMetadataCache;

  @Autowired
  public QueryMemberCriteriaDataByMemberId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventCriteriaMetadataCache eventCriteriaMetadataCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventCriteriaMetadataCache = eventCriteriaMetadataCache;

  }

  @Override
  public List<MemberCriteriaData> data(Integer memberId) {

    List<MemberCriteriaData> memberCriteriaDataList = jdbcTemplate.query(
        String.format(SELECT_MEMBER_CRITERIA_DATA, memberId),
        (rs, rowNum) -> new MemberCriteriaData(rs.getInt("ID"), memberId,
            eventCriteriaMetadataCache.getEventCriteria(rs.getInt("criteria_meta_id")),
            rs.getInt("member_criteria_value")));

    return memberCriteriaDataList;
  }

}
