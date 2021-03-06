package com.ef.model.event;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ef.common.util.DateUtil;

public class PREventSchedule implements Comparable<PREventSchedule> {

  private int eventId;
  private long id;
  private Date startDate, endDate;
  private Timestamp scheduleOnTime, creationTimestamp, publishedOnTimestamp;
  private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
  private boolean innerCircle, myBloggers, allEligible;
  private int bloggersPerDay;
  private String scheduleTimeInfo;
  private String daysOfTheWeek;
  private List<java.util.Date> availableDates;
  private List<String> availableDatesForDisplay;

  private List<EventScheduleSubscription> subscriptions;

  public PREventSchedule() {
    availableDates = new ArrayList<java.util.Date>();
    availableDatesForDisplay = new ArrayList<String>();
  }

  public PREventSchedule(long id, int eventId, Date startDate, Date endDate, String daysOfTheWeek, boolean innerCircle,
      boolean myBloggers, boolean allEligible, Timestamp creationTimestamp, Timestamp scheduledForTimestamp,
      Timestamp publishedOnTimestamp, int bloggersPerDay, String scheduleTimeInfo) {
    this.id = id;
    this.eventId = eventId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.daysOfTheWeek = daysOfTheWeek;
    this.monday = daysOfTheWeek.contains(String.valueOf(MONDAY.getValue()));
    this.tuesday = daysOfTheWeek.contains(String.valueOf(TUESDAY.getValue()));
    this.wednesday = daysOfTheWeek.contains(String.valueOf(WEDNESDAY.getValue()));
    this.thursday = daysOfTheWeek.contains(String.valueOf(THURSDAY.getValue()));
    this.friday = daysOfTheWeek.contains(String.valueOf(FRIDAY.getValue()));
    this.saturday = daysOfTheWeek.contains(String.valueOf(SATURDAY.getValue()));
    this.sunday = daysOfTheWeek.contains(String.valueOf(SUNDAY.getValue()));
    this.innerCircle = innerCircle;
    this.myBloggers = myBloggers;
    this.allEligible = allEligible;
    this.creationTimestamp = creationTimestamp;
    this.scheduleOnTime = scheduledForTimestamp;
    this.publishedOnTimestamp = publishedOnTimestamp;
    this.bloggersPerDay = bloggersPerDay;
    this.scheduleTimeInfo = scheduleTimeInfo;
    availableDates = new ArrayList<java.util.Date>();
    availableDatesForDisplay = new ArrayList<String>();
  }

  public PREventSchedule(int eventId, long id, Date startDate, Date endDate, Timestamp scheduleOnTime,
      Timestamp creationTimestamp, Timestamp publishedOnTimestamp, boolean monday, boolean tuesday, boolean wednesday,
      boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean innerCircle, boolean myBloggers,
      boolean allEligible, int bloggersPerDay, String scheduleTimeInfo, String daysOfTheWeek,
      List<java.util.Date> availableDates, List<String> availableDatesForDisplay,
      List<EventScheduleSubscription> subscriptions) {
    super();
    this.eventId = eventId;
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
    this.scheduleOnTime = scheduleOnTime;
    this.creationTimestamp = creationTimestamp;
    this.publishedOnTimestamp = publishedOnTimestamp;
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
    this.bloggersPerDay = bloggersPerDay;
    this.scheduleTimeInfo = scheduleTimeInfo;
    this.daysOfTheWeek = daysOfTheWeek;
    this.availableDates = availableDates;
    this.availableDatesForDisplay = availableDatesForDisplay;
    this.subscriptions = subscriptions;
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

  public Timestamp getScheduleOnTime() {
    return scheduleOnTime;
  }

  public void setScheduleOnTime(Timestamp scheduleTime) {
    this.scheduleOnTime = scheduleTime;
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

  public int getBloggersPerDay() {
    return bloggersPerDay;
  }

  public void setBloggersPerDay(int bloggersPerDay) {
    this.bloggersPerDay = bloggersPerDay;
  }

  public String getScheduleTimeInfo() {
    return scheduleTimeInfo;
  }

  public void setScheduleTimeInfo(String scheduleTimeInfo) {
    this.scheduleTimeInfo = scheduleTimeInfo;
  }

  public String getDaysOfTheWeek() {
    return daysOfTheWeek;
  }

  public void setDaysOfTheWeek(String daysOfTheWeek) {
    this.daysOfTheWeek = daysOfTheWeek;

  }

  public List<EventScheduleSubscription> getSubscriptions() {
    return subscriptions;
  }

  public void setSubscriptions(List<EventScheduleSubscription> subscriptions) {
    this.subscriptions = subscriptions;
  }

  public List<java.util.Date> getAvailableDates() {
    return availableDates;
  }

  public void setAvailableDates(List<java.util.Date> availableDates) {
    this.availableDates = availableDates;

    for (java.util.Date date : availableDates) {
      availableDatesForDisplay.add(new DateUtil().formatDateForEventDisplay(date));
    }
  }

  public List<String> getAvailableDatesForDisplay() {
    return availableDatesForDisplay;
  }

  public int getTotalNumberOfSubscriptionForSchedule() {
    return getAvailableDates().size() * getBloggersPerDay();
  }

  @Override
  public String toString() {
    return "PREventSchedule [eventId=" + eventId + ", id=" + id + ", startDate=" + startDate + ", endDate=" + endDate
        + ", scheduleOnTime=" + scheduleOnTime + ", creationTimestamp=" + creationTimestamp + ", publishedOnTimestamp="
        + publishedOnTimestamp + ", monday=" + monday + ", tuesday=" + tuesday + ", wednesday=" + wednesday
        + ", thursday=" + thursday + ", friday=" + friday + ", saturday=" + saturday + ", sunday=" + sunday
        + ", innerCircle=" + innerCircle + ", myBloggers=" + myBloggers + ", allEligible=" + allEligible
        + ", bloggersPerDay=" + bloggersPerDay + ", scheduleTimeInfo=" + scheduleTimeInfo + ", daysOfTheWeek="
        + daysOfTheWeek + ", availableDates=" + availableDates + ", subscriptions=" + subscriptions + "]";
  }

  @Override
  public int compareTo(PREventSchedule o) {
    return ((Long) getId()).compareTo((Long) o.getId());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PREventSchedule other = (PREventSchedule) obj;
    if (id != other.id)
      return false;
    return true;
  }

}
