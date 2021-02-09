package com.ef.model.member;

public class MemberDomainBindingModel {

  private int memberId;
  private int domainId;

  public MemberDomainBindingModel() {

  }

  public MemberDomainBindingModel(int memberId, int domainId) {
    super();
    this.memberId = memberId;
    this.domainId = domainId;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public int getDomainId() {
    return domainId;
  }

  public void setDomainId(int domainId) {
    this.domainId = domainId;
  }

  @Override
  public String toString() {
    return "MemberDomainBindingModel [memberId=" + memberId + ", domainId=" + domainId + "]";
  }

}
