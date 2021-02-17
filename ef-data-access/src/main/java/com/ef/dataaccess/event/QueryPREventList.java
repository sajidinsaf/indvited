package com.ef.dataaccess.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.event.EventVenue;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;

@Component(value = "queryPREventList")
public class QueryPREventList implements Query<Integer, List<PREvent>> {

  private final String SELECT_EVENT = "select * from event where member_id=%d";

  private final JdbcTemplate jdbcTemplate;
  private final Query<Integer, EventVenue> queryVenue;
  private final Query<Integer, List<PREventSchedule>> queryPREventScheduleListByEventId;

  @Autowired
  public QueryPREventList(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryEventVenueById") Query<Integer, EventVenue> queryVenue,
      @Qualifier("queryPREventScheduleListByEventId") Query<Integer, List<PREventSchedule>> queryPREventScheduleListByEventId) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryVenue = queryVenue;
    this.queryPREventScheduleListByEventId = queryPREventScheduleListByEventId;
  }

  @Override
  public List<PREvent> data(final Integer memberId) {

    List<PREvent> events = jdbcTemplate.query(String.format(SELECT_EVENT, memberId),
        (rs, rowNum) -> new PREvent(rs.getInt("ID"), rs.getString("CAP"), rs.getString("NOTES"),
            rs.getDate("CREATED_DATE"), rs.getInt("EVENT_TYPE_ID"), rs.getInt("DOMAIN_ID"), rs.getInt("EVENT_VENUE_ID"),
            rs.getString("EXCLUSIONS"), rs.getInt("MEMBER_ID")));

    for (PREvent event : events) {

      EventVenue eventVenue = queryVenue.data(event.getEventVenueId());
      event.setEventVenue(eventVenue);

      List<PREventSchedule> eventSchedules = queryPREventScheduleListByEventId.data(event.getId());

      event.setSchedules(eventSchedules);

    }

    return events;
  }

}
