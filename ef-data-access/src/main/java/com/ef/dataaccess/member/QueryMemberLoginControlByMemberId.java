package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberLoginControlRowMapper;
import com.ef.model.member.MemberLoginControl;

@Component(value = "queryMemberLoginControlByMemberId")
public class QueryMemberLoginControlByMemberId implements Query<Integer, MemberLoginControl> {

  private final String SELECT_MEMBER_LOGIN_CONTROL = "select member_id, token, creation_timestamp, expiry_timestamp from member_login_control where member_id=?";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryMemberLoginControlByMemberId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public MemberLoginControl data(Integer memberId) {

    MemberLoginControl memberLoginControl = jdbcTemplate.queryForObject(SELECT_MEMBER_LOGIN_CONTROL,
        new Object[] { memberId }, new MemberLoginControlRowMapper());

    return memberLoginControl;
  }

}
