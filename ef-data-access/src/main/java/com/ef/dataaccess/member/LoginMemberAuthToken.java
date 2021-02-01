package com.ef.dataaccess.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.Query;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;
import com.ef.model.member.MemberLoginControl;
import com.ef.model.member.MemberTokenAuthBindingModel;
import com.ef.model.member.MemberType;

@Component(value = "loginMemberAuthToken")
public class LoginMemberAuthToken implements Query<MemberTokenAuthBindingModel, Member> {

  private static final Logger logger = LoggerFactory.getLogger(LoginMemberAuthToken.class);
  private ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Query<String, String> emailFormatterForDb;
  private final Query<Integer, MemberLoginControl> queryLoginControlByMemberId;
  private final Query<MemberLoginBindingModel, Member> queryMemberByEmailAndMemberType;

  @Autowired
  public LoginMemberAuthToken(@Qualifier("emailFormatterForDb") Query<String, String> emailFormatterForDb,
      @Qualifier("queryMemberByEmailAndMemberType") Query<MemberLoginBindingModel, Member> queryMemberByEmailAndMemberType,
      @Qualifier("queryMemberLoginControlByMemberId") Query<Integer, MemberLoginControl> queryLoginControlByMemberId) {
    this.emailFormatterForDb = emailFormatterForDb;
    this.queryLoginControlByMemberId = queryLoginControlByMemberId;
    this.queryMemberByEmailAndMemberType = queryMemberByEmailAndMemberType;
  }

  @Override
  public Member data(MemberTokenAuthBindingModel data) {

    MemberType memberType = null;
    String email = emailFormatterForDb.data(data.getEmail());

    memberType = data.getMemberType();

    Member member = queryMemberByEmailAndMemberType.data(new MemberLoginBindingModel(email, null, memberType));

    MemberLoginControl memberLoginControl = null;

    try {
      memberLoginControl = queryLoginControlByMemberId.data(member.getId());
    } catch (EmptyResultDataAccessException e) {
      logUtil.info(logger, "No Member login control information found for email[", email,
          "] this should not have happened because MemberType was found for this member!");
      return null;
    }

    member.setMemberLoginControl(memberLoginControl);

    return member;
  }

}
