package com.ef.model.event;

import java.sql.Date;
import java.sql.Timestamp;

public class PREventSchedule {

  private int eventId;
  private long id;
  private Date startDate, endDate;
  private Timestamp scheduleTime, creationTimestamp, publishedOnTimestamp;
  private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
  private boolean innerCircle, myBloggers, allEligible;

  private EventTimeslot[] eventTimeSlots;

  public PREventSchedule() {

  }

  public PREventSchedule(long id, int eventId, Date startDate, Date endDate, String daysOfTheWeek, boolean innerCircle,
      boolean myBloggers, boolean allEligible, Timestamp creationTimestamp, Timestamp scheduledForTimestamp,
      Timestamp publishedOnTimestamp) {
    this.id = id;
    this.eventId = eventId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.monday = daysOfTheWeek.contains("2");
    this.tuesday = daysOfTheWeek.contains("3");
    this.wednesday = daysOfTheWeek.contains("4");
    this.thursday = daysOfTheWeek.contains("5");
    this.friday = daysOfTheWeek.contains("6");
    this.saturday = daysOfTheWeek.contains("7");
    this.sunday = daysOfTheWeek.contains("1");
    this.innerCircle = innerCircle;
    this.myBloggers = myBloggers;
    this.allEligible = allEligible;
    this.creationTimestamp = creationTimestamp;
    this.scheduleTime = scheduledForTimestamp;
    this.publishedOnTimestamp = publishedOnTimestamp;

  }

  public EventTimeslot[] getEventTimeSlots() {
    return eventTimeSlots;
  }

  public void setEventTimeSlots(EventTimeslot[] eventTimeSlots) {
    this.eventTimeSlots = eventTimeSlots;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public Timestamp getScheduleTime() {
    return scheduleTime;
  }

  public void setScheduleTime(Timestamp scheduleTime) {
    this.scheduleTime = scheduleTime;
  }

  public Timestamp getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(Timestamp creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  public Timestamp getPublishedOnTimestamp() {
    return publishedOnTimestamp;
  }

  public void setPublishedOnTimestamp(Timestamp publishedOnTimestamp) {
    this.publishedOnTimestamp = publishedOnTimestamp;
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

}
