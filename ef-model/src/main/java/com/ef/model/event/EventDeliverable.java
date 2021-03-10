package com.ef.model.event;

import java.io.Serializable;

public class EventDeliverable implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3959945521308125926L;

  private int eventId;
  private int deliverableId;
  private String deliverableName;
  private String deliverableDetail;

  public EventDeliverable() {

  }

  public EventDeliverable(int id, int deliverableId, String name) {
    super();
    this.eventId = id;
    this.deliverableId = deliverableId;
    this.deliverableName = name;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int id) {
    this.eventId = id;
  }

  public String getDeliverableName() {
    return deliverableName;
  }

  public void setDeliverableName(String name) {
    this.deliverableName = name;
  }

  public int getDeliverableId() {
    return deliverableId;
  }

  public void setDeliverableId(int deliverableId) {
    this.deliverableId = deliverableId;
  }

  public String getDeliverableDetail() {
    return deliverableDetail;
  }

  public void setDeliverableDetail(String deliverableDetail) {
    this.deliverableDetail = deliverableDetail;
  }

  @Override
  public String toString() {
    return "EventDeliverable [eventId=" + eventId + ", deliverableId=" + deliverableId + ", deliverableName="
        + deliverableName + ", deliverableDetail=" + deliverableDetail + "]";
  }

}
