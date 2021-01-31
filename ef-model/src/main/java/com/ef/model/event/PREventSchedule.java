package com.ef.model.event;

public class PREventSchedule extends PREventScheduleAllDayBindingModel {

  private PREventTimeSlotBindingModel[] prEventTimeSlots;

  public PREventSchedule() {

  }

  public PREventSchedule(PREventTimeSlotBindingModel[] prEventTimeSlots) {
    super();
    this.prEventTimeSlots = prEventTimeSlots;
  }

  public PREventTimeSlotBindingModel[] getPrEventTimeSlots() {
    return prEventTimeSlots;
  }

  public void setPrEventTimeSlots(PREventTimeSlotBindingModel[] prEventTimeSlots) {
    this.prEventTimeSlots = prEventTimeSlots;
  }

}
