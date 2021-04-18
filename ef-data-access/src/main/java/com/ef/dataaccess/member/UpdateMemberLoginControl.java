package com.ef.dataaccess.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.Update;
import com.ef.model.member.Member;
import com.fasterxml.uuid.Generators;

@Component("updateMemberLoginControl")
public class UpdateMemberLoginControl implements Update<Integer, Member> {
  private static final Logger logger = LoggerFactory.getLogger(UpdateMemberLoginControl.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String UPDATE_MEMBER_CONTROL = "Update member_login_control set token=?, expiry_timestamp=? where member_id=?";
  private final JdbcTemplate jdbcTemplate;
  private final Query<Integer, Member> queryMemberById;

  private final static long login_session_expiry_period_in_milliseconds = System
      .getProperty("ef.login.session.expiry.period.in.seconds") == null ? 2419200000L
          : 1000L * Long.parseLong(System.getProperty("ef.login.session.expiry.period.in.seconds"));

  @Autowired
  public UpdateMemberLoginControl(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMemberById") Query<Integer, Member> queryMemberById) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberById = queryMemberById;

  }

  @Override
  public Member data(Integer memberId) {

    Member member = queryMemberById.data(memberId);

    if (member == null) {
      return null;
    }

    logUtil.debug(logger, "Retrieved member ", member);

    String token = Generators.timeBasedGenerator().generate().toString();

    jdbcTemplate.update(connection -> {
      return getUpdatePreparedStatement(memberId, token, connection);
    });

    return member;
  }

  private final PreparedStatement getUpdatePreparedStatement(int memberId, String loginToken, Connection connection)
      throws SQLException {

    PreparedStatement ps = connection.prepareStatement(UPDATE_MEMBER_CONTROL);
    ps.setString(1, loginToken);
    ps.setTimestamp(2, new Timestamp(System.currentTimeMillis() + login_session_expiry_period_in_milliseconds));
    ps.setInt(3, memberId);

    return ps;
  }

  public static void main(String args[]) {
    Timestamp t = new Timestamp(System.currentTimeMillis() + login_session_expiry_period_in_milliseconds);
    System.out.println(t);
    String token = Generators.timeBasedGenerator().generate().toString();
    System.out.println(token);

  }
}
