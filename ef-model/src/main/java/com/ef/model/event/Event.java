package com.ef.model.event;

import java.util.Date;

public interface Event {

  public int getId();

  public EventType getEventType();

  public String getDescription();

  public Date getCreatedDate();

}
