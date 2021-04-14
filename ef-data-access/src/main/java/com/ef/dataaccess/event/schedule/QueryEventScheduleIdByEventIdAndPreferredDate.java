package com.ef.dataaccess.event.schedule;

import java.sql.Date;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.common.util.DateUtil;
import com.ef.dataaccess.Query;

@Component(value = "queryEventScheduleIdByEventIdAndPreferredDate")
public class QueryEventScheduleIdByEventIdAndPreferredDate implements Query<Pair<Integer, Date>, Long> {

  private final String SELECT_EVENT = "select id from event_schedule es where es.event_id=%d and '%s' between es.start_date and es.end_date";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryEventScheduleIdByEventIdAndPreferredDate(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Long data(Pair<Integer, Date> eventIdAndPreferredDatePair) {

    int eventId = eventIdAndPreferredDatePair.getLeft();
    String preferredDate = new DateUtil().formatDate(eventIdAndPreferredDatePair.getRight(),
        DateUtil.yyyy_dash_MM_dash_dd_format);

    Long scheduleId = jdbcTemplate.queryForObject(String.format(SELECT_EVENT, eventId, preferredDate), Long.class);

    return scheduleId;
  }

}
