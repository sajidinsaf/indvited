package com.ef.dataaccess.spring.member.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.member.MemberLoginControl;

public class MemberLoginControlRowMapper implements RowMapper<MemberLoginControl> {

  @Override
  public MemberLoginControl mapRow(ResultSet rs, int rowNum) throws SQLException {

    MemberLoginControl memberLoginControl = new MemberLoginControl(rs.getInt("member_id"), rs.getString("TOKEN"),
        rs.getTimestamp("CREATION_TIMESTAMP"), rs.getTimestamp("EXPIRY_TIMESTAMP"));
    return memberLoginControl;
  }

}
