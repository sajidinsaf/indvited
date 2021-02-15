package com.ef.model.member;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.ef.common.FieldCannotBeMutatedException;

public class Member implements Serializable {

  private static final long serialVersionUID = 275549302589403129L;

  private int id;
  private String firstname;
  private String lastname;
  private String email;
  private String gender;
  private String phone;
  private String password;
  private MemberType memberType;
  private Timestamp date_registered;
  private Timestamp timestamp_of_last_login;
  private MemberLoginControl memberLoginControl;
  private boolean enabled;
  private MemberAddress memberAddress;
  private List<MemberCriteriaData> memberCriteriaDataList;
  private List<MemberDomain> memberDomainMappings;

  public Member() {

  }

  public Member(int id, String firstname, String lastname, String email, String gender, String phone,
      MemberType memberType, Timestamp date_registered, Timestamp timestamp_of_last_login, boolean enabled) {
    super();
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.gender = gender;
    this.phone = phone;
    this.memberType = memberType;
    this.date_registered = date_registered;
    this.timestamp_of_last_login = timestamp_of_last_login;
    this.enabled = enabled;
  }

  public Member(int id, String firstname, String lastname, String email, String gender, String phone,
      MemberType memberType, Timestamp date_registered, Timestamp timestamp_of_last_login,
      MemberLoginControl memberLoginControl, boolean enabled) {
    super();
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.gender = gender;
    this.phone = phone;
    this.memberType = memberType;
    this.date_registered = date_registered;
    this.timestamp_of_last_login = timestamp_of_last_login;
    this.memberLoginControl = memberLoginControl;
  }

  public Member(int id, String firstname, String lastname, String email, String gender, String phone, String password,
      MemberType memberType, Timestamp date_registered, Timestamp timestamp_of_last_login,
      MemberLoginControl memberLoginControl, boolean enabled) {
    super();
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.gender = gender;
    this.phone = phone;
    this.password = password;
    this.memberType = memberType;
    this.date_registered = date_registered;
    this.timestamp_of_last_login = timestamp_of_last_login;
    this.memberLoginControl = memberLoginControl;
    this.enabled = enabled;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    checkForMutation(id, "id");
    this.id = id;
  }

  public String getFirstName() {
    return firstname;
  }

  public void setFirstName(String firstname) {
    checkForMutation(firstname, "firstname");
    this.firstname = firstname;
  }

  public String getLastName() {
    return lastname;
  }

  public void setLastName(String lastname) {
    checkForMutation(lastname, "lastname");
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    checkForMutation(email, "email");
    this.email = email;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    checkForMutation(gender, "gender");
    this.gender = gender;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    checkForMutation(phone, "phone");
    this.phone = phone;
  }

  public MemberType getMemberType() {
    return memberType;
  }

  public void setMemberType(MemberType memberType) {
    checkForMutation(memberType, "memberType");
    this.memberType = memberType;
  }

  public Timestamp getDateRegistered() {
    return date_registered;
  }

  public void setDate_registered(Timestamp date_registered) {
    checkForMutation(date_registered, "date_registered");
    this.date_registered = date_registered;
  }

  public Timestamp getTimestamp_of_last_login() {
    return timestamp_of_last_login;
  }

  public void setTimestamp_of_last_login(Timestamp timestamp_of_last_login) {
    checkForMutation(timestamp_of_last_login, "timestamp_of_last_login");
    this.timestamp_of_last_login = timestamp_of_last_login;
  }

  public MemberAddress getMemberAddress() {
    return memberAddress;
  }

  public void setMemberAddress(MemberAddress memberAddress) {
    this.memberAddress = memberAddress;
  }

  public MemberLoginControl getMemberLoginControl() {
    return memberLoginControl;
  }

  public void setMemberLoginControl(MemberLoginControl memberLoginControl) {
    this.memberLoginControl = memberLoginControl;
  }

  public boolean isEnabled() {
    return enabled;
  }

  private void checkForMutation(Object o, String fieldName) {
    if (o != null) {
      throw new FieldCannotBeMutatedException(fieldName);
    }
  }

  public List<MemberCriteriaData> getMemberCriteriaDataList() {
    return memberCriteriaDataList;
  }

  public void setMemberCriteriaDataList(List<MemberCriteriaData> memberCriteriaDataList) {
    this.memberCriteriaDataList = memberCriteriaDataList;
  }

  public List<MemberDomain> getMemberDomainMappings() {
    return memberDomainMappings;
  }

  public void setMemberDomainMappings(List<MemberDomain> memberDomainMappings) {
    this.memberDomainMappings = memberDomainMappings;
  }

  @Override
  public String toString() {
    return "Member [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", gender="
        + gender + ", phone=" + phone + ", password=" + password + ", memberType=" + memberType + ", date_registered="
        + date_registered + ", timestamp_of_last_login=" + timestamp_of_last_login + ", memberLoginControl="
        + memberLoginControl + ", enabled=" + enabled + ", memberAddress=" + memberAddress + ", memberCriteriaDataList="
        + memberCriteriaDataList + ", memberDomainMappings=" + memberDomainMappings + "]";
  }

}
