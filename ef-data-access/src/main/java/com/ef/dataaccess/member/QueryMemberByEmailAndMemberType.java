package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberRowMapper;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;

@Component(value = "queryMemberByEmailAndMemberType")
public class QueryMemberByEmailAndMemberType implements Query<MemberLoginBindingModel, Member> {

  private final String SELECT_MEMBER = "select * from member where email=? and member_type_id=?";

  private final JdbcTemplate jdbcTemplate;
  private final MemberTypeCache memberTypeCache;

  @Autowired
  public QueryMemberByEmailAndMemberType(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      MemberTypeCache memberTypeCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.memberTypeCache = memberTypeCache;
  }

  @Override
  public Member data(MemberLoginBindingModel loginModel) {
    Member member = jdbcTemplate.queryForObject(SELECT_MEMBER,
        new Object[] { loginModel.getEmail(), loginModel.getMemberType().getId() },
        new MemberRowMapper(memberTypeCache));
    return member;
  }

}
