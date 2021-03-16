package com.ef.model.event.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.ef.model.event.EventStatusMeta;
import com.ef.model.event.PREvent;

public class PREventWrapper extends PREvent {

  private EventStatusMeta eventStatus;

  private List<MemberEventDeliverableDataWrapper> memberDeliverableSubmission;

  public PREventWrapper(PREvent prEvent) {
    super(prEvent);
    memberDeliverableSubmission = new ArrayList<MemberEventDeliverableDataWrapper>();
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

}
