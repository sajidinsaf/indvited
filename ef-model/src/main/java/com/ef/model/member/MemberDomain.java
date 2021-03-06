package com.ef.model.member;

public class MemberDomain {

  private int id;
  private int memberId;
  private int domainForumId;
  private String memberForumUrl;

  public MemberDomain() {

  }

  public MemberDomain(int id, int memberId, int domainForumId, String memberForumUrl) {
    super();
    this.id = id;
    this.memberId = memberId;
    this.domainForumId = domainForumId;
    this.memberForumUrl = memberForumUrl;
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

  public int getDomainForumId() {
    return domainForumId;
  }

  public void setDomainForumId(int domainForumId) {
    this.domainForumId = domainForumId;
  }

  public String getMemberForumUrl() {
    return memberForumUrl;
  }

  public void setMemberForumUrl(String memberForumUrl) {
    this.memberForumUrl = memberForumUrl;
  }

  @Override
  public String toString() {
    return "MemberDomain [id=" + id + ", memberId=" + memberId + ", domainForumId=" + domainForumId
        + ", memberForumUrl=" + memberForumUrl + "]";
  }

}
