package com.ef.model.member;

import com.ef.model.event.EventCriteriaMetadata;

public class MemberCriteriaData {
  public static final int KNOWN_CRITERIA_ID_ZOMATO_REVIEWS = 1;
  public static final int KNOWN_CRITERIA_ID_INSTAGRAM_FOLLOWER_COUNT = 2;
  public static final int KNOWN_CRITERIA_ID_ZOMATO_LEVEL = 3;
  public static final int KNOWN_CRITERIA_ID_YOUTUBE_FOLLOWER_COUNT = 4;
  public static final int KNOWN_CRITERIA_ID_ZOMATO_FOLLOWER_COUNT = 5;

  private String id;
  private int memberId;
  private EventCriteriaMetadata criteriaMetadata;
  private int memberCriteriaValue;

  public MemberCriteriaData(String id, int memberId, EventCriteriaMetadata criteriaMetadata, int memberCriteriaValue) {
    super();
    this.id = id;
    this.memberId = memberId;
    this.criteriaMetadata = criteriaMetadata;
    this.memberCriteriaValue = memberCriteriaValue;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((criteriaMetadata == null) ? 0 : criteriaMetadata.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MemberCriteriaData other = (MemberCriteriaData) obj;
    if (criteriaMetadata == null) {
      if (other.criteriaMetadata != null)
        return false;
    } else if (!criteriaMetadata.equals(other.criteriaMetadata))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "MemberCriteriaData [member_id=" + memberId + ", criteriaMetadata=" + criteriaMetadata
        + ", memberCriteriaValue=" + memberCriteriaValue + "]";
  }

}
