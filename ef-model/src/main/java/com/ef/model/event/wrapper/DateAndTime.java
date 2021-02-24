package com.ef.model.event.wrapper;

public class DateAndTime {

  private long subscriptionId;
  private String date;
  private String time;

  public DateAndTime(long subscriptionId, String date, String time) {

    this.subscriptionId = subscriptionId;
    this.date = date;
    this.time = time;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public long getSubscriptionId() {
    return subscriptionId;
  }

  public void setSubscriptionId(long subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  @Override
  public String toString() {
    return "DateAndTime [subscriptionId=" + subscriptionId + ", date=" + date + ", time=" + time + "]";
  }

}
