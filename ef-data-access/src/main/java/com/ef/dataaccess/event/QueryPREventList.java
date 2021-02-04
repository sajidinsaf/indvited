package com.ef.dataaccess.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.event.PREvent;

@Component(value = "queryPREventList")
public class QueryPREventList implements Query<Integer, List<PREvent>> {

  private final String SELECT_EVENT = "select * from event where member_id=%d";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryPREventList(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<PREvent> data(final Integer eventId) {

    List<PREvent> events = jdbcTemplate.query(String.format(SELECT_EVENT, eventId),
        (rs, rowNum) -> new PREvent(rs.getInt("ID"), rs.getString("CAP"), rs.getString("NOTES"),
            rs.getDate("CREATED_DATE"), rs.getInt("EVENT_TYPE_ID"), rs.getInt("DOMAIN_ID"), rs.getInt("EVENT_VENUE_ID"),
            rs.getString("EXCLUSIONS"), rs.getInt("MEMBER_ID")));

    return events;
  }

}
