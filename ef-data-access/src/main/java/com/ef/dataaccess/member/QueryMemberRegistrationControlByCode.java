package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberRegistrationControlRowMapper;
import com.ef.model.member.MemberRegistrationControlModel;

@Component(value = "queryMemberRegistrationControlByCode")
public class QueryMemberRegistrationControlByCode implements Query<String, MemberRegistrationControlModel> {

  private final String SELECT_MEMBER_REGISTRATION_CONTROL = "SELECT member_id, registration_code, expiry_timestamp, confirmation_timestamp FROM member_registration_control WHERE registration_code=?";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryMemberRegistrationControlByCode(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public MemberRegistrationControlModel data(String code) {
    MemberRegistrationControlModel memberRegistrationControl = null;
    try {
      memberRegistrationControl = jdbcTemplate.queryForObject(SELECT_MEMBER_REGISTRATION_CONTROL, new Object[] { code },
          new MemberRegistrationControlRowMapper());
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
    return memberRegistrationControl;
  }

}
