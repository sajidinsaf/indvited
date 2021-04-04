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
    sb.append("___________________________________________");
    sb.append(lineSeparator);
    sb.append("Venue: ").append(getVenueName());
    sb.append(lineSeparator);
    sb.append("Address: ").append(getVenueAddress());
    sb.append(lineSeparator);
    sb.append("From: ").append(getDateFrom()).append(" To: ").append(getDateTo());
    sb.append(lineSeparator);
    sb.append("Number of bloggers: ").append(getMemberSubscriptionList().size());
    sb.append(lineSeparator);
    sb.append("___________________________________________");
    StringBuilder msSb = new StringBuilder();
    int serNo = 0;
    for (MemberSubscription ms : getMemberSubscriptionList()) {
      msSb.append(++serNo).append(". ").append("Name: ").append(ms.getFirstName()).append(" ").append(ms.getLastName())
          .append(lineSeparator);
      int padLength = (serNo + "").length() + 3;

      String phoneLabel = padSpaces("Phone: ", padLength);
      msSb.append(phoneLabel).append(ms.getPhoneNumber()).append(lineSeparator);

      String dateLabel = padSpaces("Date: ", padLength);
      msSb.append(dateLabel).append(ms.getScheduleDate()).append(" Time: ").append(ms.getScheduleTime())
          .append(lineSeparator).append(lineSeparator);
    }

    if (!msSb.toString().trim().equals("")) {
      sb.append(lineSeparator).append(lineSeparator);
      sb.append(msSb.toString());
    }

    // sb.append(lineSeparator);
    // sb.append(lineSeparator);
    return sb.toString();
  }

  public String padSpaces(String inputString, int length) {
    StringBuilder sb = new StringBuilder();
    while (sb.length() < length) {
      sb.append(' ');
    }
    sb.append(inputString);

    return sb.toString();
  }

  @Override
  public String toString() {
    return "EventScheduleSubscribers [eventId=" + eventId + ", venueName=" + venueName + ", venueAddress="
        + venueAddress + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", memberSubscriptionList="
        + memberSubscriptionList + "]";
  }

}
