package com.ef.dataaccess.spring.rowmapper.event;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.event.EventStatusMeta;

public class EventStatusMetaRowMapper implements RowMapper<EventStatusMeta> {

  @Override
  public EventStatusMeta mapRow(ResultSet rs, int rowNum) throws SQLException {

    EventStatusMeta esm = new EventStatusMeta(rs.getInt("ID"), rs.getString("NAME"), rs.getString("DESCRIPTION"));
    return esm;
  }

}
