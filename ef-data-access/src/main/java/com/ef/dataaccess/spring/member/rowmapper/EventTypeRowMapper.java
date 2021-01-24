package com.ef.dataaccess.spring.member.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.event.EventType;

public class EventTypeRowMapper implements RowMapper<EventType> {

  @Override
  public EventType mapRow(ResultSet rs, int rowNum) throws SQLException {
    EventType eventType = new EventType(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("DOMAIN_ID"));
    return eventType;
  }

}
