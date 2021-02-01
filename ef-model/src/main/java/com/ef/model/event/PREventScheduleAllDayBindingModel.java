package com.ef.model.event;

public class PREventScheduleAllDayBindingModel extends AbstractPREventScheduleBindingModel {

  public PREventScheduleAllDayBindingModel() {
    // TODO Auto-generated constructor stub
  }

  public PREventScheduleAllDayBindingModel(int prEventId, String startDate, String endDate, boolean monday,
      boolean tuesday, boolean wedenesday, boolean thursday, boolean friday, boolean saturday, boolean sunday,
      boolean innerCircle, boolean myBloggers, boolean allEligible) {
    super(prEventId, startDate, endDate, monday, tuesday, wedenesday, thursday, friday, saturday, sunday, innerCircle,
        myBloggers, allEligible);

  }

  public PREventScheduleAllDayBindingModel(int prEventId, String startDate, String endDate, boolean monday,
      boolean tuesday, boolean wedenesday, boolean thursday, boolean friday, boolean saturday, boolean sunday,
      boolean innerCircle, boolean myBloggers, boolean allEligible, String scheduleDate, String scheduleTime) {
    super(prEventId, startDate, endDate, monday, tuesday, wedenesday, thursday, friday, saturday, sunday, innerCircle,
        myBloggers, allEligible, scheduleDate, scheduleTime);

  }

  @Override
  public PREventTimeSlotBindingModel[] getTimeSlots() {
    return new PREventTimeSlotBindingModel[] { new PREventTimeSlotBindingModel("0000", "2359") };
  }

}
