package com.ef.model.event.wrapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.ef.common.util.DateUtil;
import com.ef.model.event.EventScheduleSubscription;

public class EventScheduleSubscriptionForPrApprovalWrapper extends EventScheduleSubscription {

  private List<DateAndTime> prefferedDatesAndTimes;

  public EventScheduleSubscriptionForPrApprovalWrapper(EventScheduleSubscription subscription) {
    super(subscription.getId(), subscription.getEventScheduleId(), subscription.getSubscriberId(),
        subscription.getScheduleDate(), subscription.getPreferredTime(), subscription.getEventStatus());
    setSubscriber(subscription.getSubscriber());
    prefferedDatesAndTimes = new ArrayList<DateAndTime>();
    addSubscription(subscription);
  }

  public boolean addSubscription(EventScheduleSubscription subscription) {
    if (subscription.getEventScheduleId() != getEventScheduleId()
        || subscription.getSubscriberId() != getSubscriberId()) {
      return false;
    }
    long subscriptionId = subscription.getId();
    Date date = subscription.getScheduleDate();
    String time = subscription.getPreferredTime();
    String displayDate = new DateUtil().formatDateForEventDisplay(date);
    prefferedDatesAndTimes.add(new DateAndTime(subscriptionId, displayDate, time));
    return true;
  }

  public List<DateAndTime> getPrefferedDatesAndTimes() {
    return prefferedDatesAndTimes;
  }

  public void setPrefferedDatesAndTimes(List<DateAndTime> prefferedDatesAndTimes) {
    this.prefferedDatesAndTimes = prefferedDatesAndTimes;
  }

  @Override
  public String toString() {
    return "EventScheduleSubscriptionForPrApprovalWrapper [prefferedDatesAndTimes=" + prefferedDatesAndTimes
        + ", EventScheduleSubscription=" + super.toString() + "]";
  }

  @Override
  public int hashCode() {
    return ((Long) getEventScheduleId()).hashCode() * ((Integer) getSubscriberId()).hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (getClass() != obj.getClass())
      return false;
    EventScheduleSubscriptionForPrApprovalWrapper other = (EventScheduleSubscriptionForPrApprovalWrapper) obj;
    if (getEventScheduleId() != other.getEventScheduleId()) {
      return false;
    } else if (getSubscriberId() != other.getSubscriberId()) {
      return false;
    }
    return true;
  }

}
