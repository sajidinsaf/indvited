package com.ef.model.event.wrapper;

import com.ef.model.event.PREventSchedule;

public class PREventScheduleForPrApprovalWrapper extends PREventSchedule {

  private int availableSubscriptions;

  public PREventScheduleForPrApprovalWrapper(PREventSchedule prEventSchedule) {
    super(prEventSchedule.getEventId(), prEventSchedule.getId(), prEventSchedule.getStartDate(),
        prEventSchedule.getEndDate(), prEventSchedule.getScheduleOnTime(), prEventSchedule.getCreationTimestamp(),
        prEventSchedule.getPublishedOnTimestamp(), prEventSchedule.isMonday(), prEventSchedule.isTuesday(),
        prEventSchedule.isWednesday(), prEventSchedule.isThursday(), prEventSchedule.isFriday(),
        prEventSchedule.isSaturday(), prEventSchedule.isSunday(), prEventSchedule.isInnerCircle(),
        prEventSchedule.isMyBloggers(), prEventSchedule.isAllEligible(), prEventSchedule.getBloggersPerDay(),
        prEventSchedule.getScheduleTimeInfo(), prEventSchedule.getDaysOfTheWeek(), prEventSchedule.getAvailableDates(),
        prEventSchedule.getAvailableDatesForDisplay(), prEventSchedule.getSubscriptions());

  }

  public int getAvailableSubscriptions() {
    return availableSubscriptions;
  }

  public void setAvailableSubscriptions(int availableSubscriptions) {
    this.availableSubscriptions = availableSubscriptions;
  }

  @Override
  public String toString() {
    return "PREventScheduleForPrApprovalWrapper [availableSubscriptions=" + availableSubscriptions
        + ", PREventSchedule()=" + super.toString() + "]";
  }

}
