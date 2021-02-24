package com.ef.model.event;

import java.sql.Date;
import java.util.Random;

import com.ef.model.member.Member;
import com.google.gson.Gson;

public class EventScheduleSubscription implements Comparable<EventScheduleSubscription> {

  private long id;
  private long eventSubscriptionId;
  private int subscriberId;
  private Date scheduleDate;
  private String preferredTime;
  private final EventStatusMeta eventStatus;
  private Member subscriber;

  public EventScheduleSubscription(long id, long eventSubscriptionTimeslotId, int subscriberId, Date scheduleDate,
      String preferredTime, EventStatusMeta eventStatus) {
    super();
    this.id = id;
    this.eventSubscriptionId = eventSubscriptionTimeslotId;
    this.subscriberId = subscriberId;
    this.scheduleDate = scheduleDate;
    this.preferredTime = preferredTime;
    this.eventStatus = eventStatus;
  }

  public static void main(String args[]) {
    long eventScheduleTimeslotIdSubscriptionId = new Random(10000000).nextLong();
    long eventScheduleTimeslotId = new Random(10000000).nextLong();
    int subscriberId = new Random(100000).nextInt();
    Date scheduleDate = new Date(System.currentTimeMillis());
    String preferredTime = "1200";
    int statusId = new Random(5).nextInt();
    String name = "statusName-" + statusId;
    String description = "statusDescription-" + eventScheduleTimeslotIdSubscriptionId;
    EventStatusMeta status = new EventStatusMeta(statusId, name, description);
    EventScheduleSubscription a = new EventScheduleSubscription(eventScheduleTimeslotIdSubscriptionId,
        eventScheduleTimeslotId, subscriberId, scheduleDate, preferredTime, status);

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
    return eventSubscriptionId;
  }

  public void setScheduleSubscriptionId(long eventSubscriptionTimeslotId) {
    this.eventSubscriptionId = eventSubscriptionTimeslotId;
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

  public long getEventSubscriptionId() {
    return eventSubscriptionId;
  }

  public void setEventSubscriptionId(long eventSubscriptionTimeslotId) {
    this.eventSubscriptionId = eventSubscriptionTimeslotId;
  }

  public String getPreferredTime() {
    return preferredTime;
  }

  public void setPreferredTime(String preferredTime) {
    this.preferredTime = preferredTime;
  }

  public Date getScheduleDate() {
    return scheduleDate;
  }

  public void setScheduleDate(Date scheduleDate) {
    this.scheduleDate = scheduleDate;
  }

  public Member getSubscriber() {
    return subscriber;
  }

  public void setSubscriber(Member subscriber) {
    this.subscriber = subscriber;
  }

  @Override
  public int compareTo(EventScheduleSubscription o) {
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
    EventScheduleSubscription other = (EventScheduleSubscription) obj;
    if (id != other.id)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "EventScheduleSubscription [id=" + id + ", eventSubscriptionId=" + eventSubscriptionId + ", subscriberId="
        + subscriberId + ", scheduleDate=" + scheduleDate + ", preferredTime=" + preferredTime + ", eventStatus="
        + eventStatus + ", subscriber=" + subscriber + "]";
  }

}
