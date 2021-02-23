package com.ef.model.event;

import java.util.Random;

import com.google.gson.Gson;

public class PREventScheduleSubscriptionBindingModel {
  public static final String DISPLAY_DATE_FORMAT = "EEE d MMM yyyy";
  private long scheduleSubscriptionId;
  private int subscriberId;
  private String scheduleDate;
  private String preferredTime;

  public PREventScheduleSubscriptionBindingModel() {
    // TODO Auto-generated constructor stub
  }

  public PREventScheduleSubscriptionBindingModel(long scheduleSubscriptionId, int subscriberId, String scheduleDate,
      String preferredTime) {
    super();
    this.scheduleSubscriptionId = scheduleSubscriptionId;
    this.subscriberId = subscriberId;
    this.scheduleDate = scheduleDate;
    setPreferredTime(preferredTime);
  }

  public long getScheduleSubscriptionId() {
    return scheduleSubscriptionId;
  }

  public void setScheduleSubscriptionId(long eventSubscriptionTimeslotId) {
    this.scheduleSubscriptionId = eventSubscriptionTimeslotId;
  }

  public int getSubscriberId() {
    return subscriberId;
  }

  public void setSubscriberId(int subscriberId) {
    this.subscriberId = subscriberId;
  }

  public static void main(String args[]) {
    long eventScheduleSubscriptionId = new Random().nextInt(10000000);
    int subscriberId = new Random().nextInt(100000);
    String preferredTime = "1400";
    String scheduleDateStr = "15/01/2021";

    PREventScheduleSubscriptionBindingModel a = new PREventScheduleSubscriptionBindingModel(eventScheduleSubscriptionId,
        subscriberId, scheduleDateStr, preferredTime);

    System.out.println(new Gson().toJson(a));

  }

  public String getPreferredTime() {
    return preferredTime;
  }

  public void setPreferredTime(String preferredTime) {
    this.preferredTime = preferredTime == null ? preferredTime : preferredTime.replaceAll(":", "").replaceAll(" ", "");
  }

  public String getScheduleDate() {
    return scheduleDate;
  }

  public void setScheduleDate(String scheduleDate) {
    this.scheduleDate = scheduleDate;
  }

  @Override
  public String toString() {
    return "PREventScheduleSubscriptionBindingModel [scheduleSubscriptionId=" + scheduleSubscriptionId
        + ", subscriberId=" + subscriberId + ", scheduleDate=" + scheduleDate + ", preferredTime=" + preferredTime
        + "]";
  }

}
