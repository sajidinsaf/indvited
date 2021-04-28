package com.ef.dataaccess.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.spring.rowmapper.event.PREventTableRowMapper;
import com.ef.model.event.EventDeliverable;
import com.ef.model.event.EventVenue;
import com.ef.model.event.PREvent;

@Component(value = "queryEventById")
public class QueryEventById implements Query<Integer, PREvent> {

  private final String SELECT_EVENT = "select * from event where id=?";

  private final JdbcTemplate jdbcTemplate;
  private final EventTypeCache eventTypeCache;
  private final Query<Integer, EventVenue> queryVenue;
  private final Query<Integer, List<EventDeliverable>> queryEventDeliverableListByEventId;

  @Autowired
  public QueryEventById(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryEventVenueById") Query<Integer, EventVenue> queryVenue, EventTypeCache eventTypeCache,
      @Qualifier("queryEventDeliverableListByEventId") Query<Integer, List<EventDeliverable>> queryEventDeliverableListByEventId) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventTypeCache = eventTypeCache;
    this.queryVenue = queryVenue;
    this.queryEventDeliverableListByEventId = queryEventDeliverableListByEventId;
  }

  @Override
  public PREvent data(Integer id) {

    PREvent prEvent = null;
    try {
      prEvent = jdbcTemplate.queryForObject(SELECT_EVENT, new Object[] { id }, new PREventTableRowMapper());
    } catch (EmptyResultDataAccessException e) {
      EmptyResultDataAccessException e1 = new EmptyResultDataAccessException("No event found for id: " + id, 1);
      e1.initCause(e);
      throw e1;
    }
    prEvent.setEventType(eventTypeCache.getEventType(prEvent.getEventTypeId()));

    EventVenue eventVenue = queryVenue.data(prEvent.getEventVenueId());
    prEvent.setEventVenue(eventVenue);

    List<EventDeliverable> deliverables = queryEventDeliverableListByEventId.data(id);
    prEvent.setEventDeliverables(deliverables);

    return prEvent;
  }

}
