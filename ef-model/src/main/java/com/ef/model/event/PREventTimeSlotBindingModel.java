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

}
