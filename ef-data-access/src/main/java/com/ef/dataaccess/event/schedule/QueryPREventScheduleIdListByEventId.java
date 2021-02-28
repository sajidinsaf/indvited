package com.ef.dataaccess.event.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;

@Component(value = "queryPREventScheduleIdListByEventId")
public class QueryPREventScheduleIdListByEventId implements Query<Integer, List<Long>> {

  private final String SELECT = "select id from event_schedule where event_id=%d";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryPREventScheduleIdListByEventId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Long> data(final Integer eventId) {

    List<Long> scheduleIds = jdbcTemplate.query(String.format(SELECT, eventId), (rs, rowNum) -> rs.getLong("ID"));

    return scheduleIds;
  }

}
