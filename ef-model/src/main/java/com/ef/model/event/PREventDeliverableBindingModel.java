package com.ef.model.event;

public class PREventDeliverableBindingModel {

  private String deliverableName;

  public PREventDeliverableBindingModel() {

  }

  public PREventDeliverableBindingModel(String deliverableName) {
    super();
    this.deliverableName = deliverableName;
  }

  public String getDeliverableName() {
    return deliverableName;
  }

  public void setDeliverableName(String criterionName) {
    this.deliverableName = criterionName;
  }

  @Override
  public String toString() {
    return "EventCriteriaBindingModel [deliverableName=" + deliverableName + "]";
  }

}
