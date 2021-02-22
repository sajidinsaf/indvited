package com.ef.model.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AvailableScheduledDate implements Comparable<AvailableScheduledDate> {

  private String date;
  private Long scheduleId;

  public AvailableScheduledDate(String date, Long scheduleId) {
    super();
    this.date = date;
    this.scheduleId = scheduleId;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Long getScheduleId() {
    return scheduleId;
  }

  public void setScheduleId(Long scheduleId) {
    this.scheduleId = scheduleId;
  }

  @Override
  public String toString() {
    return "AvailableScheduledDate [date=" + date + ", scheduleId=" + scheduleId + "]";
  }

  @Override
  public int compareTo(AvailableScheduledDate o) {
    SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy");
    try {
      Date date1 = formatter.parse(date);
      Date date2 = formatter.parse(o.date);
      return date1.compareTo(date2);
    } catch (ParseException e) {
      throw new RuntimeException("Exception while parsing dates with format [EEE d MMM yyyy] for comparison. thisDate: "
          + date + " incomingDate: " + o.date, e);
    }

  }

}