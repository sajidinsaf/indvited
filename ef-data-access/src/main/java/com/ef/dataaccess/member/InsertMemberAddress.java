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
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.model.member.MemberAddress;
import com.ef.model.member.MemberAddressRegistrationBindingModel;

@Component("insertMemberAddress")
public class InsertMemberAddress implements Insert<MemberAddressRegistrationBindingModel, MemberAddress> {

  private static final Logger logger = LoggerFactory.getLogger(InsertMemberAddress.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final String INSERT_SCHEDULE_STATEMENT = "INSERT INTO member_address(member_id, addr_line1, addr_line2, addr_line3, city, country, pincode, creation_timestamp) VALUES (?,?,?,?,?,?,?,?)";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public InsertMemberAddress(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;

  }

  @Override
  public MemberAddress data(final MemberAddressRegistrationBindingModel input) {

    logUtil.debug(logger, " Input Schedule Details: ", input);

    Timestamp creationTimeStamp = new Timestamp(System.currentTimeMillis());

    KeyHolder keyHolder = new GeneratedKeyHolder();

    // Insert the PR Event schedule and get the schedule id.
    jdbcTemplate.update(connection -> {
      return getInsertAddressPreparedStatement(input, connection, creationTimeStamp);
    }, keyHolder);

    int addressId = keyHolder.getKey().intValue();

    MemberAddress memberAddress = new MemberAddress(addressId, input.getMemberId(), input.getAddressLine1(),
        input.getAddressLine2(), input.getAddressLine3(), input.getCity(), input.getCountry(), input.getPincode());

    return memberAddress;
  }

  private final PreparedStatement getInsertAddressPreparedStatement(MemberAddressRegistrationBindingModel input,
      Connection connection, Timestamp creationTimestamp) throws SQLException {

    PreparedStatement ps = connection.prepareStatement(INSERT_SCHEDULE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
    ps.setInt(1, input.getMemberId());
    ps.setString(2, input.getAddressLine1());
    ps.setString(3, input.getAddressLine2());
    ps.setString(4, input.getAddressLine3());
    ps.setString(5, input.getCity());
    ps.setString(6, input.getCountry());
    ps.setString(7, input.getPincode());
    ps.setTimestamp(8, creationTimestamp);

    return ps;
  }

}
