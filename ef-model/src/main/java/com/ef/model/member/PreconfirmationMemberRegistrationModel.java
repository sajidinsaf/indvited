package com.ef.model.member;

public class PreconfirmationMemberRegistrationModel {

  private final Member member;
  private final MemberRegistrationControlModel registrationConfirmationCode;

  public PreconfirmationMemberRegistrationModel(Member member,
      MemberRegistrationControlModel registrationConfirmationCode) {
    super();
    this.member = member;
    this.registrationConfirmationCode = registrationConfirmationCode;
  }

  public Member getMember() {
    return member;
  }

  public MemberRegistrationControlModel getRegistrationConfirmationCode() {
    return registrationConfirmationCode;
  }

  @Override
  public String toString() {
    return "PreconfirmationMemberRegistrationModel [member=" + member + ", registrationConfirmationCode="
        + registrationConfirmationCode + "]";
  }

}
