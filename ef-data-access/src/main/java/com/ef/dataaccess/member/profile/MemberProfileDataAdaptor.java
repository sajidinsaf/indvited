package com.ef.dataaccess.member.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.core.DomainForumCache;
import com.ef.dataaccess.event.EventCriteriaMetadataCache;
import com.ef.model.core.DomainForum;
import com.ef.model.core.Forum;
import com.ef.model.member.MemberCriteriaDataBindingModel;
import com.ef.model.member.MemberDomainForumBindingModel;
import com.ef.model.member.MemberForumCriterionBindingModel;

@Component("memberProfileDataAdaptor")
public class MemberProfileDataAdaptor {

  private final DomainForumCache domainForumCache;
  private final EventCriteriaMetadataCache criteriaMetadataCache;

  @Autowired
  public MemberProfileDataAdaptor(DomainForumCache domainForumCache, EventCriteriaMetadataCache criteriaMetadataCache) {
    this.domainForumCache = domainForumCache;
    this.criteriaMetadataCache = criteriaMetadataCache;
  }

  /**
   * Method to overcome UI limitations to create a
   * 
   * @param input
   * @return
   */
  public List<MemberDomainForumBindingModel> buildMemberDomainForumBindingModel(
      MemberForumCriterionBindingModel input) {
    List<MemberDomainForumBindingModel> memberDomainForumBindingModels = new ArrayList<MemberDomainForumBindingModel>();

    List<Integer> enabledDomainIds = input.getEnabledDomainIds();
    Map<Integer, String> memberForumUrls = input.getMemberForumUrls();
    int memberId = input.getMemberId();

    for (Integer domainId : enabledDomainIds) {
      Set<Forum> forums = domainForumCache.getForumsByDomainId(domainId);
      for (Forum forum : forums) {
        DomainForum df = domainForumCache.getDomainForum(domainId, forum.getId());
        String url = memberForumUrls.get(forum.getId());
        MemberDomainForumBindingModel mdfbm = new MemberDomainForumBindingModel(memberId, df.getId(), url);
        memberDomainForumBindingModels.add(mdfbm);
      }
    }

    return memberDomainForumBindingModels;

  }

  public List<MemberCriteriaDataBindingModel> buildMemberCriteriaDataBindingModel(
      MemberForumCriterionBindingModel input) {
    Map<Integer, Integer> criteriaValueMap = input.getCriteriaValueMap();
    List<MemberCriteriaDataBindingModel> mcdbmList = new ArrayList<MemberCriteriaDataBindingModel>();
    for (int eventCriteriaId : criteriaValueMap.keySet()) {
      MemberCriteriaDataBindingModel mcdbm = new MemberCriteriaDataBindingModel(input.getMemberId(), eventCriteriaId,
          criteriaValueMap.get(eventCriteriaId));
      mcdbmList.add(mcdbm);
    }

    return mcdbmList;
  }

}
