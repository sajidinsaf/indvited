package com.ef.dataaccess.event.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.event.report.wrapper.EventScheduleSubscribersQueryResult;
import com.ef.model.event.report.EventScheduleSubscribers;
import com.ef.model.event.report.EventScheduleSubscribersQueryParameters;
import com.ef.model.event.report.MemberSubscription;

@Component(value = "queryEventScheduleSubsribersListByDate")
public class QueryEventScheduleSubsribersListByDate
    implements Query<EventScheduleSubscribersQueryParameters, List<EventScheduleSubscribers>> {

  private final String SELECT = "SELECT e.id as event_id, v.name as venue_name, v.address as venue_address, m.firstname as first_name, m.lastname last_name, m.phone as phone, ess.schedule_date as date, ess.preferred_time as time FROM event e, event_schedule es, event_schedule_subscription ess, venue v, member m WHERE ess.status_id=4 and ess.event_schedule_id = es.id and es.event_id = e.id and e.event_venue_id = v.id and m.id = ess.subscriber_id and e.member_id=%d and ess.schedule_date BETWEEN '%s' AND '%s'";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QueryEventScheduleSubsribersListByDate(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<EventScheduleSubscribers> data(final EventScheduleSubscribersQueryParameters params) {

    List<EventScheduleSubscribersQueryResult> result = jdbcTemplate.query(
        String.format(SELECT, params.getPrId(), params.getDateFrom(), params.getDateTo()),
        (rs, rowNum) -> new EventScheduleSubscribersQueryResult(rs.getInt("event_id"), rs.getString("venue_name"),
            rs.getString("venue_address"), params.getDateFrom(), params.getDateTo(), rs.getString("first_name"),
            rs.getString("last_name"), rs.getString("phone"), rs.getString("date"), rs.getString("time")));

    Map<Integer, EventScheduleSubscribers> eventScheduleSubscribersMap = new HashMap<Integer, EventScheduleSubscribers>();

    for (EventScheduleSubscribersQueryResult queryObject : result) {
      int eventId = queryObject.getEventId();
      EventScheduleSubscribers eventScheduleSubscribers = eventScheduleSubscribersMap.get(eventId);
      if (eventScheduleSubscribers == null) {
        eventScheduleSubscribers = new EventScheduleSubscribers(eventId, queryObject.getVenueName(),
            queryObject.getVenueAddress(), queryObject.getDateFrom(), queryObject.getDateTo());
      }
      MemberSubscription memberSubscription = new MemberSubscription(queryObject.getFirstName(),
          queryObject.getLastName(), queryObject.getPhone(), queryObject.getScheduleDate(),
          queryObject.getScheduleTime());
      eventScheduleSubscribers.add(memberSubscription);
      eventScheduleSubscribersMap.put(eventId, eventScheduleSubscribers);
    }

    return new ArrayList<EventScheduleSubscribers>(eventScheduleSubscribersMap.values());
  }

}
