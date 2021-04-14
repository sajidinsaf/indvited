package com.ef.model.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.common.logging.ServiceLoggingUtil;
import com.google.gson.Gson;

public class PREventScheduleSubscriptionWebFormBindingModel {

  private static final Logger logger = LoggerFactory.getLogger(PREventScheduleSubscriptionWebFormBindingModel.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private int eventId;
  private String firstName, lastName, email, phone, preferredDate, preferredTime, criteria, address, city, gender;

  public PREventScheduleSubscriptionWebFormBindingModel() {
    // TODO Auto-generated constructor stub
  }

  public PREventScheduleSubscriptionWebFormBindingModel(int eventId, String firstName, String lastName, String email,
      String phone, String preferredDate, String preferredTime, String criteria, String address, String city,
      String gender) {
    super();
    this.eventId = eventId;
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

  public String getPreferredDate() {
    return preferredDate;
  }

  public void setPreferredDate(String preferredDate) {
    this.preferredDate = preferredDate;
  }

  public String getPreferredTime() {
    return preferredTime;
  }

  public void setPreferredTime(String preferredTime) {
    this.preferredTime = preferredTime;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
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

  @Override
  public String toString() {
    return "PREventScheduleSubscriptionWebFormBindingModel [eventId=" + eventId + ", firstName=" + firstName
        + ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + ", preferredDate=" + preferredDate
        + ", preferredTime=" + preferredTime + ", criteria=" + criteria + ", address=" + address + ", city=" + city
        + ", gender=" + gender + "]";
  }

  public static void main(String args[]) {
    String firstName = "Gregory";
    String lastName = "Peck";
    String email = "gp@gmail.com";
    String phone = "4525253222";
    String preferredDate = "Thu 15 Jan 2020-23";
    String preferredTime = "12:30";
    String criteria = "sfsfW£4sdfsfsfw£SDS";
    String address = "3234 sdfsdf";
    String city = "London";
    String gender = "M";
    PREventScheduleSubscriptionWebFormBindingModel w = new PREventScheduleSubscriptionWebFormBindingModel(50, firstName,
        lastName, email, phone, preferredDate, preferredTime, criteria, address, city, gender);

    System.out.println(new Gson().toJson(w));

  }

}
