package com.ef.dataaccess.event.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.Query;
import com.ef.dataaccess.event.util.EventEnricher;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;

@Component(value = "queryApprovalPendingSubscriptionsByPrId")
public class QueryApprovalPendingSubscriptionsByPrId implements Query<Integer, List<PREvent>> {

  private final Query<Integer, List<PREvent>> queryPREventList;

  private final EventEnricher eventEnricher;

  @Autowired
  public QueryApprovalPendingSubscriptionsByPrId(
      @Qualifier("queryPREventList") Query<Integer, List<PREvent>> queryPREventList, EventEnricher eventEnricher) {
    this.queryPREventList = queryPREventList;
    this.eventEnricher = eventEnricher;
  }

  @Override
  public List<PREvent> data(Integer prId) {

    List<PREvent> allPrEvents = queryPREventList.data(prId);

    Map<PREvent, TreeMap<PREventSchedule, Set<EventScheduleSubscription>>> validEvents = new HashMap<PREvent, TreeMap<PREventSchedule, Set<EventScheduleSubscription>>>();

    for (PREvent prEvent : allPrEvents) {

      // create a structure of events with schedules that have valid subscriptions
      // other events, schedules and subscriptions will be discarded

      for (PREventSchedule schedule : prEvent.getSchedules()) {
        List<EventScheduleSubscription> subscriptions = schedule.getSubscriptions();
        for (EventScheduleSubscription subscription : subscriptions) {
          if (subscription.getEventStatus().getId() == EventStatusMeta.KNOWN_STATUS_ID_APPLIED) {

            TreeMap<PREventSchedule, Set<EventScheduleSubscription>> eventScheduleMap = validEvents.get(prEvent);
            if (eventScheduleMap == null) {
              eventScheduleMap = new TreeMap<PREventSchedule, Set<EventScheduleSubscription>>();
            }

            Set<EventScheduleSubscription> subscriptionsSet = eventScheduleMap.get(schedule);

            if (subscriptionsSet == null) {
              subscriptionsSet = new TreeSet<EventScheduleSubscription>();
            }

            subscriptionsSet.add(subscription);
            eventScheduleMap.put(schedule, subscriptionsSet);
            validEvents.put(prEvent, eventScheduleMap);

          }
        }
      }
    }

    for (PREvent event : validEvents.keySet()) {

      List<PREventSchedule> schedulesForThisEvent = new ArrayList<PREventSchedule>(validEvents.get(event).keySet());

      for (PREventSchedule schedule : schedulesForThisEvent) {

        List<EventScheduleSubscription> subscriptionsForThisSchedule = new ArrayList<EventScheduleSubscription>(
            validEvents.get(event).get(schedule));

        schedule.setSubscriptions(subscriptionsForThisSchedule);

      }
      event.setSchedules(schedulesForThisEvent);

      eventEnricher.populateEventCriteria(event);

    }

    return new ArrayList<PREvent>(validEvents.keySet());
  }

}
