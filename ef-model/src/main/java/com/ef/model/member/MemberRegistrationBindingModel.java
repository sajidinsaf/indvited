package com.ef.model.member;

import java.util.Date;

public class MemberRegistrationBindingModel implements CredentialBindingModel {

  private String id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String email;
  private String gender;
  private String phone;
  private String memberType;
  private Date dateRegistered;

  public MemberRegistrationBindingModel() {

  }

  public MemberRegistrationBindingModel(String id, String firstname, String lastname, String username, String password,
      String email, String gender, String phone, String memberType) {
    super();
    this.id = id;
    this.firstName = firstname;
    this.lastName = lastname;
    this.username = username;
    this.password = password;
    this.email = email;
    this.phone = phone;
    this.memberType = memberType;
    this.dateRegistered = new Date();

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstname) {
    this.firstName = firstname;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastname) {
    this.lastName = lastname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getMemberType() {
    return memberType;
  }

  public void setMemberType(String memberType) {
    this.memberType = memberType;
  }

  public Date getDateRegistered() {
    return dateRegistered;
  }

  @Override
  public String getSecret() {
    return getPassword();
  }

  @Override
  public String toString() {
    return "MemberRegistrationBindingModel [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
        + ", username=" + username + ", password=" + password + ", email=" + email + ", gender=" + gender + ", phone="
        + phone + ", memberType=" + memberType + ", dateRegistered=" + dateRegistered + "]";
  }

}
