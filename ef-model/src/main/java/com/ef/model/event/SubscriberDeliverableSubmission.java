package com.ef.model.event;

import com.google.gson.Gson;

public class SubscriberDeliverableSubmission {

  private int eventId, subscriberId;
  private String deliverableUrl;

  private EventDeliverableMetadata eventDeliverableMeta;

  public SubscriberDeliverableSubmission() {

  }

  public SubscriberDeliverableSubmission(int eventId, int subscriberId, String deliverableUrl,
      EventDeliverableMetadata eventDeliverableMeta) {
    super();
    this.eventId = eventId;
    this.subscriberId = subscriberId;
    this.deliverableUrl = deliverableUrl;
    this.eventDeliverableMeta = eventDeliverableMeta;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public int getSubscriberId() {
    return subscriberId;
  }

  public void setSubscriberId(int subscriberId) {
    this.subscriberId = subscriberId;
  }

  public String getDeliverableUrl() {
    return deliverableUrl;
  }

  public void setDeliverableUrl(String deliverableUrl) {
    this.deliverableUrl = deliverableUrl;
  }

  public EventDeliverableMetadata getEventDeliverableMeta() {
    return eventDeliverableMeta;
  }

  public void setEventDeliverableMeta(EventDeliverableMetadata eventDeliverableMeta) {
    this.eventDeliverableMeta = eventDeliverableMeta;
  }

  public static void main(String[] args) {
    int eventId = 9999;
    int subscriberId = 34223;
    EventDeliverableMetadata deliverableMeta = new EventDeliverableMetadata(1, "Zomato Review", "Zomato Review");
    String deliverableUrl = "https://instagram.com/34535";
    SubscriberDeliverableSubmission event = new SubscriberDeliverableSubmission(eventId, subscriberId, deliverableUrl,
        deliverableMeta);

    System.out.println(new Gson().toJson(event));
  }

  @Override
  public String toString() {
    return "SubscriberDeliverableSubmission [eventId=" + eventId + ", subscriberId=" + subscriberId
        + ", deliverableUrl=" + deliverableUrl + ", eventDeliverableMeta=" + eventDeliverableMeta + "]";
  }

}
