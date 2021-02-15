package com.ef.dataaccess.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.rowmapper.event.PREventTableRowMapper;
import com.ef.model.event.PREvent;

@Component(value = "queryEventById")
public class QueryEventById implements Query<Integer, PREvent> {

  private final String SELECT_EVENT = "select * from event where id=?";

  private final JdbcTemplate jdbcTemplate;
  private final EventTypeCache eventTypeCache;

  @Autowired
  public QueryEventById(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate, EventTypeCache eventTypeCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventTypeCache = eventTypeCache;
  }

  @Override
  public PREvent data(Integer id) {
    PREvent prEvent = jdbcTemplate.queryForObject(SELECT_EVENT, new Object[] { id }, new PREventTableRowMapper());
    prEvent.setEventType(eventTypeCache.getEventType(prEvent.getEventTypeId()));
    return prEvent;
  }

}
