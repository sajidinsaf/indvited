package com.ef.dataaccess.event.schedule;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
import com.ef.model.event.wrapper.EventScheduleSubscriptionForPrApprovalWrapper;
import com.ef.model.event.wrapper.PREventScheduleForPrApprovalWrapper;

@Component(value = "queryApprovalPendingSubscriptionsByPrId")
public class QueryApprovalPendingSubscriptionsByPrId implements Query<Integer, List<PREvent>> {

  private final Query<Integer, List<PREvent>> queryPREventList;

  private final EventEnricher eventEnricher;

  private final List<Integer> applicableStatusIds = Arrays.asList(EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_UPLOADED);

  @Autowired
  public QueryApprovalPendingSubscriptionsByPrId(
      @Qualifier("queryPREventList") Query<Integer, List<PREvent>> queryPREventList, EventEnricher eventEnricher) {
    this.queryPREventList = queryPREventList;
    this.eventEnricher = eventEnricher;
  }

  @Override
  public List<PREvent> data(Integer prId) {

    List<PREvent> allPrEvents = queryPREventList.data(prId);

    Map<PREvent, Map<PREventSchedule, Map<EventScheduleSubscriptionForPrApprovalWrapper, EventScheduleSubscriptionForPrApprovalWrapper>>> validEvents = new HashMap<PREvent, Map<PREventSchedule, Map<EventScheduleSubscriptionForPrApprovalWrapper, EventScheduleSubscriptionForPrApprovalWrapper>>>();

    for (PREvent prEvent : allPrEvents) {

      // create a structure of events with schedules that have valid subscriptions
      // other events, schedules and subscriptions will be discarded

      for (PREventSchedule schedule : prEvent.getSchedules()) {
        List<EventScheduleSubscription> subscriptions = schedule.getSubscriptions();
        for (EventScheduleSubscription rawSubscription : subscriptions) {

          int statusId = rawSubscription.getEventStatus().getId();

          if ((statusId == EventStatusMeta.KNOWN_STATUS_ID_APPLIED && isAfterToday(rawSubscription.getScheduleDate()))
              || applicableStatusIds.contains(statusId)) {

            Map<PREventSchedule, Map<EventScheduleSubscriptionForPrApprovalWrapper, EventScheduleSubscriptionForPrApprovalWrapper>> eventScheduleMap = validEvents
                .get(prEvent);
            if (eventScheduleMap == null) {
              eventScheduleMap = new TreeMap<PREventSchedule, Map<EventScheduleSubscriptionForPrApprovalWrapper, EventScheduleSubscriptionForPrApprovalWrapper>>();
            }

            Map<EventScheduleSubscriptionForPrApprovalWrapper, EventScheduleSubscriptionForPrApprovalWrapper> subscriptionsSet = eventScheduleMap
                .get(schedule);

            if (subscriptionsSet == null) {

              subscriptionsSet = new HashMap<EventScheduleSubscriptionForPrApprovalWrapper, EventScheduleSubscriptionForPrApprovalWrapper>();
            }

            EventScheduleSubscriptionForPrApprovalWrapper subscriptionWrapper = new EventScheduleSubscriptionForPrApprovalWrapper(
                rawSubscription);

            boolean exists = false;
            if (subscriptionsSet.containsKey(subscriptionWrapper)) {
              exists = true;
              subscriptionWrapper = subscriptionsSet.remove(subscriptionWrapper);
            }

            if (exists) {
              subscriptionWrapper.addSubscription(rawSubscription);
            }

            subscriptionsSet.put(subscriptionWrapper, subscriptionWrapper);
            eventScheduleMap.put(schedule, subscriptionsSet);
            validEvents.put(prEvent, eventScheduleMap);

          }
        }
      }
    }

    for (PREvent event : validEvents.keySet()) {

      List<PREventSchedule> schedulesForThisEvent = new ArrayList<PREventSchedule>(validEvents.get(event).keySet());
      List<PREventSchedule> scheduleWrappersForThisEvent = new ArrayList<PREventSchedule>();
      for (PREventSchedule rawSchedule : schedulesForThisEvent) {

        Set<EventScheduleSubscription> sortedSubscriptions = new TreeSet<EventScheduleSubscription>(comparator());
        sortedSubscriptions.addAll(validEvents.get(event).get(rawSchedule).keySet());

        PREventScheduleForPrApprovalWrapper scheduleWrapper = new PREventScheduleForPrApprovalWrapper(rawSchedule);

        scheduleWrapper.setSubscriptions(new ArrayList<EventScheduleSubscription>(sortedSubscriptions));

        scheduleWrappersForThisEvent.add(scheduleWrapper);
      }
      event.setSchedules(scheduleWrappersForThisEvent);

      eventEnricher.populateEventCriteria(event);

    }

    return new ArrayList<PREvent>(validEvents.keySet());
  }

  private boolean isAfterToday(Date scheduleDate) {
    return scheduleDate.toLocalDate().isAfter(LocalDate.now());
  }

  private Comparator<EventScheduleSubscription> comparator() {
    // Compare the subscriptions by name and id
    Comparator<EventScheduleSubscription> subscriptionComparator = Comparator
        .comparing(EventScheduleSubscription::getId).thenComparing(EventScheduleSubscription::getSubscriberId);
    return subscriptionComparator;
  }

}
