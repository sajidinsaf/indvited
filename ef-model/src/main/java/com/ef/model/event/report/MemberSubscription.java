package com.ef.model.event.report;

import java.util.Date;

import com.ef.common.util.DateUtil;

public class MemberSubscription {

  private String firstName, lastName, phoneNumber, scheduleDate, scheduleTime;

  public MemberSubscription() {

  }

  public MemberSubscription(String firstName, String lastName, String phoneNumber, Date scheduleDate,
      String scheduleTime) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    setScheduleDate(scheduleDate);
    setScheduleTime(scheduleTime);
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getScheduleDate() {
    return scheduleDate;
  }

  public void setScheduleDate(Date scheduleDate) {
    if (scheduleDate == null) {
      return;
    }
    this.scheduleDate = new DateUtil().formatDateForEventDisplay(scheduleDate);
  }

  public String getScheduleTime() {
    return scheduleTime;
  }

  public void setScheduleTime(String scheduleTime) {

    this.scheduleTime = new DateUtil().formatTimeString(scheduleTime, "HHmm", "hh:mma");
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return "MemberSubscription [firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
        + ", scheduleDate=" + scheduleDate + ", scheduleTime=" + scheduleTime + "]";
  }

}
