package com.ef.eventservice.publisher;

import com.ef.common.MapBasedContext;

public class EventServiceContext extends MapBasedContext {

  public static final String CURRENT_EVENT_WRAPPER = "currentEventWrapper";
  public static final String WEB_SCUBSCRIPTION_MODEL = "webSubscriptionModel";

  public EventServiceContext() {
    super();
  }

  @Override
  public <T> T get(String key) {
    return super.get(key);
  }

}
