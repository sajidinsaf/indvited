package com.ef.model.event;

import java.util.Calendar;

import com.google.gson.Gson;

public class PREventScheduleBindingModel {

  private int eventId;
  private String startDate, endDate, scheduleDate, scheduleTime;
  private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
  private boolean innerCircle, myBloggers, allEligible;
  private boolean allDay = true;
  private PREventTimeSlotBindingModel[] timeSlots;

  public PREventScheduleBindingModel() {
    // TODO Auto-generated constructor stub
  }

  public PREventScheduleBindingModel(int prEventId, String startDate, String endDate, boolean monday, boolean tuesday,
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

  public PREventScheduleBindingModel(int prEventId, String startDate, String endDate, boolean monday, boolean tuesday,
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

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int prEventId) {
    this.eventId = prEventId;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
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

  public boolean isWednesday() {
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
      sb.append(Calendar.SUNDAY);
    }
    if (monday) {
      sb.append(sb.toString().equals("") ? "" : ",").append(Calendar.MONDAY);
    }
    if (tuesday) {
      sb.append(sb.toString().equals("") ? "" : ",").append(Calendar.TUESDAY);
    }
    if (wednesday) {
      sb.append(sb.toString().equals("") ? "" : ",").append(Calendar.WEDNESDAY);
    }
    if (thursday) {
      sb.append(sb.toString().equals("") ? "" : ",").append(Calendar.THURSDAY);
    }
    if (friday) {
      sb.append(sb.toString().equals("") ? "" : ",").append(Calendar.FRIDAY);
    }
    if (saturday) {
      sb.append(sb.toString().equals("") ? "" : ",").append(Calendar.SATURDAY);
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

  public PREventTimeSlotBindingModel[] getTimeSlots() {
    if (allDay) {
      return new PREventTimeSlotBindingModel[] { new PREventTimeSlotBindingModel("0000", "2359") };
    }
    return timeSlots;
  }

  public static void main(String args[]) {
    PREventScheduleBindingModel a = new PREventScheduleBindingModel();
    a.setMonday(true);
    a.setTuesday(true);
    a.setWednesday(true);
    a.setThursday(true);
    a.setFriday(true);
    a.setSaturday(false);
    a.setSunday(false);

    a.setAllEligible(true);
    a.setInnerCircle(true);

    a.setAllDay(false);
    PREventTimeSlotBindingModel prEventTimeSlot1 = new PREventTimeSlotBindingModel();
    prEventTimeSlot1.setTimeFrom("1200");
    prEventTimeSlot1.setTimeTo("1600");

    PREventTimeSlotBindingModel prEventTimeSlot2 = new PREventTimeSlotBindingModel();
    prEventTimeSlot2.setTimeFrom("1800");
    prEventTimeSlot2.setTimeTo("2000");

    a.setTimeSlots(new PREventTimeSlotBindingModel[] { prEventTimeSlot1, prEventTimeSlot2 });
    a.setEventId(37);

    a.setStartDate("15/01/2020");
    a.setEndDate("22/01/2020");

    System.out.println(a.getScheduledDaysOfTheWeekString());
//      PREventTimeSlotBindingModel prEventTimeSlot1 = new PREventTimeSlotBindingModel();
//      prEventTimeSlot1.setEventDate("15/01/2021");
//      prEventTimeSlot1.setTimeFrom("1200");
//      prEventTimeSlot1.setTimeTo("1600");
    //
//      PREventTimeSlotBindingModel prEventTimeSlot2 = new PREventTimeSlotBindingModel();
//      prEventTimeSlot2.setEventDate("15/01/2021");
//      prEventTimeSlot2.setTimeFrom("1800");
//      prEventTimeSlot2.setTimeTo("2000");
    //

    System.out.println(new Gson().toJson(a));

  }

  public String getScheduleDate() {
    return scheduleDate;
  }

  public void setScheduleDate(String scheduleData) {
    this.scheduleDate = scheduleData;
  }

  public String getScheduleTime() {
    return scheduleTime;
  }

  public void setScheduleTime(String scheduleTime) {
    this.scheduleTime = scheduleTime;
  }

  public void setTimeSlots(PREventTimeSlotBindingModel[] timeSlots) {
    this.timeSlots = timeSlots;
  }

  public boolean isAllDay() {
    return allDay;
  }

  public void setAllDay(boolean allDay) {
    this.allDay = allDay;
  }

}
