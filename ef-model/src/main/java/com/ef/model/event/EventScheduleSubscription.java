package com.ef.model.event;

import java.util.Random;

import com.google.gson.Gson;

public class EventScheduleSubscription {

  private long id;
  private long eventSubscriptionTimeslotId;
  private int subscriberId;
  private String preferredTime;
  private final EventStatusMeta eventStatus;

  public EventScheduleSubscription(long id, long eventSubscriptionTimeslotId, int subscriberId, String preferredTime,
      EventStatusMeta eventStatusMeta) {
    super();
    this.id = id;
    this.eventSubscriptionTimeslotId = eventSubscriptionTimeslotId;
    this.subscriberId = subscriberId;
    this.preferredTime = preferredTime;
    this.eventStatus = eventStatusMeta;
  }

  public static void main(String args[]) {
    long eventScheduleTimeslotIdSubscriptionId = new Random(10000000).nextLong();
    long eventScheduleTimeslotId = new Random(10000000).nextLong();
    int subscriberId = new Random(100000).nextInt();
    String preferredTime = "1200";
    int statusId = new Random(5).nextInt();
    String name = "statusName-" + statusId;
    String description = "statusDescription-" + eventScheduleTimeslotIdSubscriptionId;
    EventStatusMeta status = new EventStatusMeta(statusId, name, description);
    EventScheduleSubscription a = new EventScheduleSubscription(eventScheduleTimeslotIdSubscriptionId,
        eventScheduleTimeslotId, subscriberId, preferredTime, status);

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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getScheduleSubscriptionId() {
    return eventSubscriptionTimeslotId;
  }

  public void setScheduleSubscriptionId(long eventSubscriptionTimeslotId) {
    this.eventSubscriptionTimeslotId = eventSubscriptionTimeslotId;
  }

  public int getSubscriberId() {
    return subscriberId;
  }

  public void setSubscriberId(int subscriberId) {
    this.subscriberId = subscriberId;
  }

  public EventStatusMeta getEventStatus() {
    return eventStatus;
  }

  public long getEventSubscriptionTimeslotId() {
    return eventSubscriptionTimeslotId;
  }

  public void setEventSubscriptionTimeslotId(long eventSubscriptionTimeslotId) {
    this.eventSubscriptionTimeslotId = eventSubscriptionTimeslotId;
  }

  public String getPreferredTime() {
    return preferredTime;
  }

  public void setPreferredTime(String preferredTime) {
    this.preferredTime = preferredTime;
  }

  @Override
  public String toString() {
    return "EventScheduleSubscription [id=" + id + ", eventSubscriptionTimeslotId=" + eventSubscriptionTimeslotId
        + ", subscriberId=" + subscriberId + ", preferredTime=" + preferredTime + ", eventStatus=" + eventStatus + "]";
  }

}
