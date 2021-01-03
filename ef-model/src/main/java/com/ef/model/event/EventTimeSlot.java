package com.ef.model.event;

import java.sql.Date;

public class EventTimeSlot {

  private Date eventDate;
  private String timeFrom;
  private String timeTo;

  public EventTimeSlot(Date eventDate, String timeFrom, String timeTo) {
    super();
    this.eventDate = eventDate;
    this.timeFrom = timeFrom;
    this.timeTo = timeTo;
  }

  public Date getEventDate() {
    return eventDate;
  }

  public void setEventDate(Date eventDate) {
    this.eventDate = eventDate;
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
    return "EventTimeSlot [eventDate=" + eventDate + ", timeFrom=" + timeFrom + ", timeTo=" + timeTo + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
    result = prime * result + ((timeFrom == null) ? 0 : timeFrom.hashCode());
    result = prime * result + ((timeTo == null) ? 0 : timeTo.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    EventTimeSlot other = (EventTimeSlot) obj;
    if (eventDate == null) {
      if (other.eventDate != null)
        return false;
    } else if (!eventDate.equals(other.eventDate))
      return false;
    if (timeFrom == null) {
      if (other.timeFrom != null)
        return false;
    } else if (!timeFrom.equals(other.timeFrom))
      return false;
    if (timeTo == null) {
      if (other.timeTo != null)
        return false;
    } else if (!timeTo.equals(other.timeTo))
      return false;
    return true;
  }

}
