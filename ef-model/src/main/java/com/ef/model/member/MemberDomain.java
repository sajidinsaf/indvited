package com.ef.model.member;

public class MemberDomain {

  private int id;
  private int memberId;
  private int domainId;

  public MemberDomain() {

  }

  public MemberDomain(int id, int memberId, int domainId) {
    super();
    this.id = id;
    this.memberId = memberId;
    this.domainId = domainId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
    return "MemberDomain [id=" + id + ", memberId=" + memberId + ", domainId=" + domainId + "]";
  }

}
