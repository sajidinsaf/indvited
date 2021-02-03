package com.ef.dataaccess.event.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.event.EventTimeslot;

@Component(value = "queryEventScheduleTimeslots")
public class QueryEventScheduleTimeslots implements Query<Long, List<EventTimeslot>> {

  private final String SELECT_EVENT = "select * from event_schedule_timeslot where event_schedule_id=%d";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryEventScheduleTimeslots(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<EventTimeslot> data(final Long scheduleId) {

    List<EventTimeslot> eventTimeslots = jdbcTemplate.query(String.format(SELECT_EVENT, scheduleId),
        (rs, rowNum) -> new EventTimeslot(rs.getLong("ID"), rs.getLong("EVENT_SCHEDULE_ID"), rs.getString("START_TIME"),
            rs.getString("END_TIME")));

    return eventTimeslots;
  }

}
