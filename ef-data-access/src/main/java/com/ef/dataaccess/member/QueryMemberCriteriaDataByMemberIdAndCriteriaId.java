package com.ef.dataaccess.member;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.event.EventCriteriaMetadataCache;
import com.ef.model.member.MemberCriteriaData;

@Component(value = "queryMemberCriteriaDataByMemberIdAndCriteriaId")
public class QueryMemberCriteriaDataByMemberIdAndCriteriaId
    implements Query<Pair<Integer, Integer>, MemberCriteriaData> {

  private final String SELECT_MEMBER_CRITERIA_DATA = "select * from member_criteria_data where member_id=%d and criteria_meta_id=%d";

  private final JdbcTemplate jdbcTemplate;
  private final EventCriteriaMetadataCache eventCriteriaMetadataCache;

  @Autowired
  public QueryMemberCriteriaDataByMemberIdAndCriteriaId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventCriteriaMetadataCache eventCriteriaMetadataCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventCriteriaMetadataCache = eventCriteriaMetadataCache;

  }

  @Override
  public MemberCriteriaData data(Pair<Integer, Integer> memberIdAndCriteriaMetaId) {

    int memberId = memberIdAndCriteriaMetaId.getLeft();
    int criteriaMetaId = memberIdAndCriteriaMetaId.getRight();
    MemberCriteriaData memberCriteriaDataList = jdbcTemplate.queryForObject(
        String.format(SELECT_MEMBER_CRITERIA_DATA, memberId, criteriaMetaId),
        (rs, rowNum) -> new MemberCriteriaData(rs.getInt("ID"), memberId,
            eventCriteriaMetadataCache.getEventCriteria(criteriaMetaId), rs.getInt("member_criteria_value")));

    return memberCriteriaDataList;
  }

}
