package com.ef.model.event;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.model.member.Member;
import com.ef.model.member.MemberAddress;
import com.ef.model.member.MemberCriteriaData;
import com.google.gson.Gson;

public class PREventScheduleSubscriptionWeb implements EventScheduleSubscription {

  private static final Logger logger = LoggerFactory.getLogger(PREventScheduleSubscriptionWeb.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private int id;
  private int eventId;
  private long eventScheduleId;
  private int statusId;
  private String firstName, lastName, email, phone, preferredTime, criteria, address, city, gender;
  private Date preferredDate;
  private EventStatusMeta eventStatus;
  private List<MemberCriteriaData> memberCriteriaDataList;

  public PREventScheduleSubscriptionWeb() {

  }

  public PREventScheduleSubscriptionWeb(int id, int eventId, long eventScheduleId, String firstName, String lastName,
      String email, String phone, Date preferredDate, String preferredTime, String criteria, String address,
      String city, String gender, int statusId) {
    super();
    this.id = id;
    this.eventId = eventId;
    this.eventScheduleId = eventScheduleId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.preferredDate = preferredDate;
    this.preferredTime = preferredTime;
    this.criteria = criteria;
    this.address = address;
    this.city = city;
    this.gender = gender;
    this.statusId = statusId;
  }

  public PREventScheduleSubscriptionWeb(int id, int eventId, long eventScheduleId, String firstName, String lastName,
      String email, String phone, Date preferredDate, String preferredTime,
      List<MemberCriteriaData> memberCriteriaDataList, String address, String city, String gender,
      EventStatusMeta eventStatus) {
    super();
    this.id = id;
    this.eventId = eventId;
    this.eventScheduleId = eventScheduleId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.preferredDate = preferredDate;
    this.preferredTime = preferredTime;
    this.memberCriteriaDataList = memberCriteriaDataList;
    this.address = address;
    this.city = city;
    this.gender = gender;
    this.eventStatus = eventStatus;
    this.statusId = eventStatus.getId();
  }

  public long getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public long getEventScheduleId() {
    return eventScheduleId;
  }

  public void setEventScheduleId(long eventScheduleId) {
    this.eventScheduleId = eventScheduleId;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Date getPreferredDate() {
    return preferredDate;
  }

  public void setPreferredDate(Date preferredDate) {
    this.preferredDate = preferredDate;
  }

  public String getPreferredTime() {
    return preferredTime;
  }

  public void setPreferredTime(String preferredTime) {
    this.preferredTime = preferredTime;
  }

  public String getCriteria() {
    return criteria;
  }

  public void setCriteria(String criteria) {
    this.criteria = criteria;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public int getStatusId() {
    return statusId;
  }

  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }

  @Override
  public String toString() {
    return "PREventScheduleSubscriptionWeb [id=" + id + ", eventId=" + eventId + ", eventScheduleId=" + eventScheduleId
        + ", statusId=" + statusId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
        + ", phone=" + phone + ", preferredDate=" + preferredDate + ", preferredTime=" + preferredTime + ", criteria="
        + criteria + ", address=" + address + ", city=" + city + ", gender=" + gender + "]";
  }

  public static void main(String args[]) {
    String firstName = "Gregory";
    String lastName = "Peck";
    String email = "gp@gmail.com";
    String phone = "4525253222";
    Date preferredDate = new Date(System.currentTimeMillis());
    String preferredTime = "12:30";
    String criteria = "sfsfW£4sdfsfsfw£SDS";
    String address = "3234 sdfsdf";
    String city = "London";
    String gender = "M";
    int id = 23423;
    int eventId = 103243;
    int eventScheduleId = 1023;
    int statusId = 32423;
    PREventScheduleSubscriptionWeb w = new PREventScheduleSubscriptionWeb(id, eventId, eventScheduleId, firstName,
        lastName, email, phone, preferredDate, preferredTime, criteria, address, city, gender, statusId);

    System.out.println(new Gson().toJson(w));

  }

  @Override
  public long getScheduleSubscriptionId() {
    return getEventScheduleId();
  }

  @Override
  public int getSubscriberId() {
    // We don't expect this web subscription option to be used for long after launch
    // so casting the long to an int should be safe here.
    return (int) getEventScheduleId();
  }

  @Override
  public EventStatusMeta getEventStatus() {
    return eventStatus;
  }

  public void setEventStatus(EventStatusMeta eventStatusMeta) {
    this.eventStatus = eventStatusMeta;
  }

  @Override
  public Date getScheduleDate() {
    return preferredDate;
  }

  @Override
  public Member getSubscriber() {
    Member member = new Member();
    member.setFirstName(firstName);
    member.setLastName(lastName);
    member.setGender(gender);
    member.setEmail(email);
    member.setPhone(phone);
    member.setId(id);
    member.setMemberCriteriaDataList(memberCriteriaDataList);

    MemberAddress memberAddress = new MemberAddress();
    memberAddress.setCity(city);
    memberAddress.setAddressLine1(address);
    memberAddress.setMemberId(getSubscriberId());
    memberAddress.setCurrent(true);
    member.setMemberAddress(memberAddress);

    return member;
  }

  public void setMemberCriteriaDataList(List<MemberCriteriaData> memberCriteriaDataList) {
    this.memberCriteriaDataList = memberCriteriaDataList;
  }

  public List<MemberCriteriaData> getMemberCriteriaDataList() {
    return memberCriteriaDataList;
  }

  @Override
  public int compareTo(EventScheduleSubscription o) {
    return new Long(id).compareTo(new Long(o.getId()));
  }

  @Override
  public int getSubscriptionMode() {
    return SUBSCRIPTION_MODE_WEB;
  }
}
