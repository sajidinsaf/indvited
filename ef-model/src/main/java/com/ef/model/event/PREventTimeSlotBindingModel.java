package com.ef.model.event;

public class PREventTimeSlotBindingModel {

  private String timeFrom;
  private String timeTo;

  public PREventTimeSlotBindingModel() {

  }

  public PREventTimeSlotBindingModel(String timeFrom, String timeTo) {
    super();
    this.timeFrom = timeFrom;
    this.timeTo = timeTo;
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
