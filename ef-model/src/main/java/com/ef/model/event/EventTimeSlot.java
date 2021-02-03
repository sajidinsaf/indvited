package com.ef.model.event;

public class EventTimeslot {

  private long id;
  private long eventScheduleId;
  private String timeFrom;
  private String timeTo;

  public EventTimeslot() {

  }

  public EventTimeslot(long id, long eventScheduleId, String timeFrom, String timeTo) {
    super();
    this.id = id;
    this.eventScheduleId = eventScheduleId;
    this.timeFrom = timeFrom;
    this.timeTo = timeTo;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public long getEventScheduleId() {
    return eventScheduleId;
  }

  public void setEventScheduleId(long eventScheduleId) {
    this.eventScheduleId = eventScheduleId;
  }

  @Override
  public String toString() {
    return "EventTimeslot [id=" + id + ", timeFrom=" + timeFrom + ", timeTo=" + timeTo + "]";
  }

}
