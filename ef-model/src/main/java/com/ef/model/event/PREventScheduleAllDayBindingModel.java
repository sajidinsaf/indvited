package com.ef.model.event;

import java.util.Date;

public class PREventScheduleAllDayBindingModel extends AbstractPREventScheduleBindingModel {

  public PREventScheduleAllDayBindingModel() {
    // TODO Auto-generated constructor stub
  }

  public PREventScheduleAllDayBindingModel(int prEventId, Date startDate, Date endDate, boolean monday, boolean tuesday,
      boolean wedenesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean innerCircle,
      boolean myBloggers, boolean allEligible) {
    super(prEventId, startDate, endDate, monday, tuesday, wedenesday, thursday, friday, saturday, sunday, innerCircle,
        myBloggers, allEligible);

  }

  @Override
  public PREventTimeSlotBindingModel[] getTimeSlots() {
    return new PREventTimeSlotBindingModel[] { new PREventTimeSlotBindingModel("0000", "2359") };
  }

}
