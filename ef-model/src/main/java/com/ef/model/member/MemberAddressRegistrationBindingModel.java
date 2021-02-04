package com.ef.model.member;

public class MemberAddressRegistrationBindingModel {

  private int memberId;
  private String addressLine1, addressLine2, addressLine3, city, country, pincode;

  public MemberAddressRegistrationBindingModel() {

  }

  public MemberAddressRegistrationBindingModel(int memberId, String addressLine1, String addressLine2,
      String addressLine3, String city, String country, String pincode) {
    super();
    this.memberId = memberId;
    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.addressLine3 = addressLine3;
    this.city = city;
    this.country = country;
    this.pincode = pincode;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
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
  public String toString() {
    return "MemberAddressRegistrationBindingModel [memberId=" + memberId + ", addressLine1=" + addressLine1
        + ", addressLine2=" + addressLine2 + ", addressLine3=" + addressLine3 + ", city=" + city + ", country="
        + country + ", pincode=" + pincode + "]";
  }

}
