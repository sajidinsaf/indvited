package com.ef.dataaccess.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;

@Component("memberLoginService")
public class MemberLoginService implements Query<LoginCredentials, Boolean> {

  private final String SELECT_PASSWORD = "select password from member where username=?";

  private final PasswordEncoder encoder;
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public MemberLoginService(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate, PasswordEncoder encoder) {
    this.encoder = encoder;
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Boolean data(LoginCredentials credentials) {
    String encryptedPassword = null;
    try {
      encryptedPassword = jdbcTemplate.queryForObject(SELECT_PASSWORD, new Object[] { credentials.getUsername() },
          String.class);
    } catch (EmptyResultDataAccessException e) {
      return false;
    }
    return encoder.matches(credentials.getPassword(), encryptedPassword);
  }

}
