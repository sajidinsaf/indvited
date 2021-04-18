package com.ef.eventservice.controller.util;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.common.Context;
import com.ef.common.Strategy;
import com.ef.dataaccess.event.EventStatusMetaCache;
import com.ef.eventservice.publisher.EventServiceContext;
import com.ef.model.event.EventScheduleSubscription;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREventSchedule;
import com.ef.model.event.wrapper.PREventWrapper;

@Component(value = "prEventStatusStrategy")
public class PREventStatusStrategy implements Strategy<Context, Void> {

  private final EventStatusMetaCache eventStatusMetaCache;
  private final Set<EventStatusMeta> overRideStatus;

  @Autowired
  public PREventStatusStrategy(EventStatusMetaCache eventStatusMetaCache) {

    this.eventStatusMetaCache = eventStatusMetaCache;
    overRideStatus = new java.util.HashSet<EventStatusMeta>();
    overRideStatus.add(eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_CLOSED));
    overRideStatus.add(eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_DELIVERABLE_UPLOADED));
    overRideStatus.add(eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_APPROVED));
    overRideStatus.add(eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_APPLIED));
  }

  @Override
  public Void apply(Context context) {

    PREventWrapper prEvent = context.get(EventServiceContext.CURRENT_EVENT_WRAPPER);

    prEvent.setEventStatus(eventStatusMetaCache.getEventStatusMeta(EventStatusMeta.KNOWN_STATUS_ID_CREATED));

    List<PREventSchedule> schedules = prEvent.getSchedules();

    for (PREventSchedule schedule : schedules) {

      List<EventScheduleSubscription> subscriptions = schedule.getSubscriptions();

      if (subscriptions == null) {
        continue;
      }
      for (EventScheduleSubscription subscription : subscriptions) {

        if (overRideStatus.contains(subscription.getEventStatus())) {
          prEvent.setEventStatus(subscription.getEventStatus());
          return null;
        }
      }
    }
    return null;
  }

}
