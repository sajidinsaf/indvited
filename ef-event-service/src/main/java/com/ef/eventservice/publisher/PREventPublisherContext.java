package com.ef.eventservice.publisher;

import com.ef.common.MapBasedContext;

public class PREventPublisherContext extends MapBasedContext {

  public static final String CURRENT_EVENT_WRAPPER = "currentEventWrapper";

  public PREventPublisherContext() {
    super();
  }

  @Override
  public <T> T get(String key) {
    return super.get(key);
  }

}
