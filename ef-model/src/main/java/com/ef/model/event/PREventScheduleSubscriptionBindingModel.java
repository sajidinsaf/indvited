package com.ef.model.event;

import java.util.Random;

import com.google.gson.Gson;

public class PREventScheduleSubscriptionBindingModel {

  private long scheduleSubscriptionId;
  private int subscriberId;
  private String preferredTime;

  public PREventScheduleSubscriptionBindingModel() {
    // TODO Auto-generated constructor stub
  }

  public PREventScheduleSubscriptionBindingModel(long eventSubscriptionTimeslotId, int subscriberId,
      String preferredTime) {
    super();
    this.scheduleSubscriptionId = eventSubscriptionTimeslotId;
    this.subscriberId = subscriberId;
    this.preferredTime = preferredTime;
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

    PREventScheduleSubscriptionBindingModel a = new PREventScheduleSubscriptionBindingModel(eventScheduleSubscriptionId,
        subscriberId, preferredTime);

    System.out.println(new Gson().toJson(a));

  }

  public String getPreferredTime() {
    return preferredTime;
  }

  public void setPreferredTime(String preferredTime) {
    this.preferredTime = preferredTime;
  }
}
