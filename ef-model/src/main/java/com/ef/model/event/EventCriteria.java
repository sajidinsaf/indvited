package com.ef.model.event;

import com.ef.model.core.Forum;
import com.ef.model.core.Identifiable;

public class EventCriteria implements Identifiable {

  /**
   * 
   */
  private static final long serialVersionUID = -3443436689819945665L;
  private int id;
  private String name;
  private int criterionValue;
  private Forum forum;

  public EventCriteria() {

  }

  public EventCriteria(int id, String name, int criteriaValue, Forum forum) {
    super();
    this.id = id;
    this.name = name;
    this.criterionValue = criteriaValue;
    this.forum = forum;
  }

  public int getId() {
    return id;
  }

  public void setEventId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCriterionValue() {
    return criterionValue;
  }

  public void setCriterionValue(int criterionValue) {
    this.criterionValue = criterionValue;
  }

  public Forum getForum() {
    return forum;
  }

  public void setForum(Forum forum) {
    this.forum = forum;
  }

  @Override
  public String toString() {
    return "EventCriteria [id=" + id + ", name=" + name + ", criterionValue=" + criterionValue + ", forum=" + forum
        + "]";
  }

}
