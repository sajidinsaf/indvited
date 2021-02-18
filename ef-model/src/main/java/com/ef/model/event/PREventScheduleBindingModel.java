package com.ef.model.event;

import java.time.DayOfWeek;
import java.util.Arrays;

import com.google.gson.Gson;

public class PREventScheduleBindingModel {

  private static final int DEFAULT_BLOGGERS_PER_DAY = 1;
  private int eventId;
  private String startDate, endDate, scheduleOnDate, scheduleOnTime;
  private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
  private boolean innerCircle, myBloggers, allEligible;
  private boolean allDay = true;
  private String startTime1, startTime2, startTime3, startTime4, startTime5;
  private String endTime1, endTime2, endTime3, endTime4, endTime5;
  private int bloggersPerDay;
  private String scheduleTime;

  private PREventTimeSlotBindingModel[] timeSlots;

  public PREventScheduleBindingModel() {
    timeSlots = new PREventTimeSlotBindingModel[5];
  }

  public PREventScheduleBindingModel(int prEventId, String startDate, String endDate, boolean monday, boolean tuesday,
      boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean innerCircle,
      boolean myBloggers, boolean allEligible, String scheduleTime, int bloggersPerDay) {
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
    this.scheduleTime = scheduleTime;
    this.bloggersPerDay = bloggersPerDay;
    timeSlots = new PREventTimeSlotBindingModel[5];
  }

  public PREventScheduleBindingModel(int prEventId, String startDate, String endDate, boolean monday, boolean tuesday,
      boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean innerCircle,
      boolean myBloggers, boolean allEligible, String scheduleOnTime, String scheduleTime, int bloggersPerDay) {
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
    this.scheduleOnTime = scheduleOnTime;
    this.scheduleTime = scheduleTime;
    this.bloggersPerDay = bloggersPerDay;
    timeSlots = new PREventTimeSlotBindingModel[5];

  }

  public PREventScheduleBindingModel(int prEventId, String startDate, String endDate, boolean monday, boolean tuesday,
      boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean innerCircle,
      boolean myBloggers, boolean allEligible, String scheduleOnDate, String scheduleOnTime) {
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
    this.scheduleOnDate = scheduleOnDate;
    this.scheduleOnTime = scheduleOnTime;
    timeSlots = new PREventTimeSlotBindingModel[5];
  }

  public PREventScheduleBindingModel(int eventId, String startDate, String endDate, String scheduleDate,
      String scheduleTime, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday,
      boolean saturday, boolean sunday, boolean innerCircle, boolean myBloggers, boolean allEligible, boolean allDay,
      String startTime1, String startTime2, String startTime3, String startTime4, String startTime5, String endTime1,
      String endTime2, String endTime3, String endTime4, String endTime5) {
    super();
    this.eventId = eventId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.scheduleOnDate = scheduleDate;
    this.scheduleOnTime = scheduleTime;
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
    this.allDay = allDay;

    timeSlots = new PREventTimeSlotBindingModel[5];

    setStartTime1(startTime1);
    setStartTime2(startTime2);
    setStartTime3(startTime3);
    setStartTime4(startTime4);
    setStartTime5(startTime5);
    setEndTime1(endTime1);
    setEndTime2(endTime2);
    setEndTime3(endTime3);
    setEndTime4(endTime4);
    setEndTime5(endTime5);
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
      sb.append(DayOfWeek.SUNDAY.getValue());
    }
    if (monday) {
      sb.append(sb.toString().equals("") ? "" : ",").append(DayOfWeek.MONDAY.getValue());
    }
    if (tuesday) {
      sb.append(sb.toString().equals("") ? "" : ",").append(DayOfWeek.TUESDAY.getValue());
    }
    if (wednesday) {
      sb.append(sb.toString().equals("") ? "" : ",").append(DayOfWeek.WEDNESDAY.getValue());
    }
    if (thursday) {
      sb.append(sb.toString().equals("") ? "" : ",").append(DayOfWeek.THURSDAY.getValue());
    }
    if (friday) {
      sb.append(sb.toString().equals("") ? "" : ",").append(DayOfWeek.FRIDAY.getValue());
    }
    if (saturday) {
      sb.append(sb.toString().equals("") ? "" : ",").append(DayOfWeek.SATURDAY.getValue());
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

  public String getScheduleOnDate() {
    return scheduleOnDate;
  }

  public void setScheduleOnDate(String scheduleOnDate) {
    this.scheduleOnDate = scheduleOnDate;
  }

  public String getScheduleOnTime() {
    return scheduleOnTime;
  }

  public void setScheduleOnTime(String scheduleOnTime) {
    this.scheduleOnTime = scheduleOnTime;
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

  public String getStartTime1() {
    return startTime1;
  }

  public void setStartTime1(String startTime) {
    setStartTime(0, startTime);
    this.startTime1 = startTime;
  }

  public String getStartTime2() {
    return startTime2;
  }

  public void setStartTime2(String startTime) {
    setStartTime(1, startTime);
    this.startTime2 = startTime;
  }

  public String getStartTime3() {
    return startTime3;
  }

  public void setStartTime3(String startTime) {
    setStartTime(2, startTime);
    this.startTime3 = startTime;
  }

  public String getStartTime4() {
    return startTime4;
  }

  public void setStartTime4(String startTime) {
    setStartTime(3, startTime);
    this.startTime4 = startTime;
  }

  public String getStartTime5() {
    return startTime5;
  }

  public void setStartTime5(String startTime) {
    setStartTime(4, startTime);
    this.startTime5 = startTime;
  }

  public String getEndTime1() {
    return endTime1;
  }

  public void setEndTime1(String endTime) {
    setEndTime(0, endTime);
    this.endTime1 = endTime;
  }

  public String getEndTime2() {
    return endTime2;
  }

  public void setEndTime2(String endTime) {
    setEndTime(1, endTime);
    this.endTime2 = endTime;
  }

  public String getEndTime3() {
    return endTime3;
  }

  public void setEndTime3(String endTime) {
    setEndTime(2, endTime);
    this.endTime3 = endTime;
  }

  public String getEndTime4() {
    return endTime4;
  }

  public void setEndTime4(String endTime) {
    setEndTime(3, endTime);
    this.endTime4 = endTime;
  }

  public String getEndTime5() {
    return endTime5;
  }

  public void setEndTime5(String endTime) {
    setEndTime(4, endTime);
    this.endTime5 = endTime;
  }

  public void setStartTime(int index, String startTime) {
    PREventTimeSlotBindingModel petsbm = getPREventTimeSlotBindingModel(index);
    petsbm.setTimeFrom(startTime.replaceAll(":", ""));
  }

  public void setEndTime(int index, String endTime) {
    PREventTimeSlotBindingModel petsbm = getPREventTimeSlotBindingModel(index);
    petsbm.setTimeTo(endTime.replaceAll(":", ""));
  }

  private PREventTimeSlotBindingModel getPREventTimeSlotBindingModel(int index) {
    PREventTimeSlotBindingModel petsbm = timeSlots[index] != null ? timeSlots[index]
        : new PREventTimeSlotBindingModel();
    return petsbm;

  }

  public int getBloggersPerDay() {
    return bloggersPerDay < 1 ? DEFAULT_BLOGGERS_PER_DAY : bloggersPerDay;
  }

  public void setBloggersPerDay(int bloggersPerDay) {
    this.bloggersPerDay = bloggersPerDay;
  }

  public String getScheduleTime() {
    return scheduleTime;
  }

  public void setScheduleTime(String scheduleTime) {
    this.scheduleTime = scheduleTime;
  }

  @Override
  public String toString() {
    return "PREventScheduleBindingModel [eventId=" + eventId + ", startDate=" + startDate + ", endDate=" + endDate
        + ", scheduleOnDate=" + scheduleOnDate + ", scheduleOnTime=" + scheduleOnTime + ", monday=" + monday
        + ", tuesday=" + tuesday + ", wednesday=" + wednesday + ", thursday=" + thursday + ", friday=" + friday
        + ", saturday=" + saturday + ", sunday=" + sunday + ", innerCircle=" + innerCircle + ", myBloggers="
        + myBloggers + ", allEligible=" + allEligible + ", allDay=" + allDay + ", startTime1=" + startTime1
        + ", startTime2=" + startTime2 + ", startTime3=" + startTime3 + ", startTime4=" + startTime4 + ", startTime5="
        + startTime5 + ", endTime1=" + endTime1 + ", endTime2=" + endTime2 + ", endTime3=" + endTime3 + ", endTime4="
        + endTime4 + ", endTime5=" + endTime5 + ", bloggersPerDay=" + bloggersPerDay + ", scheduleTime=" + scheduleTime
        + ", timeSlots=" + Arrays.toString(timeSlots) + "]";
  }

}
