package com.ef.model.event.wrapper;

import java.util.List;

import com.ef.model.event.SubscriberDeliverableSubmission;
import com.ef.model.member.Member;

public class MemberEventDeliverableDataWrapper {

  private Member subscriber;
  private List<SubscriberDeliverableSubmission> deliverables;

  public MemberEventDeliverableDataWrapper() {
    // TODO Auto-generated constructor stub
  }

  public MemberEventDeliverableDataWrapper(Member subscriber, List<SubscriberDeliverableSubmission> deliverables) {
    super();
    this.subscriber = subscriber;
    this.deliverables = deliverables;
  }

  public Member getSubscriber() {
    return subscriber;
  }

  public void setSubscriber(Member subscriber) {
    this.subscriber = subscriber;
  }

  public List<SubscriberDeliverableSubmission> getDeliverables() {
    return deliverables;
  }

  public void setDeliverables(List<SubscriberDeliverableSubmission> deliverables) {
    this.deliverables = deliverables;
  }

  @Override
  public String toString() {
    return "MemberEventDeliverableDataWrapper [subscriber=" + subscriber + ", deliverables=" + deliverables + "]";
  }

}
