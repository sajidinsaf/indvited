package com.ef.dataaccess.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;
import com.ef.model.member.MemberRegistrationControlModel;
import com.ef.model.member.PreconfirmationMemberRegistrationModel;
import com.fasterxml.uuid.Generators;

@Component("insertMember")
public class InsertMember implements Insert<MemberRegistrationBindingModel, PreconfirmationMemberRegistrationModel> {
  private static final Logger logger = LoggerFactory.getLogger(InsertMember.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String INSERT_STATEMENT_MEMBER = "INSERT INTO member(firstname, lastname, password, email, gender, phone, member_type_id) VALUES (?,?,?,?,?,?,?)";
  private final String INSERT_STATEMENT_MEMBER_CONTROL = "INSERT INTO member_login_control(member_id,token,expiry_timestamp) VALUES (?,?,?)";
  private final JdbcTemplate jdbcTemplate;
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
      @Qualifier("queryMemberById") Query<Integer, Member> queryMemberById,
      @Qualifier("emailFormatterForDb") Query<String, String> emailFormatterForDb, MemberTypeCache memberTypeCache,
      PasswordEncoder encoder,
      @Qualifier("insertRegistrationConfirmationCode") Insert<Member, MemberRegistrationControlModel> insertRegistrationConfirmationCode) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberById = queryMemberById;
    this.memberTypeCache = memberTypeCache;
    this.encoder = encoder;
    this.emailFormatterForDb = emailFormatterForDb;
    this.insertRegistrationConfirmationCode = insertRegistrationConfirmationCode;
  }

  @Override
  public PreconfirmationMemberRegistrationModel data(MemberRegistrationBindingModel input) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      return getInsertEventPreparedStatement(input, connection);
    }, keyHolder);

    int memberId = (int) keyHolder.getKey();

    Member member = queryMemberById.data(memberId);

    logUtil.debug(logger, "Created member record ", member);
    long loginExpiryTime = System.currentTimeMillis() + login_session_expiry_period_in_milliseconds;
    String member_login_control_token = Generators.timeBasedGenerator().generate().toString();

    jdbcTemplate.update(INSERT_STATEMENT_MEMBER_CONTROL,
        new Object[] { memberId, member_login_control_token, new Timestamp(loginExpiryTime) });

    MemberRegistrationControlModel registrationConfirmationCode = insertRegistrationConfirmationCode.data(member);

    PreconfirmationMemberRegistrationModel pmrModel = new PreconfirmationMemberRegistrationModel(member,
        registrationConfirmationCode);
    logUtil.debug(logger, "Returning pre-confirmation registration information ", pmrModel);
    return pmrModel;
  }

  private String encryptPassword(String password) {

    return encoder.encode(password);
  }

  private final PreparedStatement getInsertEventPreparedStatement(MemberRegistrationBindingModel input,
      Connection connection) throws SQLException {

    int memberTypeId = memberTypeCache.getMemberType(input.getMemberType()).getId();
    String password = encryptPassword(input.getPassword());

    String email = emailFormatterForDb.data(input.getEmail());
    String gender = input.getGender().toUpperCase();

    PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT_MEMBER, Statement.RETURN_GENERATED_KEYS);
    ps.setString(1, input.getFirstName());
    ps.setString(2, input.getLastName());
    ps.setString(3, password);
    ps.setString(4, email);
    ps.setString(5, gender);
    ps.setString(6, input.getPhone());

    ps.setInt(7, memberTypeId);
    return ps;
  }

  public static void main(String args[]) {
    System.out.println(login_session_expiry_period_in_milliseconds);
    Timestamp t = new Timestamp(System.currentTimeMillis() + login_session_expiry_period_in_milliseconds);
    System.out.println(t.toString());
  }
}
