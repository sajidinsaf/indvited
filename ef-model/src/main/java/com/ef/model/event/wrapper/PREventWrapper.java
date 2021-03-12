package com.ef.model.event.wrapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ef.model.event.EventDeliverableMetadata;
import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREvent;
import com.ef.model.event.SubscriberDeliverableSubmission;
import com.ef.model.member.Member;
import com.ef.model.member.MemberAddress;
import com.ef.model.member.MemberCriteriaData;
import com.ef.model.member.MemberDomain;
import com.ef.model.member.MemberLoginControl;
import com.ef.model.member.MemberType;

public class PREventWrapper extends PREvent {

  private EventStatusMeta eventStatus;

  private List<MemberEventDeliverableDataWrapper> memberDeliverableSubmission;

  public PREventWrapper(PREvent prEvent) {
    super(prEvent);
    memberDeliverableSubmission = new ArrayList<MemberEventDeliverableDataWrapper>();

    MemberEventDeliverableDataWrapper dummy = new MemberEventDeliverableDataWrapper(dummyMember(), dummySubmission());
    memberDeliverableSubmission.add(dummy);
  }

  public EventStatusMeta getEventStatus() {
    return eventStatus;
  }

  public void setEventStatus(EventStatusMeta eventStatus) {
    this.eventStatus = eventStatus;
  }

  public List<MemberEventDeliverableDataWrapper> getMemberDeliverableSubmission() {
    return memberDeliverableSubmission;
  }

  public void setMemberDeliverableSubmission(List<MemberEventDeliverableDataWrapper> memberDeliverableSubmission) {
    this.memberDeliverableSubmission = memberDeliverableSubmission;
  }

  @Override
  public String toString() {
    return "PREventWrapper [eventStatus=" + eventStatus + ", memberDeliverableSubmission=" + memberDeliverableSubmission
        + "]";
  }

  private Member dummyMember() {
    int id = 11111111;
    String firstname = "James";
    String lastname = "Bond";
    String email = "james.bond@mi6.org";
    String gender = "M";
    String phone = "555555555";
    String password = "sgsjst3ntej45ngdg";
    MemberType memberType = new MemberType(3, "blogger");
    Timestamp dateRegistered = new Timestamp(System.currentTimeMillis());
    Timestamp timestampOfLastLogin = new Timestamp(System.currentTimeMillis());
    MemberLoginControl memberLoginControl = new MemberLoginControl(id, password, dateRegistered, dateRegistered);
    boolean enabled = true;
    MemberAddress memberAddress = new MemberAddress(4444, id, "13 Friday Street", "Fleet Stree", "", "London", "UK",
        "EC1 2SA");
    List<MemberCriteriaData> memberCriteriaDataList = new ArrayList<MemberCriteriaData>();
    List<MemberDomain> memberDomainMappings = new ArrayList<MemberDomain>();

    return new Member(id, firstname, lastname, email, gender, phone, memberType, dateRegistered, timestampOfLastLogin,
        enabled);

  }

  private List<SubscriberDeliverableSubmission> dummySubmission() {
    SubscriberDeliverableSubmission s = new SubscriberDeliverableSubmission(55325, 11111111,
        "http://zomato.com/reviews/2432424", new EventDeliverableMetadata(1, "Zomato Review", "Zomato Review"));
    return Arrays.asList(s);
  }

}
