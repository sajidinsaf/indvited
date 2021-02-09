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

  private final int id;
  private final String name;
  private final String description;

  public EventStatusMeta(int id, String name, String description) {
    super();
    this.id = id;
    this.name = name;
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

}
