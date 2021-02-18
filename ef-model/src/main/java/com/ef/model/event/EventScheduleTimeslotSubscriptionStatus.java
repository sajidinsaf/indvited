package com.ef.model.event;

public class EventScheduleTimeslotSubscriptionStatus {

  private final long id;
  private final long eventTimeSlotSubscriptionId;
  private final EventStatusMeta eventTimeSlotSubscriptionStatus;

  public EventScheduleTimeslotSubscriptionStatus(long id, long eventTimeSlotSubscriptionId,
      EventStatusMeta eventTimeSlotSubscriptionStatus) {
    super();
    this.id = id;
    this.eventTimeSlotSubscriptionId = eventTimeSlotSubscriptionId;
    this.eventTimeSlotSubscriptionStatus = eventTimeSlotSubscriptionStatus;
  }

  public long getEventId() {
    return id;
  }

  public long getEventTimeSlotSubscriptionId() {
    return eventTimeSlotSubscriptionId;
  }

  public EventStatusMeta getEventTimeSlotSubscriptionStatus() {
    return eventTimeSlotSubscriptionStatus;
  }

  @Override
  public String toString() {
    return "EventScheduleTimeslotSubscriptionStatus [id=" + id + ", eventTimeSlotSubscriptionId="
        + eventTimeSlotSubscriptionId + ", eventTimeSlotSubscriptionStatus=" + eventTimeSlotSubscriptionStatus + "]";
  }

}
