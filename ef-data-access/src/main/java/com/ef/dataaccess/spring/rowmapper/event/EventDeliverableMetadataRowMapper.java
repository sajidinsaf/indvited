package com.ef.dataaccess.spring.rowmapper.event;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.event.EventDeliverableMetadata;

public class EventDeliverableMetadataRowMapper implements RowMapper<EventDeliverableMetadata> {

  @Override
  public EventDeliverableMetadata mapRow(ResultSet rs, int rowNum) throws SQLException {

    EventDeliverableMetadata domain = new EventDeliverableMetadata(rs.getInt("ID"), rs.getString("NAME"),
        rs.getString("DESCRIPTION"));
    return domain;
  }

}
