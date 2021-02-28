package com.ef.model.event;

public class PREventScheduleSubscriptionStatusChangeBindingModel {

  private int eventId;
  private long scheduleId;
  private long subscriptionId;
  private int subscriberId;
  private int approverId;

  public PREventScheduleSubscriptionStatusChangeBindingModel() {

  }

  public PREventScheduleSubscriptionStatusChangeBindingModel(int eventId, long scheduleId, long subscriptionId,
      int subscriberId, int approverId) {
    super();
    this.eventId = eventId;
    this.scheduleId = scheduleId;
    this.subscriptionId = subscriptionId;
    this.subscriberId = subscriberId;
    this.approverId = approverId;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public long getScheduleId() {
    return scheduleId;
  }

  public void setScheduleId(long scheduleId) {
    this.scheduleId = scheduleId;
  }

  public long getSubscriptionId() {
    return subscriptionId;
  }

  public void setSubscriptionId(long subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  public int getSubscriberId() {
    return subscriberId;
  }

  public void setSubscriberId(int subscriberId) {
    this.subscriberId = subscriberId;
  }

  public int getApproverId() {
    return approverId;
  }

  public void setApproverId(int approverId) {
    this.approverId = approverId;
  }

  @Override
  public String toString() {
    return "PREventScheduleSubscriptionStatusChangeBindingModel [eventId=" + eventId + ", scheduleId=" + scheduleId
        + ", subscriptionId=" + subscriptionId + ", subscriberId=" + subscriberId + ", approverId=" + approverId + "]";
  }

}
