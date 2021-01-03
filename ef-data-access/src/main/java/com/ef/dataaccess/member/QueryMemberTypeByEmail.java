package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.member.MemberType;

@Component(value = "queryMemberTypeByEmail")
public class QueryMemberTypeByEmail implements Query<String, MemberType> {

  private final String SELECT_MEMBER = "select member_type_id from member where email=?";

  private final JdbcTemplate jdbcTemplate;
  private final MemberTypeCache memberTypeCache;

  @Autowired
  public QueryMemberTypeByEmail(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      MemberTypeCache memberTypeCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.memberTypeCache = memberTypeCache;
  }

  @Override
  public MemberType data(String email) {

    int memberTypeId = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { email }, Integer.class);
    return memberTypeCache.getMemberType(memberTypeId);
  }

}
