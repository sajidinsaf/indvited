package com.ef.model.event;

import com.google.gson.Gson;

public class PREventScheduleSubscriptionStatusChangeBindingModel {
  private int eventId;
  private long scheduleId;
  private long subscriptionId;
  private int subscriberId;
  private int approverId;
  private String dataString;
  private int subscriptionMode;

  public PREventScheduleSubscriptionStatusChangeBindingModel() {

  }

  public PREventScheduleSubscriptionStatusChangeBindingModel(int eventId, long scheduleId, long subscriptionId,
      int subscriberId, int approverId, int subscriptionMode) {
    super();
    this.eventId = eventId;
    this.scheduleId = scheduleId;
    this.subscriptionId = subscriptionId;
    this.subscriberId = subscriberId;
    this.approverId = approverId;
    this.subscriptionMode = subscriptionMode;
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

  public String getDataString() {
    return dataString;
  }

  public int getSubscriptionMode() {
    return subscriptionMode;
  }

  public void setSubscriptionMode(int subscriptionMode) {
    this.subscriptionMode = subscriptionMode;
  }

  public void setDataString(String dataString) {
    if (dataString == null) {
      return;
    }
    this.dataString = dataString;
    PREventScheduleSubscriptionStatusChangeBindingModel m = new Gson().fromJson(dataString,
        PREventScheduleSubscriptionStatusChangeBindingModel.class);
    setEventId(m.getEventId());
    setScheduleId(m.getScheduleId());
    setSubscriberId(m.getSubscriberId());
    setApproverId(m.getApproverId());
    setSubscriptionId(m.getSubscriptionId());
    setSubscriptionMode(m.getSubscriptionMode());
  }

  @Override
  public String toString() {
    return "PREventScheduleSubscriptionStatusChangeBindingModel [eventId=" + eventId + ", scheduleId=" + scheduleId
        + ", subscriptionId=" + subscriptionId + ", subscriberId=" + subscriberId + ", approverId=" + approverId
        + ", dataString=" + dataString + ", subscriptionMode=" + subscriptionMode + "]";
  }

}
