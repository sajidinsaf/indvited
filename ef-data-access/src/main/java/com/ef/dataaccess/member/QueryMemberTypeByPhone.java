package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.member.MemberType;

@Component(value = "queryMemberTypeByPhone")
public class QueryMemberTypeByPhone implements Query<String, MemberType> {

  private final String SELECT_MEMBER = "select member_type_id from member where phone=?";

  private final JdbcTemplate jdbcTemplate;
  private final MemberTypeCache memberTypeCache;

  @Autowired
  public QueryMemberTypeByPhone(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      MemberTypeCache memberTypeCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.memberTypeCache = memberTypeCache;
  }

  @Override
  public MemberType data(String phone) {

    int memberTypeId = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { phone }, Integer.class);
    return memberTypeCache.getMemberType(memberTypeId);
  }

}
