package com.ef.model.event;

import com.ef.model.core.Identifiable;

public class EventStatusMeta implements Identifiable {

  private static final long serialVersionUID = -996305650172275758L;

  public static final int KNOWN_STATUS_ID_CREATED = 1;
  public static final int KNOWN_STATUS_ID_ELIGIBLE = 2;
  public static final int KNOWN_STATUS_ID_APPLIED = 3;
  public static final int KNOWN_STATUS_ID_APPROVED = 4;
  public static final int KNOWN_STATUS_ID_REJECTED = 5;
  public static final int KNOWN_STATUS_ID_APPROVAL_CANCELLED = 6;
  public static final int KNOWN_STATUS_ID_EVENT_CANCELLED = 7;
  public static final int KNOWN_STATUS_ID_CLOSED = 8;
  public static final int KNOWN_STATUS_ID_REDUNDANT = 9;
  public static final int KNOWN_STATUS_ID_DELIVERABLE_UPLOADED = 10;

  private final int id;
  private final String name;
  private final String displayName;
  private final String description;

  public EventStatusMeta(int id, String name, String displayName, String description) {
    super();
    this.id = id;
    this.name = name;
    this.displayName = displayName;
    this.description = description;
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

  public String getDisplayName() {
    return displayName;
  }

  @Override
  public String toString() {
    return "EventStatusMeta [id=" + id + ", name=" + name + ", displayName=" + displayName + ", description="
        + description + "]";
  }

}
