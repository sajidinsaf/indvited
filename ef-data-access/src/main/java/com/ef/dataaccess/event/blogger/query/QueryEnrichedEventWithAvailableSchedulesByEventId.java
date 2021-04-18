package com.ef.dataaccess.event.blogger.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.event.util.EventEnricher;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;
import com.ef.model.member.Member;

@Component(value = "queryEnrichedEventWithAvailableSchedulesByEventId")
public class QueryEnrichedEventWithAvailableSchedulesByEventId implements Query<Integer, PREvent> {

  private final String SELECT = "SELECT es.* FROM event_schedule es WHERE es.event_id=%d AND es.start_date < DATE_ADD(now(), INTERVAL 30 DAY) AND es.end_date > DATE_ADD(NOW(), INTERVAL 1 DAY) ";

  private final JdbcTemplate jdbcTemplate;
  private final Query<Integer, PREvent> queryEventById;
  private final Query<Integer, Member> queryMemberById;
  private final EventEnricher eventEnricher;

  @Autowired
  public QueryEnrichedEventWithAvailableSchedulesByEventId(
      @Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryEventById") Query<Integer, PREvent> queryEventById,
      @Qualifier("queryMemberById") Query<Integer, Member> queryMemberById, EventEnricher eventEnricher) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryEventById = queryEventById;
    this.queryMemberById = queryMemberById;
    this.eventEnricher = eventEnricher;

  }

  @Override
  public PREvent data(final Integer eventId) {

    List<PREventSchedule> schedules = jdbcTemplate.query(String.format(SELECT, eventId),
        (rs, rowNum) -> new PREventSchedule(rs.getLong("ID"), rs.getInt("EVENT_ID"), rs.getDate("START_DATE"),
            rs.getDate("END_DATE"), rs.getString("DAYS_OF_THE_WEEK"), rs.getBoolean("PUBLISH_TO_INNER_CIRCLE"),
            rs.getBoolean("PUBLISH_TO_MY_BLOGGERS"), rs.getBoolean("PUBLISH_TO_ALL_ELIGIBLE"),
            rs.getTimestamp("CREATION_TIMESTAMP"), rs.getTimestamp("SCHEDULED_FOR_TIMESTAMP"),
            rs.getTimestamp("PUBLISHED_ON_TIMESTAMP"), rs.getInt("bloggers_per_day"), rs.getString("schedule_time")));

    PREvent event = queryEventById.data(eventId);

    event.setSchedules(schedules);

    Member pr = queryMemberById.data(event.getMemberId());
    event.setMember(pr);
    eventEnricher.populateEventCriteria(event);

    return event;
  }

}
