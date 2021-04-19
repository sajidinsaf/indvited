package com.ef.model.event;

import java.sql.Date;

import com.ef.model.member.Member;

public interface EventScheduleSubscription {

  public long getId();

  public long getScheduleSubscriptionId();

  public int getSubscriberId();

  public EventStatusMeta getEventStatus();

  public long getEventScheduleId();

  public String getPreferredTime();

  public Date getScheduleDate();

  public Member getSubscriber();

  public int compareTo(EventScheduleSubscription o);

}
