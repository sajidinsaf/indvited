package com.ef.model.event;

public class EventTimeSlot {

  private long timeSlotId;
  private String timeFrom;
  private String timeTo;

  public EventTimeSlot(long timeSlotId, String timeFrom, String timeTo) {
    super();
    this.timeSlotId = timeSlotId;
    this.timeFrom = timeFrom;
    this.timeTo = timeTo;
  }

  public long getTimeSlotId() {
    return timeSlotId;
  }

  public void setTimeSlotId(long timeSlotId) {
    this.timeSlotId = timeSlotId;
  }

  public String getTimeFrom() {
    return timeFrom;
  }

  public void setTimeFrom(String timeFrom) {
    this.timeFrom = timeFrom;
  }

  public String getTimeTo() {
    return timeTo;
  }

  public void setTimeTo(String timeTo) {
    this.timeTo = timeTo;
  }

  @Override
  public String toString() {
    return "EventTimeSlot [timeSlotId=" + timeSlotId + ", timeFrom=" + timeFrom + ", timeTo=" + timeTo + "]";
  }

}
