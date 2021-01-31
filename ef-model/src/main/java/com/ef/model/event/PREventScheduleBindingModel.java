package com.ef.model.event;

public class PREventScheduleBindingModel extends PREventScheduleAllDayBindingModel {

  private PREventTimeSlotBindingModel[] timeSlots;

  public PREventScheduleBindingModel() {

  }

  public PREventScheduleBindingModel(PREventTimeSlotBindingModel[] timeSlots) {
    super();
    this.timeSlots = timeSlots;
  }

  public PREventTimeSlotBindingModel[] getPrEventTimeSlots() {
    return timeSlots;
  }

  public void setTimeSlots(PREventTimeSlotBindingModel[] timeSlots) {
    this.timeSlots = timeSlots;
  }

}
