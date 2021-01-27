package com.ef.dataaccess.spring.member.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.member.MemberRegistrationControlModel;

public class MemberRegistrationControlRowMapper implements RowMapper<MemberRegistrationControlModel> {

  @Override
  public MemberRegistrationControlModel mapRow(ResultSet rs, int rowNum) throws SQLException {

    MemberRegistrationControlModel memberRegistrationControlModel = new MemberRegistrationControlModel(
        rs.getInt("member_id"), rs.getString("registration_code"), rs.getTimestamp("expiry_timestamp"),
        rs.getTimestamp("confirmation_timestamp"));
    return memberRegistrationControlModel;
  }

}
