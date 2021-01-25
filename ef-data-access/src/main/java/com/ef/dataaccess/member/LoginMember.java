package com.ef.dataaccess.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberRowMapperWithPassword;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;
import com.ef.model.member.MemberLoginControl;
import com.ef.model.member.MemberType;

@Component(value = "loginMember")
public class LoginMember implements Query<MemberLoginBindingModel, Member> {

  private final String SELECT_MEMBER = "select id, firstname, password, lastname, username, email, phone, date_registered, timestamp_of_last_login, is_enabled from member where email=?";

  private final JdbcTemplate jdbcTemplate;
  private final Query<String, MemberType> queryMemberTypeByEmail;
  private final PasswordEncoder encoder;
  private final Query<String, String> emailFormatterForDb;
  private final Query<String, MemberLoginControl> queryLoginControlByEmail;

  @Autowired
  public LoginMember(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMemberTypeByEmail") Query<String, MemberType> queryMemberTypeByEmail,
      @Qualifier("emailFormatterForDb") Query<String, String> emailFormatterForDb, PasswordEncoder encoder,
      @Qualifier("queryMemberLoginControlByEmail") Query<String, MemberLoginControl> queryLoginControlByEmail) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberTypeByEmail = queryMemberTypeByEmail;
    this.emailFormatterForDb = emailFormatterForDb;
    this.encoder = encoder;
    this.queryLoginControlByEmail = queryLoginControlByEmail;

  }

  @Override
  public Member data(MemberLoginBindingModel data) {

    MemberType memberType = null;
    String email = emailFormatterForDb.data(data.getEmail());

    try {
      memberType = queryMemberTypeByEmail.data(email);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }

    if (memberType == null) {
      return null;
    }
    MemberLoginControl memberLoginControl = queryLoginControlByEmail.data(email);

    Member member = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { email },
        new MemberRowMapperWithPassword(memberType, memberLoginControl));

    if (!member.isEnabled() || !encoder.matches(data.getPassword(), member.getPassword())) {
      return null;
    }

    return member;
  }

}
