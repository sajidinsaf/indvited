package com.ef.model.member;

public class MemberLoginBindingModel {

  private String email;
  private String password;
  private MemberType memberType;

  public MemberLoginBindingModel() {

  }

  public MemberLoginBindingModel(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public MemberLoginBindingModel(String email, String password, MemberType memberType) {
    this.email = email;
    this.password = password;
    this.memberType = memberType;
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

  public MemberType getMemberType() {
    return memberType;
  }

  public void setMemberType(MemberType memberType) {
    this.memberType = memberType;
  }

  @Override
  public String toString() {
    return "MemberLoginBindingModel [email=" + email + ", password=" + password + ", memberType=" + memberType + "]";
  }

}
