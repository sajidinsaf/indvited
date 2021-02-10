package com.ef.member.profile.controller;

import java.util.ArrayList;
import java.util.List;

import com.ef.model.core.Forum;
import com.ef.model.event.EventCriteriaMetadata;

public class MemberProfileForumInfo {

  private Forum forum;
  private List<EventCriteriaMetadata> profileCriteria;

  public MemberProfileForumInfo(Forum forum) {
    this.forum = forum;
    profileCriteria = new ArrayList<EventCriteriaMetadata>();
  }

  public Forum getForum() {
    return forum;
  }

  public void setForum(Forum forum) {
    this.forum = forum;
  }

  public void add(EventCriteriaMetadata e) {
    profileCriteria.add(e);
  }

  public List<EventCriteriaMetadata> getProfileCriteria() {
    return profileCriteria;
  }

  public void setProfileCriteria(List<EventCriteriaMetadata> profileCriteria) {
    this.profileCriteria = profileCriteria;
  }

  @Override
  public String toString() {
    return "MemberProfileForumInfo [forum=" + forum + ", profileCriteria=" + profileCriteria + "]";
  }

}