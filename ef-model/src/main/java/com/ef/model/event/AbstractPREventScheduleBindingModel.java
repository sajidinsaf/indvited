package com.ef.model.event;

import java.util.Calendar;
import java.util.Date;

public abstract class AbstractPREventScheduleBindingModel {

  private int eventId;
  private Date startDate, endDate;
  private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
  private boolean innerCircle, myBloggers, allEligible;

  public AbstractPREventScheduleBindingModel() {
    // TODO Auto-generated constructor stub
  }

  public AbstractPREventScheduleBindingModel(int prEventId, Date startDate, Date endDate, boolean monday,
      boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday,
      boolean innerCircle, boolean myBloggers, boolean allEligible) {
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

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int prEventId) {
    this.eventId = prEventId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public boolean isMonday() {
    return monday;
  }

  public void setMonday(boolean monday) {
    this.monday = monday;
  }

  public boolean isTuesday() {
    return tuesday;
  }

  public void setTuesday(boolean tuesday) {
    this.tuesday = tuesday;
  }

  public boolean isWedenesday() {
    return wednesday;
  }

  public void setWednesday(boolean wednesday) {
    this.wednesday = wednesday;
  }

  public boolean isThursday() {
    return thursday;
  }

  public void setThursday(boolean thursday) {
    this.thursday = thursday;
  }

  public boolean isFriday() {
    return friday;
  }

  public void setFriday(boolean friday) {
    this.friday = friday;
  }

  public boolean isSaturday() {
    return saturday;
  }

  public void setSaturday(boolean saturday) {
    this.saturday = saturday;
  }

  public boolean isSunday() {
    return sunday;
  }

  public void setSunday(boolean sunday) {
    this.sunday = sunday;
  }

  public String getScheduledDaysOfTheWeekString() {
    StringBuilder sb = new StringBuilder();
    if (sunday) {
      sb.append(Calendar.SUNDAY).append(",");
    }
    if (monday) {
      sb.append(Calendar.MONDAY).append(",");
    }
    if (tuesday) {
      sb.append(Calendar.TUESDAY).append(",");
    }
    if (wednesday) {
      sb.append(Calendar.WEDNESDAY).append(",");
    }
    if (thursday) {
      sb.append(Calendar.THURSDAY).append(",");
    }
    if (friday) {
      sb.append(Calendar.FRIDAY).append(",");
    }
    if (saturday) {
      sb.append(Calendar.SATURDAY);
    }
    return sb.toString();
  }

  public boolean isInnerCircle() {
    return innerCircle;
  }

  public void setInnerCircle(boolean innerCircle) {
    this.innerCircle = innerCircle;
  }

  public boolean isMyBloggers() {
    return myBloggers;
  }

  public void setMyBloggers(boolean myBloggers) {
    this.myBloggers = myBloggers;
  }

  public boolean isAllEligible() {
    return allEligible;
  }

  public void setAllEligible(boolean allEligible) {
    this.allEligible = allEligible;
  }

  @Override
  public String toString() {
    return "PREventScheduleAllDayBindingModel [prEventId=" + eventId + ", startDate=" + startDate + ", endDate="
        + endDate + ", monday=" + monday + ", tuesday=" + tuesday + ", wednesday=" + wednesday + ", thursday="
        + thursday + ", friday=" + friday + ", saturday=" + saturday + ", sunday=" + sunday + ", innerCircle="
        + innerCircle + ", myBloggers=" + myBloggers + ", allEligible=" + allEligible + "]";
  }

  public abstract PREventTimeSlotBindingModel[] getTimeSlots();

  public static void main(String args[]) {
    AbstractPREventScheduleBindingModel a = new AbstractPREventScheduleBindingModel() {

      @Override
      public PREventTimeSlotBindingModel[] getTimeSlots() {
        return null;
      }

    };

    a.setMonday(true);
    a.setTuesday(true);
    a.setWednesday(true);
    a.setThursday(true);
    a.setFriday(true);
    a.setSaturday(true);
    a.setSunday(true);

    System.out.println(a.getScheduledDaysOfTheWeekString());
  }
}
