package com.ef.dataaccess.member;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Insert;
import com.ef.dataaccess.common.UuidGenerator;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationControlModel;
import com.fasterxml.uuid.Generators;

@Component("insertRegistrationConfirmationCode")
public class InsertRegistrationConfirmationCode implements Insert<Member, MemberRegistrationControlModel> {

  private final String INSERT_STATEMENT_MEMBER_CONTROL = "INSERT INTO member_registration_control(member_id,registration_code,expiry_timestamp) VALUES (?,?,?)";
  private final JdbcTemplate jdbcTemplate;

  private final static long registration_code_expiry_period_in_milliseconds = System
      .getProperty("ef.login.session.expiry.period.in.seconds") == null ? 604800 * 1000L // days
          : 1000L * Long.parseLong(System.getProperty("ef.registration.code.expiry.period.in.seconds"));

  @Autowired
  public InsertRegistrationConfirmationCode(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("uuidGenerator") UuidGenerator uuidGenerator) {
    this.jdbcTemplate = jdbcTemplate;

  }

  @Override
  public MemberRegistrationControlModel data(Member input) {

    String memberRegistrationCode = Generators.timeBasedGenerator().generate().toString();

    int memberId = input.getId();

    long expiryTime = System.currentTimeMillis() + registration_code_expiry_period_in_milliseconds;
    Timestamp expiryTimestamp = new Timestamp(expiryTime);
    jdbcTemplate.update(INSERT_STATEMENT_MEMBER_CONTROL,
        new Object[] { memberId, memberRegistrationCode, expiryTimestamp });

    MemberRegistrationControlModel registrationConfirmationCode = new MemberRegistrationControlModel(memberId,
        memberRegistrationCode, expiryTimestamp, null);

    return registrationConfirmationCode;
  }

  public static void main(String args[]) {
    Timestamp t = new Timestamp(System.currentTimeMillis() + registration_code_expiry_period_in_milliseconds);
  }
}
