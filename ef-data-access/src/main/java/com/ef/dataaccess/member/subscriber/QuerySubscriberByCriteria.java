package com.ef.dataaccess.member.subscriber;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.member.MemberTypeCache;
import com.ef.model.event.EventCriteria;
import com.ef.model.event.EventCriteriaMetadata;
import com.ef.model.member.Member;
import com.ef.model.member.MemberCriteriaData;

@Component(value = "querySubscriberByCriteria")
public class QuerySubscriberByCriteria implements Query<List<EventCriteria>, List<Member>> {

  private final String SELECT_EVENT = "select * from member";

  private final JdbcTemplate jdbcTemplate;
  private final MemberTypeCache memberTypeCache;
  private final Query<Pair<String, EventCriteriaMetadata>, MemberCriteriaData> queryMemberCriteriaDataById;

  @Autowired
  public QuerySubscriberByCriteria(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      MemberTypeCache memberTypeCache,
      @Qualifier("queryMemberCriteriaDataById") Query<Pair<String, EventCriteriaMetadata>, MemberCriteriaData> queryMemberCriteriaDataById) {
    this.jdbcTemplate = jdbcTemplate;
    this.memberTypeCache = memberTypeCache;
    this.queryMemberCriteriaDataById = queryMemberCriteriaDataById;
  }

  @Override
  public List<Member> data(final List<EventCriteria> eventCriteria) {

    List<Member> members = jdbcTemplate.query(String.format(SELECT_EVENT),
        (rs, rowNum) -> new Member(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
            rs.getString("EMAIL"), rs.getString("GENDER"), rs.getString("PHONE"),
            memberTypeCache.getMemberType(rs.getInt("MEMBER_TYPE_ID")), rs.getTimestamp("DATE_REGISTERED"),
            rs.getTimestamp("TIMESTAMP_OF_LAST_LOGIN"), rs.getBoolean("IS_ENABLED")));

    return members;
  }

}
