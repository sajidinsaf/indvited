package com.ef.dataaccess.member;

import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationControlModel;

@Component("confirmMemberRegistration")
public class ConfirmMember implements Insert<String, Member> {
  private static final Logger logger = LoggerFactory.getLogger(ConfirmMember.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String UPDATE_CONFIRMATION_STATUS = "UPDATE member SET is_enabled=true WHERE id=?";
  private final String UPDATE_CONFIRMATION_TIMESTAMP = "UPDATE member_registration_control SET confirmation_timestamp=? WHERE member_id=?";
  private final JdbcTemplate jdbcTemplate;
  private final Query<String, MemberRegistrationControlModel> queryMemberRegistrationControlByCode;
  private final Query<Integer, Member> queryMemberById;

  @Autowired
  public ConfirmMember(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMemberRegistrationControlByCode") Query<String, MemberRegistrationControlModel> queryMemberRegistrationControlByCode,
      @Qualifier("queryMemberById") Query<Integer, Member> queryMemberById) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberRegistrationControlByCode = queryMemberRegistrationControlByCode;
    this.queryMemberById = queryMemberById;
  }

  @Override
  public Member data(String input) {

    MemberRegistrationControlModel memberConfirmationInfo = queryMemberRegistrationControlByCode.data(input);

    if (memberConfirmationInfo == null) {
      logUtil.info(logger, "No valid entry found for confirmation code", input);
      return null;
    }

    if (memberConfirmationInfo.getExpiryTimestamp().getTime() < System.currentTimeMillis()) {
      logUtil.info(logger, "Confirmation code: ", input, " expired on ",
          new Date(memberConfirmationInfo.getExpiryTimestamp().getTime()));
      return null;
    }

    int memberId = memberConfirmationInfo.getMemberId();

    jdbcTemplate.update(UPDATE_CONFIRMATION_STATUS, new Object[] { memberId });

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    jdbcTemplate.update(UPDATE_CONFIRMATION_TIMESTAMP, new Object[] { timestamp, memberId });

    return queryMemberById.data(memberId);
  }

}
