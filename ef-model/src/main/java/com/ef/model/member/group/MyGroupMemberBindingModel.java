package com.ef.model.member.group;

public class MyGroupMemberBindingModel {

  private int myGroupId;
  private int memberId;

  public MyGroupMemberBindingModel() {

  }

  public MyGroupMemberBindingModel(int myGroupId, int memberId) {
    super();
    this.myGroupId = myGroupId;
    this.memberId = memberId;
  }

  public int getMyGroupId() {
    return myGroupId;
  }

  public void setMyGroupId(int myGroupId) {
    this.myGroupId = myGroupId;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  @Override
  public String toString() {
    return "MyGroupMemberBindingModel [myGroupId=" + myGroupId + ", memberId=" + memberId + "]";
  }

}
