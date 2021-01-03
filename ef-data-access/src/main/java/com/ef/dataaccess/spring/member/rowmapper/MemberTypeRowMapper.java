package com.ef.dataaccess.spring.member.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.member.MemberType;

public class MemberTypeRowMapper implements RowMapper<MemberType> {

  @Override
  public MemberType mapRow(ResultSet rs, int rowNum) throws SQLException {

    MemberType memberType = new MemberType(rs.getInt("ID"), rs.getString("NAME"));
    return memberType;
  }

}
