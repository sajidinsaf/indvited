package com.ef.model.event;

import com.ef.model.core.Forum;

public class EventCriteriaMetadata implements Comparable<Integer> {

  private final int id;
  private final String name;
  private final String eventCriterionName;
  private final String description;
  private final int forumId;

  private Forum forum;

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

  public Forum getForum() {
    return forum;
  }

  public void setForum(Forum forum) {
    this.forum = forum;
  }

  @Override
  public String toString() {
    return "EventCriteriaMetadata [id=" + id + ", name=" + name + ", eventCriterionName=" + eventCriterionName
        + ", description=" + description + ", forumId=" + forumId + ", forum=" + forum + "]";
  }

  @Override
  public int compareTo(Integer o) {
    return forumId;
  }

}
