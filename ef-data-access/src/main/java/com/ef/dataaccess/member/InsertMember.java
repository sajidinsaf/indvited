package com.ef.dataaccess.member;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;
import com.fasterxml.uuid.Generators;

@Component("insertMember")
public class InsertMember implements Insert<MemberRegistrationBindingModel, Member> {

  private final String INSERT_STATEMENT_MEMBER = "INSERT INTO member(firstname, lastname, username, password, email, phone, member_type_id) VALUES (?,?,?,?,?,?,?)";
  private final String INSERT_STATEMENT_MEMBER_CONTROL = "INSERT INTO member_login_control(member_email_id,token,expiry_timestamp) VALUES (?,?,?)";
  private final JdbcTemplate jdbcTemplate;
  private final Query<String, Member> queryMemberByEmail;
  private final MemberTypeCache memberTypeCache;
  private final PasswordEncoder encoder;
  private final Query<String, String> emailFormatterForDb;
  private final static int login_session_expiry_period_in_milliseconds = System
      .getProperty("ef.login.session.expiry.period.in.seconds") == null ? 1000 * 60 * 60 * 24 * 7 * 4
          : 1000 * Integer.parseInt(System.getProperty("ef.login.session.expiry.period.in.seconds"));

  @Autowired
  public InsertMember(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMemberByEmail") Query<String, Member> queryMemberByEmail,
      @Qualifier("emailFormatterForDb") Query<String, String> emailFormatterForDb, MemberTypeCache memberTypeCache,
      PasswordEncoder encoder) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberByEmail = queryMemberByEmail;
    this.memberTypeCache = memberTypeCache;
    this.encoder = encoder;
    this.emailFormatterForDb = emailFormatterForDb;
  }

  @Override
  public Member data(MemberRegistrationBindingModel input) {

    int memberTypeId = memberTypeCache.getMemberType(input.getMemberType()).getId();
    String password = encryptPassword(input.getPassword());

    String email = emailFormatterForDb.data(input.getEmail());
    jdbcTemplate.update(INSERT_STATEMENT_MEMBER, new Object[] { input.getFirstName(), input.getLastName(),
        input.getUsername(), password, email, input.getPhone(), memberTypeId });
    String member_login_control_token = Generators.timeBasedGenerator().generate().toString();

    long loginExpiryTime = System.currentTimeMillis() + login_session_expiry_period_in_milliseconds;
    jdbcTemplate.update(INSERT_STATEMENT_MEMBER_CONTROL,
        new Object[] { email, member_login_control_token, new Timestamp(loginExpiryTime) });

    Member member = queryMemberByEmail.data(input.getEmail());

    return member;
  }

  private String encryptPassword(String password) {

    return encoder.encode(password);
  }

}
