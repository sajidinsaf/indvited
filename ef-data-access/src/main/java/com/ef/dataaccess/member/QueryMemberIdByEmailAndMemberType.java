package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.member.MemberLoginBindingModel;

@Component(value = "queryMemberIdByEmailAndMemberType")
public class QueryMemberIdByEmailAndMemberType implements Query<MemberLoginBindingModel, Integer> {

  private final String SELECT_MEMBER = "select id from member where email=? and member_type_id=?";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryMemberIdByEmailAndMemberType(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Integer data(MemberLoginBindingModel loginModel) {
    int memberId = jdbcTemplate.queryForObject(SELECT_MEMBER,
        new Object[] { loginModel.getEmail(), loginModel.getMemberType().getId() }, Integer.class);
    return memberId;
  }

}
