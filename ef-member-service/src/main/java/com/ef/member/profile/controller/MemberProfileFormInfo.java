package com.ef.member.profile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ef.dataaccess.core.ForumCache;
import com.ef.model.core.Domain;
import com.ef.model.event.EventCriteriaMetadata;

public class MemberProfileFormInfo {

  private final List<Domain> domains;
  private final List<MemberProfileForumInfo> forums;

  public MemberProfileFormInfo(List<Domain> domains, List<EventCriteriaMetadata> criteria, ForumCache forumCache) {
    super();
    this.domains = domains;

    Map<Integer, MemberProfileForumInfo> forumMap = new HashMap<Integer, MemberProfileForumInfo>();

    for (EventCriteriaMetadata ecm : criteria) {
      // ecm.setForum(forumCache.getForum(ecm.getForumId()));

      MemberProfileForumInfo mpfi = forumMap.get(ecm.getForumId());

      if (mpfi == null) {
        mpfi = new MemberProfileForumInfo(forumCache.getForum(ecm.getForumId()));
      }

      mpfi.add(ecm);
      forumMap.put(ecm.getForumId(), mpfi);

    }

    forums = new ArrayList<MemberProfileForumInfo>();

    forums.addAll(forumMap.values());

  }

  public List<Domain> getDomains() {
    return domains;
  }

  public List<MemberProfileForumInfo> getForums() {
    return forums;
  }

}
