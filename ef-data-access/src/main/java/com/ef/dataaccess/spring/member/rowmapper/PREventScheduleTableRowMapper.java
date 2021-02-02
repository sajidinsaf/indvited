package com.ef.dataaccess.spring.member.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.event.PREventSchedule;

public class PREventScheduleTableRowMapper implements RowMapper<PREventSchedule> {

  @Override
  public PREventSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {

//    PREventSchedule prEvent = new PREvent(rs.getInt("ID"), rs.getString("CAP"), rs.getString("NOTES"),
//        rs.getDate("CREATED_DATE"), rs.getInt("EVENT_TYPE_ID"), rs.getInt("DOMAIN_ID"), rs.getInt("EVENT_VENUE_ID"),
//        rs.getString("EXCLUSIONS"), rs.getString("MEMBER_EMAIL_ID"));
    return null;
  }

}
