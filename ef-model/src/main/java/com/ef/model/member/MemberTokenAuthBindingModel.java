package com.ef.model.member;

public class MemberTokenAuthBindingModel implements CredentialBindingModel {

  private String email;
  private String token;
  private long expires;
  private MemberType memberType;

  public MemberTokenAuthBindingModel() {

  }

  public MemberTokenAuthBindingModel(String email, String token, long expires) {
    super();
    this.email = email;
    this.token = token;
    this.expires = expires;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getExpires() {
    return expires;
  }

  public void setExpires(long expires) {
    this.expires = expires;
  }

  public MemberType getMemberType() {
    return memberType;
  }

  public void setMemberType(MemberType memberType) {
    this.memberType = memberType;
  }

  @Override
  public String getSecret() {
    return getToken();
  }

  @Override
  public String toString() {
    return "MemberTokenAuthBindingModel [email=" + email + ", token=" + token + ", expires=" + expires + ", memberType="
        + memberType + "]";
  }

}
