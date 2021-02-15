package com.ef.dataaccess.member;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Insert;
import com.ef.dataaccess.Query;
import com.ef.dataaccess.member.profile.MemberProfileDataAdaptor;
import com.ef.model.member.Member;
import com.ef.model.member.MemberCriteriaData;
import com.ef.model.member.MemberCriteriaDataBindingModel;
import com.ef.model.member.MemberDomain;
import com.ef.model.member.MemberDomainForumBindingModel;
import com.ef.model.member.MemberForumCriterionBindingModel;

@Component("insertBloggerProfileData")
public class InsertBloggerProfileData implements Insert<MemberForumCriterionBindingModel, Member> {

  private static final Logger logger = LoggerFactory.getLogger(InsertBloggerProfileData.class);

  private final ServiceLoggingUtil loggingUtil = new ServiceLoggingUtil();

  private final Insert<MemberCriteriaDataBindingModel, MemberCriteriaData> insertMemberCriteriaData;
  private final Insert<MemberDomainForumBindingModel, MemberDomain> insertMemberDomainForum;
  private final MemberProfileDataAdaptor memberProfileDataAdaptor;
  private final Query<Integer, Member> queryMemberById;

  @Autowired
  public InsertBloggerProfileData(
      @Qualifier("insertMemberCriteriaData") Insert<MemberCriteriaDataBindingModel, MemberCriteriaData> insertMemberCriteriaData,
      @Qualifier("insertMemberDomainForum") Insert<MemberDomainForumBindingModel, MemberDomain> insertMemberDomainForum,
      @Qualifier("queryMemberById") Query<Integer, Member> queryMemberById,
      @Qualifier("memberProfileDataAdaptor") MemberProfileDataAdaptor memberProfileDataAdaptor) {
    this.insertMemberCriteriaData = insertMemberCriteriaData;
    this.insertMemberDomainForum = insertMemberDomainForum;
    this.memberProfileDataAdaptor = memberProfileDataAdaptor;
    this.queryMemberById = queryMemberById;
  }

  @Override
  public Member data(MemberForumCriterionBindingModel input) {

    List<MemberCriteriaDataBindingModel> criteriaDataList = memberProfileDataAdaptor
        .buildMemberCriteriaDataBindingModel(input);

    List<MemberCriteriaData> memberCriteriaDataList = new ArrayList<MemberCriteriaData>();
    for (MemberCriteriaDataBindingModel mcdbm : criteriaDataList) {
      memberCriteriaDataList.add(insertMemberCriteriaData.data(mcdbm));
    }

    List<MemberDomainForumBindingModel> memberDomainForumList = memberProfileDataAdaptor
        .buildMemberDomainForumBindingModel(input);

    List<MemberDomain> memberDomainMappings = new ArrayList<MemberDomain>();
    for (MemberDomainForumBindingModel mdfbm : memberDomainForumList) {
      memberDomainMappings.add(insertMemberDomainForum.data(mdfbm));
    }

    Member member = queryMemberById.data(input.getMemberId());
    member.setMemberCriteriaDataList(memberCriteriaDataList);
    member.setMemberDomainMappings(memberDomainMappings);

    return member;

  }

}
