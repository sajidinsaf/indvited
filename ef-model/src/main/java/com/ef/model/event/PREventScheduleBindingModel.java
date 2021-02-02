package com.ef.model.event;

public class PREventScheduleBindingModel extends AbstractPREventScheduleBindingModel {

  private PREventTimeSlotBindingModel[] timeSlots;

  public PREventScheduleBindingModel() {

  }

  public PREventScheduleBindingModel(int prEventId, String startDate, String endDate, boolean monday, boolean tuesday,
      boolean wedenesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean innerCircle,
      boolean myBloggers, boolean allEligible) {
    super(prEventId, startDate, endDate, monday, tuesday, wedenesday, thursday, friday, saturday, sunday, innerCircle,
        myBloggers, allEligible);

  }

  public PREventScheduleBindingModel(int prEventId, String startDate, String endDate, boolean monday, boolean tuesday,
      boolean wedenesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean innerCircle,
      boolean myBloggers, boolean allEligible, String scheduleDate, String scheduleTime) {
    super(prEventId, startDate, endDate, monday, tuesday, wedenesday, thursday, friday, saturday, sunday, innerCircle,
        myBloggers, allEligible, scheduleDate, scheduleTime);

  }

  public PREventScheduleBindingModel(int prEventId, String startDate, String endDate, boolean monday, boolean tuesday,
      boolean wedenesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean innerCircle,
      boolean myBloggers, boolean allEligible, PREventTimeSlotBindingModel[] timeSlots) {
    super(prEventId, startDate, endDate, monday, tuesday, wedenesday, thursday, friday, saturday, sunday, innerCircle,
        myBloggers, allEligible);
    this.timeSlots = timeSlots;

  }

  public PREventScheduleBindingModel(int prEventId, String startDate, String endDate, boolean monday, boolean tuesday,
      boolean wedenesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean innerCircle,
      boolean myBloggers, boolean allEligible, String scheduleDate, String scheduleTime,
      PREventTimeSlotBindingModel[] timeSlots) {
    super(prEventId, startDate, endDate, monday, tuesday, wedenesday, thursday, friday, saturday, sunday, innerCircle,
        myBloggers, allEligible, scheduleDate, scheduleTime);
    this.timeSlots = timeSlots;
  }

  public void setTimeSlots(PREventTimeSlotBindingModel[] timeSlots) {
    this.timeSlots = timeSlots;
  }

  @Override
  public PREventTimeSlotBindingModel[] getTimeSlots() {

    return timeSlots;
  }

}
