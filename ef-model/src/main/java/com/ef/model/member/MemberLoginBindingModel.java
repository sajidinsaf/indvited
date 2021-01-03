package com.ef.model.member;

public class MemberLoginBindingModel {

  private String email;
  private String password;

  public MemberLoginBindingModel() {

  }

  public MemberLoginBindingModel(String email, String password) {
    super();
    this.email = email;
    this.password = password;

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

  @Override
  public String toString() {
    return "MemberLoginBindingModel [email=" + email + ", password=" + password + "]";
  }

}
