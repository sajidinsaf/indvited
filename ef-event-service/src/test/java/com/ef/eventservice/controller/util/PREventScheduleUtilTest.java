package com.ef.eventservice.controller.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ef.model.event.PREvent;
import com.ef.model.event.PREventSchedule;

public class PREventScheduleUtilTest {

  @Test
  public void testGetAllScheduledDates() throws Exception {

    String startDateStr = "2021-02-15"; // Start date
    String endDateStr = "2021-02-21"; // End date

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Date startDate = new Date(sdf.parse(startDateStr).getTime());
    Date endDate = new Date(sdf.parse(endDateStr).getTime());

    PREventSchedule schedule = new PREventSchedule();
    schedule.setStartDate(startDate);
    schedule.setEndDate(endDate);
    schedule.setDaysOfTheWeek("1,3,5");

    List<java.util.Date> dates = new PREventScheduleUtil().getAllScheduledDates(schedule);

    assertThat(dates.size(), is(3));
    assertThat(sdf.format(dates.get(0)), is(startDateStr));
    assertThat(sdf.format(dates.get(1)), is("2021-02-17"));
    assertThat(sdf.format(dates.get(2)), is("2021-02-19"));
  }

  @Test
  public void testGetAllScheduledDatesForThreeWeeks() throws Exception {

    String startDateStr = "2021-02-15"; // Start date
    String endDateStr = "2021-03-07"; // End date

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Date startDate = new Date(sdf.parse(startDateStr).getTime());
    Date endDate = new Date(sdf.parse(endDateStr).getTime());

    PREventSchedule schedule = new PREventSchedule();
    schedule.setStartDate(startDate);
    schedule.setEndDate(endDate);
    schedule.setDaysOfTheWeek("2,4,7");

    List<java.util.Date> dates = new PREventScheduleUtil().getAllScheduledDates(schedule);

    assertThat(dates.size(), is(9));
    assertThat(sdf.format(dates.get(0)), is("2021-02-16"));
    assertThat(sdf.format(dates.get(1)), is("2021-02-18"));
    assertThat(sdf.format(dates.get(2)), is("2021-02-21"));
    assertThat(sdf.format(dates.get(3)), is("2021-02-23"));
    assertThat(sdf.format(dates.get(4)), is("2021-02-25"));
    assertThat(sdf.format(dates.get(5)), is("2021-02-28"));
    assertThat(sdf.format(dates.get(6)), is("2021-03-02"));
    assertThat(sdf.format(dates.get(7)), is("2021-03-04"));
    assertThat(sdf.format(dates.get(8)), is("2021-03-07"));
  }

  @Test
  public void shouldPopulateAllScheduledDatesForThreeWeeks() throws Exception {

    String startDateStr = "2021-02-15"; // Start date
    String endDateStr = "2021-03-07"; // End date

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Date startDate = new Date(sdf.parse(startDateStr).getTime());
    Date endDate = new Date(sdf.parse(endDateStr).getTime());

    PREventSchedule schedule = new PREventSchedule();
    schedule.setStartDate(startDate);
    schedule.setEndDate(endDate);
    schedule.setDaysOfTheWeek("2,4,7");

    PREvent prEvent = new PREvent();
    prEvent.setSchedules(Arrays.asList(schedule));

    new PREventScheduleUtil().populateAvailableDates(Arrays.asList(prEvent));

    List<java.util.Date> dates = prEvent.getSchedules().get(0).getAvailableDates();

    assertThat(dates.size(), is(9));
    assertThat(sdf.format(dates.get(0)), is("2021-02-16"));
    assertThat(sdf.format(dates.get(1)), is("2021-02-18"));
    assertThat(sdf.format(dates.get(2)), is("2021-02-21"));
    assertThat(sdf.format(dates.get(3)), is("2021-02-23"));
    assertThat(sdf.format(dates.get(4)), is("2021-02-25"));
    assertThat(sdf.format(dates.get(5)), is("2021-02-28"));
    assertThat(sdf.format(dates.get(6)), is("2021-03-02"));
    assertThat(sdf.format(dates.get(7)), is("2021-03-04"));
    assertThat(sdf.format(dates.get(8)), is("2021-03-07"));

    List<String> datesForDisplay = prEvent.getSchedules().get(0).getAvailableDatesForDisplay();

    assertThat(dates.size(), is(9));
    assertThat(datesForDisplay.get(0), is("Tue 16 Feb 2021"));
    assertThat(datesForDisplay.get(1), is("Thu 18 Feb 2021"));
    assertThat(datesForDisplay.get(2), is("Sun 21 Feb 2021"));
    assertThat(datesForDisplay.get(3), is("Tue 23 Feb 2021"));
    assertThat(datesForDisplay.get(4), is("Thu 25 Feb 2021"));
    assertThat(datesForDisplay.get(5), is("Sun 28 Feb 2021"));
    assertThat(datesForDisplay.get(6), is("Tue 2 Mar 2021"));
    assertThat(datesForDisplay.get(7), is("Thu 4 Mar 2021"));
    assertThat(datesForDisplay.get(8), is("Sun 7 Mar 2021"));

  }

}
