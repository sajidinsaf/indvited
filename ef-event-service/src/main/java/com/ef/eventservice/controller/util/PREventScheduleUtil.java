package com.ef.eventservice.controller.util;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ef.common.Context;
import com.ef.common.LRPair;
import com.ef.common.Strategy;
import com.ef.dataaccess.Query;
import com.ef.eventservice.publisher.PREventPublisherContext;
import com.ef.model.event.AvailableScheduledDate;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;
import com.ef.model.event.wrapper.PREventScheduleForPrApprovalWrapper;
import com.ef.model.event.wrapper.PREventWrapper;

@Component
public class PREventScheduleUtil {

  private final Query<Pair<Long, int[]>, Integer> queryEventScheduleSubscriptionCountByScheduleIdAndStatusIds;
  private final Strategy<Context, Void> prEventStatusStrategy;

  @Autowired
  public PREventScheduleUtil(
      @Qualifier("queryEventScheduleSubscriptionCountByScheduleIdAndStatusIds") Query<Pair<Long, int[]>, Integer> queryEventScheduleSubscriptionCountByScheduleIdAndStatusIds,
      @Qualifier("prEventStatusStrategy") Strategy<Context, Void> prEventStatusStrategy) {
    this.queryEventScheduleSubscriptionCountByScheduleIdAndStatusIds = queryEventScheduleSubscriptionCountByScheduleIdAndStatusIds;
    this.prEventStatusStrategy = prEventStatusStrategy;
  }

  public List<PREvent> populateAvailableDates(List<PREvent> events) {

    List<PREvent> enrichedEvents = new ArrayList<PREvent>();

    for (PREvent event : events) {
      for (PREventSchedule schedule : event.getSchedules()) {
        schedule.setAvailableDates(getAllScheduledDates(schedule));
        setupWrapperDate(schedule);

        for (String date : schedule.getAvailableDatesForDisplay()) {
          event.addAvailableScheduledDatesForDisplay(new AvailableScheduledDate(date, schedule.getId()));
        }
      }

      PREventWrapper eventWrapper = new PREventWrapper(event);

      setStatus(eventWrapper);

      enrichedEvents.add(eventWrapper);
    }

    return enrichedEvents;
  }

  private void setStatus(PREventWrapper eventWrapper) {
    PREventPublisherContext context = new PREventPublisherContext();
    context.put(PREventPublisherContext.CURRENT_EVENT_WRAPPER, eventWrapper);
    prEventStatusStrategy.apply(context);

  }

  private void setupWrapperDate(PREventSchedule schedule) {
    if (schedule instanceof PREventScheduleForPrApprovalWrapper) {
      PREventScheduleForPrApprovalWrapper wrapper = (PREventScheduleForPrApprovalWrapper) schedule;
      int approvedSubscriptions = queryEventScheduleSubscriptionCountByScheduleIdAndStatusIds
          .data(new LRPair<Long, int[]>(schedule.getId(), new int[] { EventStatusMeta.KNOWN_STATUS_ID_APPROVED }));
      wrapper.setAvailableSubscriptions(wrapper.getTotalNumberOfSubscriptionForSchedule() - approvedSubscriptions);

    }
  }

  private List<java.util.Date> getAllScheduledDates(PREventSchedule schedule) {

    List<java.util.Date> dates = new ArrayList<java.util.Date>();

    Date thisDate = schedule.getStartDate();
    Date endDate = schedule.getEndDate();
    String daysOfTheWeek = schedule.getDaysOfTheWeek();
    while (thisDate.compareTo(endDate) <= 0) {
      if (isAfterToday(thisDate)) {
        String dayNumber = String.valueOf(thisDate.toLocalDate().getDayOfWeek().getValue());
        if (daysOfTheWeek.contains(dayNumber)) {
          dates.add(thisDate);
        }
      }
      thisDate = getNextDate(thisDate);
    }

    return dates;
  }

  private boolean isAfterToday(Date scheduleDate) {
    LocalDate today = LocalDate.now();
    LocalDate localScheduleDate = scheduleDate.toLocalDate();
    return localScheduleDate.isAfter(today);
  }

  private Date getNextDate(Date thisDate) {
    Calendar c = Calendar.getInstance();
    c.setTime(thisDate);
    c.add(Calendar.DATE, 1);
    return new Date(c.getTime().getTime());
  }
}
