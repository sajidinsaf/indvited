package com.ef.eventservice.controller.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;
import com.ef.model.event.PREventScheduleSubscriptionWeb;

@Component
public class PREventWebSubscriptionsUtil {

  private final Query<Integer, PREvent> queryEventById;
  private final Query<Long, PREventSchedule> queryEventScheduleById;
  private final Query<Integer, List<PREventScheduleSubscriptionWeb>> queryApprovalPendingSubscriptionsFromWeb;

  public PREventWebSubscriptionsUtil(@Qualifier("queryEventById") Query<Integer, PREvent> queryEventById,
      @Qualifier("queryEventScheduleById") Query<Long, PREventSchedule> queryEventScheduleById,
      @Qualifier("queryApprovalPendingSubscriptionsFromWeb") Query<Integer, List<PREventScheduleSubscriptionWeb>> queryApprovalPendingSubscriptionsFromWeb) {
    this.queryEventById = queryEventById;
    this.queryEventScheduleById = queryEventScheduleById;
    this.queryApprovalPendingSubscriptionsFromWeb = queryApprovalPendingSubscriptionsFromWeb;
  }

  public List<PREvent> populateWebEvents(List<PREvent> prEvents, int prId) {

    Map<Integer, PREvent> prEventsMap = asMap(prEvents);

    List<PREventScheduleSubscriptionWeb> webSubscriptions = queryApprovalPendingSubscriptionsFromWeb.data(prId);

    Map<Long, List<EventScheduleSubscription>> subscriptionsByScheduleIdMap = new HashMap<Long, List<EventScheduleSubscription>>();

    for (PREventScheduleSubscriptionWeb subscription : webSubscriptions) {
      long scheduleId = subscription.getEventScheduleId();
      List<EventScheduleSubscription> schedules = subscriptionsByScheduleIdMap.get(scheduleId);

      if (schedules == null) {
        schedules = new ArrayList<EventScheduleSubscription>();
      }

      schedules.add(subscription);

      subscriptionsByScheduleIdMap.put(scheduleId, schedules);

    }

    List<PREventSchedule> allEventSchedules = new ArrayList<PREventSchedule>();
    for (Long scheduleId : subscriptionsByScheduleIdMap.keySet()) {
      PREventSchedule schedule = queryEventScheduleById.data(scheduleId);

      if (schedule == null) {
        throw new RuntimeException("Schedule found null for schedule id: " + scheduleId
            + ". This should not have happened. Check data integrity");
      }
      schedule.setSubscriptions(subscriptionsByScheduleIdMap.get(scheduleId));
      allEventSchedules.add(schedule);
    }

    for (PREventSchedule schedule : allEventSchedules) {
      int eventId = schedule.getEventId();
      PREvent event = prEventsMap.get(eventId);

      if (event == null) {
        event = queryEventById.data(eventId);
      }
      event.addSchedule(schedule);
      prEventsMap.put(eventId, event);
    }
    return new ArrayList<PREvent>(prEventsMap.values());
  }

  private Map<Integer, PREvent> asMap(List<PREvent> prEvents) {
    Map<Integer, PREvent> map = new LinkedHashMap<Integer, PREvent>();
    for (PREvent prEvent : prEvents) {
      map.put(prEvent.getId(), prEvent);
    }
    return map;
  }

}
