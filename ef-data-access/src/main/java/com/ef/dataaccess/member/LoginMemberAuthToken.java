package com.ef.dataaccess.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.member.rowmapper.MemberRowMapperWithPassword;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginControl;
import com.ef.model.member.MemberTokenAuthBindingModel;
import com.ef.model.member.MemberType;

@Component(value = "loginMemberAuthToken")
public class LoginMemberAuthToken implements Query<MemberTokenAuthBindingModel, Member> {

  private static final Logger logger = LoggerFactory.getLogger(LoginMemberAuthToken.class);
  private ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String SELECT_MEMBER = "select id, firstname, password, lastname, username, email, gender, phone, date_registered, timestamp_of_last_login, is_enabled from member where email=? and member_type_id=?";

  private final JdbcTemplate jdbcTemplate;
  private final Query<String, String> emailFormatterForDb;
  private final Query<String, MemberLoginControl> queryLoginControlByEmail;

  @Autowired
  public LoginMemberAuthToken(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("emailFormatterForDb") Query<String, String> emailFormatterForDb,
      @Qualifier("queryMemberLoginControlByEmail") Query<String, MemberLoginControl> queryLoginControlByEmail) {
    this.jdbcTemplate = jdbcTemplate;
    this.emailFormatterForDb = emailFormatterForDb;
    this.queryLoginControlByEmail = queryLoginControlByEmail;

  }

  @Override
  public Member data(MemberTokenAuthBindingModel data) {

    MemberType memberType = null;
    String email = emailFormatterForDb.data(data.getEmail());

    memberType = data.getMemberType();

    MemberLoginControl memberLoginControl = null;

    try {
      memberLoginControl = queryLoginControlByEmail.data(email);
    } catch (EmptyResultDataAccessException e) {
      logUtil.info(logger, "No Member login control information found for email[", email,
          "] this should not have happened because MemberType was found for this member!");
      return null;
    }

    Member member = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { email, memberType.getId() },
        new MemberRowMapperWithPassword(memberType, memberLoginControl));

    return member;
  }

}
