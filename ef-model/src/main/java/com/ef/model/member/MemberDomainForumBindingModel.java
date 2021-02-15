package com.ef.model.member;

public class MemberDomainForumBindingModel {

  private int memberId;
  private int domainForumId;
  private String memberForumUrl;

  public MemberDomainForumBindingModel() {

  }

  public MemberDomainForumBindingModel(int memberId, int domainForumId, String memberForumUrl) {
    super();
    this.memberId = memberId;
    this.domainForumId = domainForumId;
    this.memberForumUrl = memberForumUrl;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public int getDomainForumId() {
    return domainForumId;
  }

  public void setDomainForumId(int domainId) {
    this.domainForumId = domainId;
  }

  public String getMemberForumUrl() {
    return memberForumUrl;
  }

  public void setMemberForumUrl(String memberForumUrl) {
    this.memberForumUrl = memberForumUrl;
  }

  @Override
  public String toString() {
    return "MemberDomainForumBindingModel [memberId=" + memberId + ", domainForumId=" + domainForumId
        + ", memberForumUrl=" + memberForumUrl + "]";
  }

}
