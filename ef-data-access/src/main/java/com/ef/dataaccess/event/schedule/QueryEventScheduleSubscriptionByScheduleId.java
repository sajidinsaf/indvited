package com.ef.dataaccess.event.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.event.EventStatusMetaCache;
import com.ef.model.event.EventScheduleSubscription;

@Component(value = "queryEventScheduleSubscriptionByScheduleId")
public class QueryEventScheduleSubscriptionByScheduleId implements Query<Long, List<EventScheduleSubscription>> {

  private final String SELECT_EVENT = "select * from event_schedule_subscription where event_schedule_id=%d";

  private final JdbcTemplate jdbcTemplate;
  private final EventStatusMetaCache eventStatusMetaCache;

  @Autowired
  public QueryEventScheduleSubscriptionByScheduleId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      EventStatusMetaCache eventStatusMetaCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventStatusMetaCache = eventStatusMetaCache;
  }

  @Override
  public List<EventScheduleSubscription> data(final Long scheduleId) {

    List<EventScheduleSubscription> eventScheduleSubscriptions = jdbcTemplate.query(
        String.format(SELECT_EVENT, scheduleId),
        (rs, rowNum) -> new EventScheduleSubscription(rs.getLong("ID"), rs.getLong("EVENT_SCHEDULE_ID"),
            rs.getInt("SUBSCRIBER_ID"), rs.getDate("SCHEDULE_DATE"), rs.getString("PREFERRED_TIME"),
            eventStatusMetaCache.getEventStatusMeta(rs.getInt("STATUS_ID"))));

    return eventScheduleSubscriptions;
  }

}
