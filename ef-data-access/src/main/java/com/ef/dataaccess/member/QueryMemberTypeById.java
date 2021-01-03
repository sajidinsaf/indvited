package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.member.MemberType;

@Component(value = "queryMemberTypeById")
public class QueryMemberTypeById implements Query<Integer, MemberType> {

  private final String SELECT_MEMBER = "select member_type_id from member where id=?";

  private final JdbcTemplate jdbcTemplate;
  private final MemberTypeCache memberTypeCache;

  @Autowired
  public QueryMemberTypeById(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      MemberTypeCache memberTypeCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.memberTypeCache = memberTypeCache;
  }

  @Override
  public MemberType data(Integer id) {

    int memberTypeId = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { id }, Integer.class);
    return memberTypeCache.getMemberType(memberTypeId);
  }

}
