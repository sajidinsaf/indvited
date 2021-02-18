package com.ef.eventservice.controller.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

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

}
