package com.ef.dataaccess.member;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberCriteriaDataRowMapper;
import com.ef.model.event.EventCriteriaMetadata;
import com.ef.model.event.MemberCriteriaData;

@Component(value = "queryMemberCriteriaDataById")
public class QueryMemberCriteriaDataById implements Query<Pair<String, EventCriteriaMetadata>, MemberCriteriaData> {

  private final String SELECT_MEMBER = "select id, member_id, criteria_meta_id, member_criteria_value from member_criteria_data where id=?";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryMemberCriteriaDataById(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;

  }

  @Override
  public MemberCriteriaData data(Pair<String, EventCriteriaMetadata> memberCriteriaIdAndEventCriteriaMetadataPair) {

    String memberCriteriaId = memberCriteriaIdAndEventCriteriaMetadataPair.getLeft();
    EventCriteriaMetadata eventCriteriaMetadata = memberCriteriaIdAndEventCriteriaMetadataPair.getRight();
    MemberCriteriaData memberCriteriaData = jdbcTemplate.queryForObject(SELECT_MEMBER,
        new Object[] { memberCriteriaId }, new MemberCriteriaDataRowMapper(eventCriteriaMetadata));
    return memberCriteriaData;
  }

}
