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
import com.ef.model.member.MemberLoginBindingModel;
import com.ef.model.member.MemberRegistrationBindingModel;
import com.ef.model.member.MemberRegistrationControlModel;
import com.ef.model.member.MemberType;
import com.ef.model.member.PreconfirmationMemberRegistrationModel;
import com.fasterxml.uuid.Generators;

@Component("insertMember")
public class InsertMember implements Insert<MemberRegistrationBindingModel, PreconfirmationMemberRegistrationModel> {

  private final String INSERT_STATEMENT_MEMBER = "INSERT INTO member(firstname, lastname, password, email, gender, phone, member_type_id) VALUES (?,?,?,?,?,?,?)";
  private final String INSERT_STATEMENT_MEMBER_CONTROL = "INSERT INTO member_login_control(member_email_id,token,expiry_timestamp) VALUES (?,?,?)";
  private final JdbcTemplate jdbcTemplate;
  private final Query<MemberLoginBindingModel, Integer> queryMemberIdByEmailAndMemberType;
  private final Query<Integer, Member> queryMemberById;
  private final MemberTypeCache memberTypeCache;
  private final PasswordEncoder encoder;
  private final Query<String, String> emailFormatterForDb;
  private final Insert<Member, MemberRegistrationControlModel> insertRegistrationConfirmationCode;
  private final static long login_session_expiry_period_in_milliseconds = System
      .getProperty("ef.login.session.expiry.period.in.seconds") == null ? 2419200000L
          : 1000L * Long.parseLong(System.getProperty("ef.login.session.expiry.period.in.seconds"));

  @Autowired
  public InsertMember(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMemberIdByEmailAndMemberType") Query<MemberLoginBindingModel, Integer> queryMemberIdByEmailAndMemberType,
      @Qualifier("queryMemberById") Query<Integer, Member> queryMemberById,
      @Qualifier("emailFormatterForDb") Query<String, String> emailFormatterForDb, MemberTypeCache memberTypeCache,
      PasswordEncoder encoder,
      @Qualifier("insertRegistrationConfirmationCode") Insert<Member, MemberRegistrationControlModel> insertRegistrationConfirmationCode) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberIdByEmailAndMemberType = queryMemberIdByEmailAndMemberType;
    this.queryMemberById = queryMemberById;
    this.memberTypeCache = memberTypeCache;
    this.encoder = encoder;
    this.emailFormatterForDb = emailFormatterForDb;
    this.insertRegistrationConfirmationCode = insertRegistrationConfirmationCode;
  }

  @Override
  public PreconfirmationMemberRegistrationModel data(MemberRegistrationBindingModel input) {

    int memberTypeId = memberTypeCache.getMemberType(input.getMemberType()).getId();
    String password = encryptPassword(input.getPassword());

    String email = emailFormatterForDb.data(input.getEmail());
    String gender = input.getGender().toUpperCase();
    jdbcTemplate.update(INSERT_STATEMENT_MEMBER, new Object[] { input.getFirstName(), input.getLastName(), password,
        email, gender, input.getPhone(), memberTypeId });

    MemberType memberType = memberTypeCache.getMemberType(memberTypeId);

    int memberId = queryMemberIdByEmailAndMemberType.data(new MemberLoginBindingModel(email, "", memberType));

    Member member = queryMemberById.data(memberId);

    long loginExpiryTime = System.currentTimeMillis() + login_session_expiry_period_in_milliseconds;
    String member_login_control_token = Generators.timeBasedGenerator().generate().toString();

    jdbcTemplate.update(INSERT_STATEMENT_MEMBER_CONTROL,
        new Object[] { member.getEmail(), member_login_control_token, new Timestamp(loginExpiryTime) });

    MemberRegistrationControlModel registrationConfirmationCode = insertRegistrationConfirmationCode.data(member);

    PreconfirmationMemberRegistrationModel pmrModel = new PreconfirmationMemberRegistrationModel(member,
        registrationConfirmationCode);

    return pmrModel;
  }

  private String encryptPassword(String password) {

    return encoder.encode(password);
  }

  public static void main(String args[]) {
    System.out.println(login_session_expiry_period_in_milliseconds);
    Timestamp t = new Timestamp(System.currentTimeMillis() + login_session_expiry_period_in_milliseconds);
    System.out.println(t.toString());
  }
}
