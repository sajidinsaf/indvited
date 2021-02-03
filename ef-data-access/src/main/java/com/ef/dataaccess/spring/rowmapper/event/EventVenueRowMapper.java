package com.ef.dataaccess.spring.rowmapper.event;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.event.EventVenue;

public class EventVenueRowMapper implements RowMapper<EventVenue> {

  @Override
  public EventVenue mapRow(ResultSet rs, int rowNum) throws SQLException {

    EventVenue eventVenue = new EventVenue(rs.getInt("ID"), rs.getString("NAME"), rs.getString("ZOMATO_URL"),
        rs.getString("VENUE_URL"), rs.getString("ADDRESS"));
    return eventVenue;
  }

}
