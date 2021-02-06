package com.ef.dataaccess.event.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.event.EventTimeslot;
import com.ef.model.event.PREventSchedule;

@Component(value = "queryPREventScheduleListByEventId")
public class QueryPREventScheduleListByEventId implements Query<Integer, List<PREventSchedule>> {

  private final String SELECT = "select * from event_schedule where event_id=%d";

  private final JdbcTemplate jdbcTemplate;
  private final Query<Long, List<EventTimeslot>> queryEventScheduleTimeslotId;

  @Autowired
  public QueryPREventScheduleListByEventId(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryEventScheduleTimeslotsByScheduleId") Query<Long, List<EventTimeslot>> queryEventScheduleTimeslotId) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryEventScheduleTimeslotId = queryEventScheduleTimeslotId;
  }

  @Override
  public List<PREventSchedule> data(final Integer eventId) {

    List<PREventSchedule> schedules = jdbcTemplate.query(String.format(SELECT, eventId),
        (rs, rowNum) -> new PREventSchedule(rs.getLong("ID"), rs.getInt("EVENT_ID"), rs.getDate("START_DATE"),
            rs.getDate("END_DATE"), rs.getString("DAYS_OF_THE_WEEK"), rs.getBoolean("PUBLISH_TO_INNER_CIRCLE"),
            rs.getBoolean("PUBLISH_TO_MY_BLOGGERS"), rs.getBoolean("PUBLISH_TO_ALL_ELIGIBLE"),
            rs.getTimestamp("CREATION_TIMESTAMP"), rs.getTimestamp("SCHEDULED_FOR_TIMESTAMP"),
            rs.getTimestamp("PUBLISHED_ON_TIMESTAMP")));

    for (PREventSchedule schedule : schedules) {

      List<EventTimeslot> eventTimeslots = queryEventScheduleTimeslotId.data(schedule.getId());
      schedule.setEventTimeSlots(eventTimeslots);

    }
    return schedules;
  }

}
