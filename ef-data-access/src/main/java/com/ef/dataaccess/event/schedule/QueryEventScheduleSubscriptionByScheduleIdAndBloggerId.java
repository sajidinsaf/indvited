package com.ef.dataaccess.event.schedule;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.event.EventStatusMetaCache;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;

@Component(value = "queryEventScheduleSubscriptionByScheduleIdAndBloggerId")
public class QueryEventScheduleSubscriptionByScheduleIdAndBloggerId
    implements Query<Pair<Integer, Long>, List<EventScheduleSubscription>> {

  private final String SELECT_EVENT = "select * from event_schedule_subscription where subscriber_id=%d and event_schedule_id=%d and status_id != %d";

  private final JdbcTemplate jdbcTemplate;
  private final EventStatusMetaCache eventStatusMetaCache;

  @Autowired
  public QueryEventScheduleSubscriptionByScheduleIdAndBloggerId(
      @Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate, EventStatusMetaCache eventStatusMetaCache) {
    this.jdbcTemplate = jdbcTemplate;
    this.eventStatusMetaCache = eventStatusMetaCache;
  }

  @Override
  public List<EventScheduleSubscription> data(final Pair<Integer, Long> bloggerIdAndscheduleIdPair) {

    int bloggerId = bloggerIdAndscheduleIdPair.getLeft();
    long scheduleId = bloggerIdAndscheduleIdPair.getRight();
    List<EventScheduleSubscription> eventScheduleSubscriptions = jdbcTemplate.query(
        String.format(SELECT_EVENT, bloggerId, scheduleId, EventStatusMeta.KNOWN_STATUS_ID_CLOSED),
        (rs, rowNum) -> new EventScheduleSubscription(rs.getLong("ID"), rs.getLong("EVENT_SCHEDULE_ID"),
            rs.getInt("SUBSCRIBER_ID"), rs.getDate("SCHEDULE_DATE"), rs.getString("PREFERRED_TIME"),
            eventStatusMetaCache.getEventStatusMeta(rs.getInt("STATUS_ID"))));

    return eventScheduleSubscriptions;
  }

}
