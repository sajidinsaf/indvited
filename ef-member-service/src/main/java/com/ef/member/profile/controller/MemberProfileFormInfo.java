package com.ef.member.profile.controller;

import java.util.List;

import com.ef.model.core.Domain;
import com.ef.model.event.EventCriteriaMetadata;

public class MemberProfileFormInfo {

  private final List<Domain> domains;
  private final List<EventCriteriaMetadata> forums;

  public MemberProfileFormInfo(List<Domain> domains, List<EventCriteriaMetadata> forums) {
    super();
    this.domains = domains;
    this.forums = forums;
  }

  public List<Domain> getDomains() {
    return domains;
  }

  public List<EventCriteriaMetadata> getForums() {
    return forums;
  }

}
