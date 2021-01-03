package com.ef.model.event;

public class EventCriteria {

  private int id;
  private String name;
  private int criteriValue;

  public EventCriteria() {

  }

  public EventCriteria(int id, String name, int criteriValue) {
    super();
    this.id = id;
    this.name = name;
    this.criteriValue = criteriValue;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCriteriValue() {
    return criteriValue;
  }

  public void setCriteriValue(int criteriValue) {
    this.criteriValue = criteriValue;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + criteriValue;
    result = prime * result + id;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    EventCriteria other = (EventCriteria) obj;
    if (criteriValue != other.criteriValue)
      return false;
    if (id != other.id)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "EventCriteria [id=" + id + ", name=" + name + ", criteriValue=" + criteriValue + "]";
  }

}
