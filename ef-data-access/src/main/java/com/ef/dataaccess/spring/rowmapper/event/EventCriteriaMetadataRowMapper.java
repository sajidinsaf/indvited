package com.ef.dataaccess.spring.rowmapper.event;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ef.model.event.EventCriteriaMetadata;

public class EventCriteriaMetadataRowMapper implements RowMapper<EventCriteriaMetadata> {

  @Override
  public EventCriteriaMetadata mapRow(ResultSet rs, int rowNum) throws SQLException {

    EventCriteriaMetadata domain = new EventCriteriaMetadata(rs.getInt("ID"), rs.getString("CRITERION_NAME"),
        rs.getString("DESCRIPTION"));
    return domain;
  }

}
