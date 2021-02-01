package com.ef.model.event;

import java.util.List;

public class EventScheduleResult {

  private final long scheduleId;
  private final List<String> failureReasons;

  public EventScheduleResult(long scheduleId) {
    this.scheduleId = scheduleId;
    failureReasons = null;
  }

  public EventScheduleResult(List<String> failureReasons) {
    this.failureReasons = failureReasons;
    scheduleId = -1;
  }

  public long getScheduleId() {
    return scheduleId;
  }

  public List<String> getFailureReasons() {
    return failureReasons;
  }

}
