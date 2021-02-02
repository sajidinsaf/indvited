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

  public PREventSchedule(int prEventId, String startDate, String endDate, boolean monday, boolean tuesday,
      boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean innerCircle,
      boolean myBloggers, boolean allEligible) {
    super();
    this.eventId = prEventId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.monday = monday;
    this.tuesday = tuesday;
    this.wednesday = wednesday;
    this.thursday = thursday;
    this.friday = friday;
    this.saturday = saturday;
    this.sunday = sunday;
    this.innerCircle = innerCircle;
    this.myBloggers = myBloggers;
    this.allEligible = allEligible;
  }

  public PREventSchedule(int prEventId, String startDate, String endDate, boolean monday, boolean tuesday,
      boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean innerCircle,
      boolean myBloggers, boolean allEligible, String scheduleDate, String scheduleTime) {
    super();
    this.eventId = prEventId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.monday = monday;
    this.tuesday = tuesday;
    this.wednesday = wednesday;
    this.thursday = thursday;
    this.friday = friday;
    this.saturday = saturday;
    this.sunday = sunday;
    this.innerCircle = innerCircle;
    this.myBloggers = myBloggers;
    this.allEligible = allEligible;
    this.scheduleDate = scheduleDate;
    this.scheduleTime = scheduleTime;
  }

  public EventTimeSlot[] getEventTimeSlots() {
    return eventTimeSlots;
  }

  public void setEventTimeSlots(EventTimeSlot[] eventTimeSlots) {
    this.eventTimeSlots = eventTimeSlots;
  }

}
