package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberLoginControlRowMapper;
import com.ef.model.member.MemberLoginControl;

@Component(value = "queryMemberLoginControlByEmail")
public class QueryMemberLoginControlByEmail implements Query<String, MemberLoginControl> {

  private final String SELECT_MEMBER_LOGIN_CONTROL = "select member_email_id, token, creation_timestamp, expiry_timestamp from member_login_control where member_email_id=?";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryMemberLoginControlByEmail(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public MemberLoginControl data(String email) {

    MemberLoginControl memberLoginControl = jdbcTemplate.queryForObject(SELECT_MEMBER_LOGIN_CONTROL,
        new Object[] { email }, new MemberLoginControlRowMapper());

    return memberLoginControl;
  }

}
