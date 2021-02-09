package com.ef.model.event;

import java.util.Random;

import com.google.gson.Gson;

public class PREventScheduleSubscriptionBindingModel {

  private long eventSubscriptionTimeslotId;
  private int subscriberId;
  private int priority;

  public PREventScheduleSubscriptionBindingModel() {
    // TODO Auto-generated constructor stub
  }

  public PREventScheduleSubscriptionBindingModel(long eventSubscriptionTimeslotId, int subscriberId, int priority) {
    super();
    this.eventSubscriptionTimeslotId = eventSubscriptionTimeslotId;
    this.subscriberId = subscriberId;
    this.priority = priority;
  }

  public long getEventSubscriptionTimeslotId() {
    return eventSubscriptionTimeslotId;
  }

  public void setEventSubscriptionTimeslotId(long eventSubscriptionTimeslotId) {
    this.eventSubscriptionTimeslotId = eventSubscriptionTimeslotId;
  }

  public int getSubscriberId() {
    return subscriberId;
  }

  public void setSubscriberId(int subscriberId) {
    this.subscriberId = subscriberId;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  @Override
  public String toString() {
    return "PREventScheduleSubscriptionBindingModel [eventSubscriptionTimeslotId=" + eventSubscriptionTimeslotId
        + ", subscriberId=" + subscriberId + ", priority=" + priority + "]";
  }

  public static void main(String args[]) {
    long eventScheduleTimeslotId = new Random().nextInt(10000000);
    int subscriberId = new Random().nextInt(100000);
    int priority = new Random().nextInt(5);

    PREventScheduleSubscriptionBindingModel a = new PREventScheduleSubscriptionBindingModel(eventScheduleTimeslotId,
        subscriberId, priority);

    System.out.println(new Gson().toJson(a));

  }
}
