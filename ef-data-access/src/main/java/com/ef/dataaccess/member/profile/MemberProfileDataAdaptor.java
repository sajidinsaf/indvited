package com.ef.dataaccess.member.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.dataaccess.core.DomainForumCache;
import com.ef.model.core.DomainForum;
import com.ef.model.core.Forum;
import com.ef.model.member.MemberDomainForumBindingModel;
import com.ef.model.member.MemberForumCriterionBindingModel;

@Component("memberProfileDataAdaptor")
public class MemberProfileDataAdaptor {

  private final DomainForumCache domainForumCache;

  @Autowired
  public MemberProfileDataAdaptor(DomainForumCache domainForumCache) {
    this.domainForumCache = domainForumCache;
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

}
