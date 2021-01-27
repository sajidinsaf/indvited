package com.ef.model.member;

import java.sql.Timestamp;

public class MemberRegistrationControlModel {

  private final int memberId;
  private final String code;
  private final Timestamp expiryTimestamp;
  private final Timestamp confirmationTimestamp;

  public MemberRegistrationControlModel(int memberId, String getCode, Timestamp expiryTimestamp,
      Timestamp confirmationTimestamp) {
    super();
    this.memberId = memberId;
    this.code = getCode;
    this.expiryTimestamp = expiryTimestamp;
    this.confirmationTimestamp = confirmationTimestamp;
  }

  public String getCode() {
    return code;
  }

  public Timestamp getExpiryTimestamp() {
    return expiryTimestamp;
  }

  public int getMemberId() {
    return memberId;
  }

  public Timestamp getConfirmationTimestamp() {
    return confirmationTimestamp;
  }

  @Override
  public String toString() {
    return "RegistrationConfirmationCode [memberId=" + memberId + ", code=" + code + ", expiryTimestamp="
        + expiryTimestamp + ", confirmationTimestamp=" + confirmationTimestamp + "]";
  }

}
