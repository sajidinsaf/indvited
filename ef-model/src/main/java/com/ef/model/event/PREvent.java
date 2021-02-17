package com.ef.model.event;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import com.ef.model.member.Member;

public class PREvent implements Event {

  private int id;
  private EventType eventType;
  private String cap;
  private Member member;
  private EventVenue eventVenue;
  private String notes;
  private Date createdDate;
  private EventTimeslot[] eventTimeSlots;
  private EventCriteria[] eventCriteria;
  private EventDeliverable[] eventDeliverables;
  private int eventTypeId;
  private int domainId;
  private int eventVenueId;
  private String exclusions;
  private int memberId;
  private List<PREventSchedule> schedules;

  public PREvent() {

  }

  public PREvent(int id, String cap, String notes, Date createdDate, int eventTypeId, int domainId, int eventVenueId,
      String exclusions, int memberId) {
    super();
    this.id = id;
    this.cap = cap;
    this.notes = notes;
    this.createdDate = createdDate;
    this.eventTypeId = eventTypeId;
    this.domainId = domainId;
    this.eventVenueId = eventVenueId;
    this.exclusions = exclusions;
    this.memberId = memberId;
  }

  public PREvent(int id, EventType eventType, String cap, Member member, EventVenue eventVenue, String notes,
      Date createdDate, EventTimeslot[] eventTimeSlots) {
    super();
    this.id = id;
    this.eventType = eventType;
    this.cap = cap;
    this.member = member;
    this.eventVenue = eventVenue;
    this.notes = notes;
    this.createdDate = createdDate;
    this.eventTimeSlots = eventTimeSlots;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getEventTypeId() {
    return eventTypeId;
  }

  public void setEventTypeId(int eventTypeId) {
    this.eventTypeId = eventTypeId;
  }

  public int getDomainId() {
    return domainId;
  }

  public void setDomainId(int domainId) {
    this.domainId = domainId;
  }

  public int getEventVenueId() {
    return eventVenueId;
  }

  public void setEventVenueId(int eventVenueId) {
    this.eventVenueId = eventVenueId;
  }

  public String getExclusions() {
    return exclusions;
  }

  public void setExclusions(String exclusions) {
    this.exclusions = exclusions;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }

  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public EventVenue getEventVenue() {
    return eventVenue;
  }

  public void setEventVenue(EventVenue eventVenue) {
    this.eventVenue = eventVenue;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public EventTimeslot[] getEventTimeSlots() {
    return eventTimeSlots;
  }

  public void setEventTimeSlots(EventTimeslot[] eventTimeSlots) {
    this.eventTimeSlots = eventTimeSlots;
  }

  public EventCriteria[] getEventCriteria() {
    return eventCriteria;
  }

  public void setEventCriteria(EventCriteria[] eventCriteria) {
    this.eventCriteria = eventCriteria;
  }

  public EventDeliverable[] getEventDeliverables() {
    return eventDeliverables;
  }

  public void setEventDeliverables(EventDeliverable[] eventDeliverables) {
    this.eventDeliverables = eventDeliverables;
  }

  public List<PREventSchedule> getSchedules() {
    return schedules;
  }

  public void setSchedules(List<PREventSchedule> schedules) {
    this.schedules = schedules;
  }

  @Override
  public String toString() {
    return "PREvent [id=" + id + ", eventType=" + eventType + ", cap=" + cap + ", member=" + member + ", eventVenue="
        + eventVenue + ", notes=" + notes + ", createdDate=" + createdDate + ", eventTimeSlots="
        + Arrays.toString(eventTimeSlots) + ", eventCriteria=" + Arrays.toString(eventCriteria) + ", eventDeliverables="
        + Arrays.toString(eventDeliverables) + ", eventTypeId=" + eventTypeId + ", domainId=" + domainId
        + ", eventVenueId=" + eventVenueId + ", exclusions=" + exclusions + ", memberId=" + memberId + ", schedules="
        + schedules + "]";
  }

  @Override
  public String getDescription() {
    return eventType != null ? eventType.getName() : id + "";
  }

}
