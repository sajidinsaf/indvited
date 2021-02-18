package com.ef.model.event;

import java.io.Serializable;

public class EventCriteriaData implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3443436689819945665L;
  private int eventId;
  private int criterionId;
  private int criterionValue;

  public EventCriteriaData() {

  }

  public EventCriteriaData(int id, int criterionId, int criterionValue) {
    super();
    this.eventId = id;
    this.criterionId = criterionId;
    this.criterionValue = criterionValue;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int id) {
    this.eventId = id;
  }

  public int getCriterionValue() {
    return criterionValue;
  }

  public void setCriterionValue(int criteriValue) {
    this.criterionValue = criteriValue;
  }

  public int getCriterionId() {
    return criterionId;
  }

  public void setCriterionId(int criteriaId) {
    this.criterionId = criteriaId;
  }

  @Override
  public String toString() {
    return "EventCriteriaData [eventId=" + eventId + ", criterionId=" + criterionId + ", criterionValue="
        + criterionValue + "]";
  }

}
