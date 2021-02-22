package com.ef.eventservice.controller.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ef.model.event.AvailableScheduledDate;
import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;

@Component
public class PREventScheduleUtil {

  public void populateAvailableDates(List<PREvent> events) {
    for (PREvent event : events) {
      for (PREventSchedule schedule : event.getSchedules()) {
        schedule.setAvailableDates(getAllScheduledDates(schedule));
        for (String date : schedule.getAvailableDatesForDisplay()) {
          event.addAvailableScheduledDatesForDisplay(new AvailableScheduledDate(date, schedule.getId()));
        }
      }
    }
  }

  public List<java.util.Date> getAllScheduledDates(PREventSchedule schedule) {

    List<java.util.Date> dates = new ArrayList<java.util.Date>();

    Date thisDate = schedule.getStartDate();
    Date endDate = schedule.getEndDate();
    String daysOfTheWeek = schedule.getDaysOfTheWeek();
    while (thisDate.compareTo(endDate) <= 0) {
      String dayNumber = String.valueOf(thisDate.toLocalDate().getDayOfWeek().getValue());
      if (daysOfTheWeek.contains(dayNumber)) {
        dates.add(thisDate);
      }
      thisDate = getNextDate(thisDate);
    }

    return dates;
  }

  private Date getNextDate(Date thisDate) {
    Calendar c = Calendar.getInstance();
    c.setTime(thisDate);
    c.add(Calendar.DATE, 1);
    return new Date(c.getTime().getTime());
  }
}
