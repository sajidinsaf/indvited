package com.ef.model.member;

import java.io.Serializable;
import java.sql.Timestamp;

public class MemberLoginControl implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5177139092646564272L;

  private final int memberId;
  private final String token;
  private final long creationTimestamp;
  private final long expires;
  private String xAuthToken;

  public MemberLoginControl(int memberId, String token, Timestamp creationTimestamp, Timestamp expiryTimestamp) {
    super();
    this.memberId = memberId;
    this.token = token;
    this.creationTimestamp = creationTimestamp.getTime();
    this.expires = expiryTimestamp.getTime();
  }

  public int getMemberId() {
    return memberId;
  }

  public String getToken() {
    return token;
  }

  public long getCreationTimestamp() {
    return creationTimestamp;
  }

  public long getExpires() {
    return expires;
  }

  public String getxAuthToken() {
    return xAuthToken;
  }

  public void setxAuthToken(String xAuthToken) {
    this.xAuthToken = xAuthToken;
  }

  @Override
  public String toString() {
    return "MemberLoginControl [memberId=" + memberId + ", token=" + token + ", creationTimestamp=" + creationTimestamp
        + ", expires=" + expires + ", xAuthToken=" + xAuthToken + "]";
  }

}
