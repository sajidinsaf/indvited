package com.ef.dataaccess.spring.rowmapper.event.schedule;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.event.PREventSchedule;

public class PREventScheduleRowMapper implements RowMapper<PREventSchedule> {

  @Override
  public PREventSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {

    PREventSchedule prEventSchedule = new PREventSchedule(rs.getLong("ID"), rs.getInt("EVENT_ID"),
        rs.getDate("START_DATE"), rs.getDate("END_DATE"), rs.getString("DAYS_OF_THE_WEEK"),
        rs.getBoolean("PUBLISH_TO_INNER_CIRCLE"), rs.getBoolean("PUBLISH_TO_MY_BLOGGERS"),
        rs.getBoolean("PUBLISH_TO_ALL_ELIGIBLE"), rs.getTimestamp("CREATION_TIMESTAMP"),
        rs.getTimestamp("SCHEDULED_FOR_TIMESTAMP"), rs.getTimestamp("PUBLISHED_ON_TIMESTAMP"));
    return prEventSchedule;
  }

}
