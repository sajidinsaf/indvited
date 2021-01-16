package com.ef.model.member;

import java.io.Serializable;
import java.sql.Timestamp;

public class MemberLoginControl implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5177139092646564272L;

  private final String email;
  private final String token;
  private final Timestamp creationTimestamp;
  private final Timestamp expires;

  public MemberLoginControl(String memberEmailId, String token, Timestamp creationTimestamp,
      Timestamp expiryTimestamp) {
    super();
    this.email = memberEmailId;
    this.token = token;
    this.creationTimestamp = creationTimestamp;
    this.expires = expiryTimestamp;
  }

  public String getEmail() {
    return email;
  }

  public String getToken() {
    return token;
  }

  public Timestamp getCreationTimestamp() {
    return creationTimestamp;
  }

  public Timestamp getExpires() {
    return expires;
  }

  @Override
  public String toString() {
    return "MemberLoginControl [memberId=" + email + ", token=" + token + ", creationTimestamp=" + creationTimestamp
        + ", expiryTimestamp=" + expires + "]";
  }

}
