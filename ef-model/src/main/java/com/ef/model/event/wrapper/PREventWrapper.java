package com.ef.model.event.wrapper;

import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREvent;

public class PREventWrapper extends PREvent {

  private EventStatusMeta eventStatus;

  public PREventWrapper(PREvent prEvent) {
    super(prEvent);
  }

  public EventStatusMeta getEventStatus() {
    return eventStatus;
  }

  public void setEventStatus(EventStatusMeta eventStatus) {
    this.eventStatus = eventStatus;
  }

  @Override
  public String toString() {
    return "PREventWrapper [eventStatus=" + eventStatus + " " + super.toString() + "]";
  }

}
