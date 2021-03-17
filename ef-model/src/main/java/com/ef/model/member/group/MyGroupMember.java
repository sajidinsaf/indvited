package com.ef.model.member.group;

import java.sql.Timestamp;

import com.ef.model.member.Member;

public class MyGroupMember {

  private MyGroup myGroup;
  private Member member;
  private Timestamp addedTimestamp;

  public MyGroupMember() {

  }

  public MyGroupMember(MyGroup myGroup, Member member, Timestamp addedTimestamp) {
    super();
    this.myGroup = myGroup;
    this.member = member;
    this.addedTimestamp = addedTimestamp;
  }

  public MyGroup getMyGroup() {
    return myGroup;
  }

  public void setMyGroup(MyGroup myGroup) {
    this.myGroup = myGroup;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public Timestamp getAddedTimestamp() {
    return addedTimestamp;
  }

  public void setAddedTimestamp(Timestamp addedTimestamp) {
    this.addedTimestamp = addedTimestamp;
  }

  @Override
  public String toString() {
    return "MyGroupMember [myGroup=" + myGroup + ", member=" + member + ", addedTimestamp=" + addedTimestamp + "]";
  }

}
