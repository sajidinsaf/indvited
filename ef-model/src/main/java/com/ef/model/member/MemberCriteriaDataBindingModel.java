package com.ef.model.member;

public class MemberCriteriaDataBindingModel {

  private int memberId;
  private String criteriaMetadataName;
  private int memberCriteriaValue;

  public MemberCriteriaDataBindingModel(int memberId, String criteriaMetadataName, int memberCriteriaValue) {
    super();
    this.memberId = memberId;
    this.criteriaMetadataName = criteriaMetadataName;
    this.memberCriteriaValue = memberCriteriaValue;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public String getCriteriaMetadataName() {
    return criteriaMetadataName;
  }

  public void setCriteriaMetadataName(String criteriaMetadataName) {
    this.criteriaMetadataName = criteriaMetadataName;
  }

  public int getMemberCriteriaValue() {
    return memberCriteriaValue;
  }

  public void setMemberCriteriaValue(int memberCriteriaValue) {
    this.memberCriteriaValue = memberCriteriaValue;
  }

  @Override
  public String toString() {
    return "MemberCriteriaDataBindingModel [memberId=" + memberId + ", criteriaMetadataName=" + criteriaMetadataName
        + ", memberCriteriaValue=" + memberCriteriaValue + "]";
  }

}
