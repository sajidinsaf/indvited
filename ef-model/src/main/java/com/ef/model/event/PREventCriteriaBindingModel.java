package com.ef.model.event;

public class PREventCriteriaBindingModel {

  private String criterionName;
  private int minValue;

  public PREventCriteriaBindingModel() {

  }

  public PREventCriteriaBindingModel(String criterionName, int minValue) {
    super();
    this.criterionName = criterionName;
    this.minValue = minValue;
  }

  public String getCriterionName() {
    return criterionName;
  }

  public void setCriterionName(String criterionName) {
    this.criterionName = criterionName;
  }

  public int getMinValue() {
    return minValue;
  }

  public void setMinValue(int minValue) {
    this.minValue = minValue;
  }

  @Override
  public String toString() {
    return "EventCriteriaBindingModel [criterionName=" + criterionName + ", minValue=" + minValue + "]";
  }

}
