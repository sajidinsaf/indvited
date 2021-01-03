package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.member.MemberType;

@Component(value = "queryMemberTypeByUsername")
public class QueryMemberTypeByUsername implements Query<String, MemberType> {

  private final String SELECT_MEMBER = "select member_type_id from member where username=?";

  private final JdbcTemplate jdbcTemplate;
  private final MemberTypeCache memberTypeCache;

  @Autowired
  public QueryMemberTypeByUsername(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      MemberTypeCache memberTypeCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.memberTypeCache = memberTypeCache;
  }

  @Override
  public MemberType data(String username) {

    int memberTypeId = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { username }, Integer.class);
    return memberTypeCache.getMemberType(memberTypeId);
  }

}
