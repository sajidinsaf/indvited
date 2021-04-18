package com.ef.dataaccess.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.Update;
import com.ef.dataaccess.spring.member.rowmapper.MemberRowMapperWithPassword;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;
import com.ef.model.member.MemberLoginControl;
import com.ef.model.member.MemberType;

@Component(value = "loginMember")
public class LoginMember implements Query<MemberLoginBindingModel, Member> {
  private static final Logger logger = LoggerFactory.getLogger(LoginMember.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String SELECT_MEMBER = "select id, firstname, password, lastname, email, gender, phone, date_registered, timestamp_of_last_login, is_enabled from member where email=? and member_type_id=?";

  private final JdbcTemplate jdbcTemplate;
  private final PasswordEncoder encoder;
  private final Query<String, String> emailFormatterForDb;
  private final Query<Integer, MemberLoginControl> queryLoginControlByEmail;
  private final Update<Integer, Member> updateMemberLoginControl;

  @Autowired
  public LoginMember(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("emailFormatterForDb") Query<String, String> emailFormatterForDb, PasswordEncoder encoder,
      @Qualifier("queryMemberLoginControlByMemberId") Query<Integer, MemberLoginControl> queryLoginControlByEmail,
      @Qualifier("updateMemberLoginControl") Update<Integer, Member> updateMemberLoginControl) {
    this.jdbcTemplate = jdbcTemplate;
    this.emailFormatterForDb = emailFormatterForDb;
    this.encoder = encoder;
    this.queryLoginControlByEmail = queryLoginControlByEmail;
    this.updateMemberLoginControl = updateMemberLoginControl;
  }

  @Override
  public Member data(MemberLoginBindingModel data) {

    MemberType memberType = null;
    String email = emailFormatterForDb.data(data.getEmail());

    memberType = data.getMemberType();

    Member member = jdbcTemplate.queryForObject(SELECT_MEMBER, new Object[] { email, memberType.getId() },
        new MemberRowMapperWithPassword(memberType, null));

    updateMemberLoginControl.data(member.getId());

    MemberLoginControl memberLoginControl = queryLoginControlByEmail.data(member.getId());

    member.setMemberLoginControl(memberLoginControl);

    if (!member.isEnabled()) {
      logUtil.debug(logger, "member is not enabled", data);
      return null;
    } else if (!encoder.matches(data.getPassword(), member.getPassword())) {
      logUtil.debug(logger, "password did not match", data);
      return null;
    }

    return member;
  }

}
