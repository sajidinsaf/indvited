package com.ef.dataaccess.member.subscriber;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.member.MemberTypeCache;
import com.ef.model.event.PREventSchedule;
import com.ef.model.member.Member;
import com.ef.model.member.MemberCriteriaData;
import com.ef.model.member.MemberType;

@Component(value = "querySubscriberByCriteria")
public class QuerySubscriberByCriteria implements Query<PREventSchedule, List<Member>> {

  private final String SELECT_EVENT = "select * from member where member_type_id="
      + MemberType.KNOWN_MEMBER_TYPE_BLOGGER;

  private final JdbcTemplate jdbcTemplate;
  private final MemberTypeCache memberTypeCache;
  private final Query<Integer, List<MemberCriteriaData>> queryMemberCriteriaDataByMemberId;

  @Autowired
  public QuerySubscriberByCriteria(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      MemberTypeCache memberTypeCache,
      @Qualifier("queryMemberCriteriaDataByMemberId") Query<Integer, List<MemberCriteriaData>> queryMemberCriteriaDataByMemberId) {
    this.jdbcTemplate = jdbcTemplate;
    this.memberTypeCache = memberTypeCache;
    this.queryMemberCriteriaDataByMemberId = queryMemberCriteriaDataByMemberId;
  }

  @Override
  public List<Member> data(final PREventSchedule prEventSchedule) {

    List<Member> members = jdbcTemplate.query(String.format(SELECT_EVENT),
        (rs, rowNum) -> new Member(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
            rs.getString("EMAIL"), rs.getString("GENDER"), rs.getString("PHONE"),
            memberTypeCache.getMemberType(rs.getInt("MEMBER_TYPE_ID")), rs.getTimestamp("DATE_REGISTERED"),
            rs.getTimestamp("TIMESTAMP_OF_LAST_LOGIN"), rs.getBoolean("IS_ENABLED")));

    for (Member member : members) {
      member.setMemberCriteriaDataList(queryMemberCriteriaDataByMemberId.data(member.getId()));
    }

    return members;
  }

}
