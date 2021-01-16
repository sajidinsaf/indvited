package com.ef.model.member;

import java.io.Serializable;
import java.sql.Timestamp;

public class MemberLoginControl implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5177139092646564272L;

  private final String memberEmailId;
  private final String token;
  private final Timestamp creationTimestamp;
  private final Timestamp expiryTimestamp;

  public MemberLoginControl(String memberId, String token, Timestamp creationTimestamp, Timestamp expiryTimestamp) {
    super();
    this.memberEmailId = memberId;
    this.token = token;
    this.creationTimestamp = creationTimestamp;
    this.expiryTimestamp = expiryTimestamp;
  }

  public String getMemberEmailId() {
    return memberEmailId;
  }

  public String getToken() {
    return token;
  }

  public Timestamp getCreationTimestamp() {
    return creationTimestamp;
  }

  public Timestamp getExpiryTimestamp() {
    return expiryTimestamp;
  }

  @Override
  public String toString() {
    return "MemberLoginControl [memberId=" + memberEmailId + ", token=" + token + ", creationTimestamp="
        + creationTimestamp + ", expiryTimestamp=" + expiryTimestamp + "]";
  }

}
