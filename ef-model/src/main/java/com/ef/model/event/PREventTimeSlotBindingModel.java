package com.ef.model.event;

public class PREventTimeSlotBindingModel {

  private String eventDate;
  private String timeFrom;
  private String timeTo;

  public PREventTimeSlotBindingModel() {

  }

  public PREventTimeSlotBindingModel(String eventDate, String timeFrom, String timeTo) {
    super();
    this.eventDate = eventDate;
    this.timeFrom = timeFrom;
    this.timeTo = timeTo;
  }

  public String getEventDate() {
    return eventDate;
  }

  public void setEventDate(String eventDateString) {
//    String[] dateParts = eventDateString.split("/");
//    int year = Integer.parseInt(dateParts[2]);
//    int month = Integer.parseInt(dateParts[1]);
//    int day = Integer.parseInt(dateParts[0]);
//
//    this.eventDate = new Date(year, month, day);

    eventDate = eventDateString;
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
    PREventTimeSlotBindingModel other = (PREventTimeSlotBindingModel) obj;
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
