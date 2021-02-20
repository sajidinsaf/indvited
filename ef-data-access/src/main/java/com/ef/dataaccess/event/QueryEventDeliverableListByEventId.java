package com.ef.dataaccess.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.event.EventDeliverable;

@Component(value = "queryEventDeliverableListByEventId")
public class QueryEventDeliverableListByEventId implements Query<Integer, List<EventDeliverable>> {

  private final String SELECT_EVENT = "select * from event_deliverable_data where event_id=%d";

  private final JdbcTemplate jdbcTemplate;
  private final EventDeliverableMetadataCache eventDeliverableMetaCache;

  @Autowired
  public QueryEventDeliverableListByEventId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventDeliverableMetadataCache eventDeliverableMetaCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventDeliverableMetaCache = eventDeliverableMetaCache;
  }

  @Override
  public List<EventDeliverable> data(final Integer eventId) {

    List<EventDeliverable> deliverables = jdbcTemplate.query(String.format(SELECT_EVENT, eventId),
        (rs, rowNum) -> new EventDeliverable(eventId, rs.getInt("event_deliverable_id"),
            eventDeliverableMetaCache.getEventDeliverable(rs.getInt("event_deliverable_id")).getDeliverableName()));

    return deliverables;
  }

}
