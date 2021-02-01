package com.ef.model.event;

public class PREventSchedule {

  private int eventId;
  private long id;
  private String startDate, endDate, scheduleDate, scheduleTime;
  private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
  private boolean innerCircle, myBloggers, allEligible;

  private EventTimeSlot[] eventTimeSlots;

  public PREventSchedule() {

  }

  public PREventSchedule(long id, EventTimeSlot[] eventTimeSlots, AbstractPREventScheduleBindingModel bindingModel) {
    super();
    this.id = id;
    eventId = bindingModel.getEventId();

    this.eventTimeSlots = eventTimeSlots;
  }

  public EventTimeSlot[] getEventTimeSlots() {
    return eventTimeSlots;
  }

  public void setPrEventTimeSlots(EventTimeSlot[] eventTimeSlots) {
    this.eventTimeSlots = eventTimeSlots;
  }

}
