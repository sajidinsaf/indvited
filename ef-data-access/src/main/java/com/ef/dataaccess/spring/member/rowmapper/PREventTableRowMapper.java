package com.ef.dataaccess.spring.member.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.event.PREvent;

public class PREventTableRowMapper implements RowMapper<PREvent> {

  @Override
  public PREvent mapRow(ResultSet rs, int rowNum) throws SQLException {

    PREvent prEvent = new PREvent(rs.getInt("ID"), rs.getString("CAP"), rs.getString("NOTES"),
        rs.getDate("CREATED_DATE"), rs.getInt("EVENT_TYPE_ID"), rs.getInt("DOMAIN_ID"), rs.getInt("EVENT_VENUE_ID"),
        rs.getString("EXCLUSIONS"), rs.getString("MEMBER_EMAIL_ID"));
    return prEvent;
  }

}
