package com.ef.model.event;

import com.google.gson.Gson;

public class SubscriberDeliverableSubmissionBindingModel {

  private int eventId, subscriberId, deliverableId;
  private String deliverableUrl, comments;

  public SubscriberDeliverableSubmissionBindingModel() {

  }

  public SubscriberDeliverableSubmissionBindingModel(int eventId, int subscriberId, int deliverableId,
      String deliverableUrl) {
    super();
    this.eventId = eventId;
    this.subscriberId = subscriberId;
    this.deliverableId = deliverableId;
    this.deliverableUrl = deliverableUrl;
  }

  public SubscriberDeliverableSubmissionBindingModel(int eventId, int subscriberId, int deliverableId,
      String deliverableUrl, String comments) {
    super();
    this.eventId = eventId;
    this.subscriberId = subscriberId;
    this.deliverableId = deliverableId;
    this.deliverableUrl = deliverableUrl;
    this.comments = comments;
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

  public int getDeliverableId() {
    return deliverableId;
  }

  public void setDeliverableId(int deliverableId) {
    this.deliverableId = deliverableId;
  }

  public String getDeliverableUrl() {
    return deliverableUrl;
  }

  public void setDeliverableUrl(String deliverableUrl) {
    this.deliverableUrl = deliverableUrl;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  @Override
  public String toString() {
    return "SubscriberDeliverableSubmissionBindingModel [eventId=" + eventId + ", subscriberId=" + subscriberId
        + ", deliverableId=" + deliverableId + ", deliverableUrl=" + deliverableUrl + ", comments=" + comments + "]";
  }

  public static void main(String[] args) {
    int eventId = 9999;
    int subscriberId = 34223;
    int deliverableId = 3;
    String deliverableUrl = "https://instagram.com/34535";
    SubscriberDeliverableSubmissionBindingModel event = new SubscriberDeliverableSubmissionBindingModel(eventId,
        subscriberId, deliverableId, deliverableUrl);

    System.out.println(new Gson().toJson(event));
  }
}
