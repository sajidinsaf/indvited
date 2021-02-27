package com.ef.model.member;

import com.ef.model.event.EventCriteriaMetadata;

public class MemberCriteriaData {
  public static final int KNOWN_CRITERIA_ID_ZOMATO_REVIEWS = 1;
  public static final int KNOWN_CRITERIA_ID_INSTAGRAM_FOLLOWER_COUNT = 2;
  public static final int KNOWN_CRITERIA_ID_ZOMATO_LEVEL = 3;
  public static final int KNOWN_CRITERIA_ID_YOUTUBE_FOLLOWER_COUNT = 4;
  public static final int KNOWN_CRITERIA_ID_ZOMATO_FOLLOWER_COUNT = 5;

  private int id;
  private int memberId;
  private EventCriteriaMetadata criteriaMetadata;
  private int memberCriteriaValue;

  public MemberCriteriaData(int id, int memberId, EventCriteriaMetadata criteriaMetadata, int memberCriteriaValue) {
    super();
    this.id = id;
    this.memberId = memberId;
    this.criteriaMetadata = criteriaMetadata;
    this.memberCriteriaValue = memberCriteriaValue;
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

  public void setMember_id(int memberId) {
    this.memberId = memberId;
  }

  public EventCriteriaMetadata getCriteriaMetadata() {
    return criteriaMetadata;
  }

  public void setCriteriaMetadata(EventCriteriaMetadata criteriaMetadata) {
    this.criteriaMetadata = criteriaMetadata;
  }

  public int getMemberCriteriaValue() {
    return memberCriteriaValue;
  }

  public void setMemberCriteriaValue(int memberCriteriaValue) {
    this.memberCriteriaValue = memberCriteriaValue;
  }

  @Override
  public String toString() {
    return "MemberCriteriaData [member_id=" + memberId + ", criteriaMetadata=" + criteriaMetadata
        + ", memberCriteriaValue=" + memberCriteriaValue + "]";
  }

}
