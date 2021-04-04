package com.ef.model.event.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ef.common.util.DateUtil;

public class EventScheduleSubscribers {

  private String lineSeparator = System.getProperty("line.separator");
  private int eventId;
  private String venueName, venueAddress, dateFrom, dateTo;
  private List<MemberSubscription> memberSubscriptionList;

  public EventScheduleSubscribers() {
    memberSubscriptionList = new ArrayList<MemberSubscription>();
  }

  public EventScheduleSubscribers(int eventId, String venueName, String venueAddress, Date dateFrom, Date dateTo) {
    super();
    this.eventId = eventId;
    this.venueName = venueName;
    this.venueAddress = venueAddress;
    setDateFrom(dateFrom);
    setDateTo(dateTo);
    memberSubscriptionList = new ArrayList<MemberSubscription>();
  }

  public EventScheduleSubscribers(int eventId, String venueName, String venueAddress, String dateFrom, String dateTo,
      List<MemberSubscription> memberSubscriptionList) {
    super();
    this.eventId = eventId;
    this.venueName = venueName;
    this.venueAddress = venueAddress;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.memberSubscriptionList = memberSubscriptionList;
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

  public String getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(Date dateFrom) {
    this.dateFrom = parseDateForDisplay(dateFrom);
  }

  public String getDateTo() {
    return dateTo;
  }

  public void setDateTo(Date dateTo) {
    this.dateTo = parseDateForDisplay(dateTo);
  }

  private String parseDateForDisplay(Date date) {
    return new DateUtil().formatDateForEventDisplay(date);
  }

  public List<MemberSubscription> getMemberSubscriptionList() {
    return memberSubscriptionList;
  }

  public void setMemberSubscriptionList(List<MemberSubscription> memberSubscriptionList) {
    this.memberSubscriptionList = memberSubscriptionList;
  }

  public void add(MemberSubscription memberSubscription) {
    memberSubscriptionList.add(memberSubscription);
  }

  public String getReportForVenue() {
    StringBuilder sb = new StringBuilder();
    sb.append("Venue: ").append(getVenueName());
    sb.append(lineSeparator);
    sb.append("Address: ").append(getVenueAddress());
    sb.append(lineSeparator);
    sb.append("From: ").append(getDateFrom()).append(" To: ").append(getDateTo());
    StringBuilder msSb = new StringBuilder();
    for (MemberSubscription ms : getMemberSubscriptionList()) {
      msSb.append(lineSeparator).append("Name: ").append(ms.getFirstName()).append(" ").append(ms.getLastName())
          .append(lineSeparator).append("Phone: ").append(ms.getPhoneNumber()).append(lineSeparator).append("Date: ")
          .append(ms.getScheduleDate()).append(" Time: ").append(ms.getScheduleTime());
    }
    if (!msSb.toString().trim().equals("")) {
      sb.append(lineSeparator);
      sb.append("--------------------");
      sb.append(lineSeparator);
      sb.append(msSb.toString());
    }
    return sb.toString();
  }

  @Override
  public String toString() {
    return "EventScheduleSubscribers [eventId=" + eventId + ", venueName=" + venueName + ", venueAddress="
        + venueAddress + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", memberSubscriptionList="
        + memberSubscriptionList + "]";
  }

}
