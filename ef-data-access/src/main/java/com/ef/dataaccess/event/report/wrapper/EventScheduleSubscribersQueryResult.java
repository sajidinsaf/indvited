package com.ef.dataaccess.event.report.wrapper;

import java.util.Date;

import com.ef.common.util.DateUtil;

public class EventScheduleSubscribersQueryResult {

  private int eventId;
  private String venueName, venueAddress, dateFrom, dateTo, firstName, lastName, phone, scheduleDate, scheduleTime;

  public EventScheduleSubscribersQueryResult(int eventId, String venueName, String venueAddress, String dateFrom,
      String dateTo, String firstName, String lastName, String phone, String scheduleDate, String scheduleTime) {
    super();
    this.eventId = eventId;
    this.venueName = venueName;
    this.venueAddress = venueAddress;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.scheduleDate = scheduleDate;
    this.scheduleTime = scheduleTime;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public String getVenueName() {
    return venueName;
  }

  public void setVenueName(String venueName) {
    this.venueName = venueName;
  }

  public String getVenueAddress() {
    return venueAddress;
  }

  public void setVenueAddress(String venueAddress) {
    this.venueAddress = venueAddress;
  }

  public Date getDateFrom() {
    return new DateUtil().parseDate(dateFrom, DateUtil.yyyy_dash_MM_dash_dd_format);
  }

  public void setDateFrom(String dateFrom) {
    this.dateFrom = dateFrom;
  }

  public Date getDateTo() {
    return new DateUtil().parseDate(dateTo, DateUtil.yyyy_dash_MM_dash_dd_format);
  }

  public void setDateTo(String dateTo) {
    this.dateTo = dateTo;
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

  public Date getScheduleDate() {
    return new DateUtil().parseDate(scheduleDate, DateUtil.yyyy_dash_MM_dash_dd_format);
  }

  public void setScheduleDate(String scheduleDate) {
    this.scheduleDate = scheduleDate;
  }

  public String getScheduleTime() {
    return scheduleTime;
  }

  public void setScheduleTime(String scheduleTime) {
    this.scheduleTime = scheduleTime;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

}
