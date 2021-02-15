package com.ef.model.member;

public class MemberCriteriaDataBindingModel {

  private int memberId;
  private int criteriaMetadataId;
  private int memberCriteriaValue;

  public MemberCriteriaDataBindingModel(int memberId, int criteriaMetadataId, int memberCriteriaValue) {
    super();
    this.memberId = memberId;
    this.criteriaMetadataId = criteriaMetadataId;
    this.memberCriteriaValue = memberCriteriaValue;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public int getMemberCriteriaValue() {
    return memberCriteriaValue;
  }

  public void setMemberCriteriaValue(int memberCriteriaValue) {
    this.memberCriteriaValue = memberCriteriaValue;
  }

  public int getCriteriaMetadataId() {
    return criteriaMetadataId;
  }

  public void setCriteriaMetadataId(int criteriaMetadataId) {
    this.criteriaMetadataId = criteriaMetadataId;
  }

  @Override
  public String toString() {
    return "MemberCriteriaDataBindingModel [memberId=" + memberId + ", criteriaMetadataId=" + criteriaMetadataId
        + ", memberCriteriaValue=" + memberCriteriaValue + "]";
  }

}
