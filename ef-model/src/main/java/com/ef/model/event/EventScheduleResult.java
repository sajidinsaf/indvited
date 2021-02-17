package com.ef.model.event;

import java.util.List;

public class EventScheduleResult {

  private final PREventSchedule schedule;
  private final long[] timeSlotIds;
  private final List<String> failureReasons;

  public EventScheduleResult(PREventSchedule schedule, long[] timeSlotIds) {
    this.schedule = schedule;
    this.timeSlotIds = timeSlotIds;
    failureReasons = null;
  }

  public EventScheduleResult(List<String> failureReasons) {
    this.failureReasons = failureReasons;
    schedule = null;
    timeSlotIds = new long[0];
  }

  public PREventSchedule getSchedule() {
    return schedule;
  }

  public List<String> getFailureReasons() {
    return failureReasons;
  }

  public long[] getTimeSlotIds() {
    return timeSlotIds;
  }

}
