package com.ef.model.member;

import java.util.Random;

import com.google.gson.Gson;

public class MemberRegistrationBindingModel implements CredentialBindingModel {

  private String id;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private String gender;
  private String phone;
  private String memberType;
  private String addressLine1, addressLine2, addressLine3, city, country, pincode;
  private boolean current;

  public MemberRegistrationBindingModel() {

  }

  public MemberRegistrationBindingModel(String id, String firstname, String lastname, String password, String email,
      String gender, String phone, String memberType) {
    super();
    this.id = id;
    this.firstName = firstname;
    this.lastName = lastname;
    this.password = password;
    this.email = email;
    this.phone = phone;
    this.memberType = memberType;
    this.gender = gender;

  }

  public MemberRegistrationBindingModel(String firstName, String lastName, String password, String email, String gender,
      String phone, String memberType, String addressLine1, String addressLine2, String addressLine3, String city,
      String country, String pincode, boolean current) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
    this.gender = gender;
    this.phone = phone;
    this.memberType = memberType;
    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.addressLine3 = addressLine3;
    this.city = city;
    this.country = country;
    this.pincode = pincode;
    this.current = current;
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

  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public String getAddressLine3() {
    return addressLine3;
  }

  public void setAddressLine3(String addressLine3) {
    this.addressLine3 = addressLine3;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPincode() {
    return pincode;
  }

  public void setPincode(String pincode) {
    this.pincode = pincode;
  }

  @Override
  public String getSecret() {
    return getPassword();
  }

  public boolean isCurrent() {
    return current;
  }

  public void setCurrent(boolean current) {
    this.current = current;
  }

  @Override
  public String toString() {
    return "MemberRegistrationBindingModel [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
        + ", password=" + password + ", email=" + email + ", gender=" + gender + ", phone=" + phone + ", memberType="
        + memberType + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", addressLine3="
        + addressLine3 + ", city=" + city + ", country=" + country + ", pincode=" + pincode + ", current=" + current
        + "]";
  }

  public static void main(String[] args) {
    MemberRegistrationBindingModel event = new MemberRegistrationBindingModel("James", "Stewart", "Ch@@z@P1zza",
        "js" + new Random(999999).nextInt() + "@mail.co", "M", "" + new Random(999999999).nextInt(), "pr", "2 Pike Rd",
        "Belgrade Avenue", "Near Jhumri Talao", "Mumbai", "India", "400080", true);

    System.out.println(new Gson().toJson(event));
  }

}
