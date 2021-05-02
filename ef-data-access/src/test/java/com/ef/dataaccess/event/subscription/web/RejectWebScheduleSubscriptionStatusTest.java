package com.ef.dataaccess.event.subscription.web;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ef.dataaccess.Update;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREventScheduleSubscriptionStatusChangeBindingModel;

public class RejectWebScheduleSubscriptionStatusTest extends WebScheduleSubscriptionStatusTest {

  @SuppressWarnings("unchecked")
  @Override
  protected Update<PREventScheduleSubscriptionStatusChangeBindingModel, Integer> getUpdateBean(
      AnnotationConfigApplicationContext appContext) {
    return appContext.getBean("rejectWebScheduleSubscriptionStatus", Update.class);
  }

  @Override
  protected int getExpectedUpdateStatus() {
    return EventStatusMeta.KNOWN_STATUS_ID_REJECTED;
  }

}
