package com.ef.model.member;

import java.sql.Timestamp;

public class MemberLoginControl {

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
