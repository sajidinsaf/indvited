package com.ef.model.event;

public class EventCriteriaMetadata {

  private final int id;
  private final String name;
  private final String eventCriterionName;
  private final String description;
  private final int forumId;

  public EventCriteriaMetadata(int id, String name, String eventCriterionName, String description, int forumId) {
    super();
    this.id = id;
    this.name = name;
    this.eventCriterionName = eventCriterionName;
    this.description = description;
    this.forumId = forumId;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getEventCriterionName() {
    return eventCriterionName;
  }

  public int getForumId() {
    return forumId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + id;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

}
