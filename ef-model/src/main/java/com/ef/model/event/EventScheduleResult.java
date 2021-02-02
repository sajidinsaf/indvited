package com.ef.model.event;

import java.util.List;

public class EventScheduleResult {

  private final long scheduleId;
  private final long[] timeSlotIds;
  private final List<String> failureReasons;

  public EventScheduleResult(long scheduleId, long[] timeSlotIds) {
    this.scheduleId = scheduleId;
    this.timeSlotIds = timeSlotIds;
    failureReasons = null;
  }

  public EventScheduleResult(List<String> failureReasons) {
    this.failureReasons = failureReasons;
    scheduleId = -1;
    timeSlotIds = new long[0];
  }

  public long getScheduleId() {
    return scheduleId;
  }

  public List<String> getFailureReasons() {
    return failureReasons;
  }

  public long[] getTimeSlotIds() {
    return timeSlotIds;
  }

}
