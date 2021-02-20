package com.ef.dataaccess.event.blogger.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.event.EventCriteriaData;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;
import com.ef.model.member.MemberCriteriaData;

@Component(value = "queryEligibleSchedulesByBloggerProfile")
public class QueryEligibleSchedulesByBloggerProfile implements Query<Integer, List<PREvent>> {

  private final String SELECT = "select * from event_schedule";

  private final JdbcTemplate jdbcTemplate;
  private final Query<Integer, List<MemberCriteriaData>> queryMemberCriteriaDataByMemberId;
  private final Query<Integer, Map<Integer, EventCriteriaData>> queryEventCriteriaDataByEventId;
  private final Query<Integer, PREvent> queryEventById;

  @Autowired
  public QueryEligibleSchedulesByBloggerProfile(@Qualifier("indvitedDbJdbcTemplate") JdbcTemplate jdbcTemplate,
      @Qualifier("queryMemberCriteriaDataByMemberId") Query<Integer, List<MemberCriteriaData>> queryMemberCriteriaDataByMemberId,
      @Qualifier("queryEventCriteriaDataByEventId") Query<Integer, Map<Integer, EventCriteriaData>> queryEventCriteriaDataByEventId,
      @Qualifier("queryEventById") Query<Integer, PREvent> queryEventById) {
    this.jdbcTemplate = jdbcTemplate;
    this.queryMemberCriteriaDataByMemberId = queryMemberCriteriaDataByMemberId;
    this.queryEventCriteriaDataByEventId = queryEventCriteriaDataByEventId;
    this.queryEventById = queryEventById;
  }

  @Override
  public List<PREvent> data(final Integer bloggerId) {

    List<MemberCriteriaData> bloggersCriteriaData = queryMemberCriteriaDataByMemberId.data(bloggerId);

    List<PREventSchedule> schedules = jdbcTemplate.query(String.format(SELECT),
        (rs, rowNum) -> new PREventSchedule(rs.getLong("ID"), rs.getInt("EVENT_ID"), rs.getDate("START_DATE"),
            rs.getDate("END_DATE"), rs.getString("DAYS_OF_THE_WEEK"), rs.getBoolean("PUBLISH_TO_INNER_CIRCLE"),
            rs.getBoolean("PUBLISH_TO_MY_BLOGGERS"), rs.getBoolean("PUBLISH_TO_ALL_ELIGIBLE"),
            rs.getTimestamp("CREATION_TIMESTAMP"), rs.getTimestamp("SCHEDULED_FOR_TIMESTAMP"),
            rs.getTimestamp("PUBLISHED_ON_TIMESTAMP"), rs.getInt("bloggers_per_day"), rs.getString("schedule_time")));

    Map<Integer, Map<Integer, EventCriteriaData>> eventIdToEventCriteriaMap = new HashMap<Integer, Map<Integer, EventCriteriaData>>();

    Set<Integer> uneligibleEventsSet = new TreeSet<Integer>();

    Map<Integer, PREvent> prEvents = new HashMap<Integer, PREvent>();

    for (PREventSchedule schedule : schedules) {
      int eventId = schedule.getEventId();

      if (uneligibleEventsSet.contains(eventId)) {
        continue;
      }

      Map<Integer, EventCriteriaData> eventCriteriaData = eventIdToEventCriteriaMap.get(eventId);

      if (eventCriteriaData == null) {
        eventCriteriaData = queryEventCriteriaDataByEventId.data(eventId);
        // Add this event's criteria to the map so that we don't have to query for it
        // again.
        eventIdToEventCriteriaMap.put(eventId, eventCriteriaData);
      }

      boolean eligible = true;
      for (MemberCriteriaData memberCriterion : bloggersCriteriaData) {
        EventCriteriaData eventCriterion = eventCriteriaData.get(memberCriterion.getCriteriaMetadata().getId());
        if (eventCriterion == null) {
          continue;
        }
        if (memberCriterion.getMemberCriteriaValue() < eventCriterion.getCriterionValue()) {
          eligible = false;
          // Add this event id to the uneligible set
          // so that we don't have to query and check event criteria for the same event
          // again.
          uneligibleEventsSet.add(eventId);
        }
      }

      if (eligible) {
        PREvent event = prEvents.get(schedule.getEventId());
        if (event == null) {
          event = queryEventById.data(schedule.getEventId());
        }
        event.addSchedule(schedule);
        prEvents.put(schedule.getEventId(), event);
      }

    }
    List<PREvent> events = new ArrayList<PREvent>(prEvents.values());
    return events;
  }

}