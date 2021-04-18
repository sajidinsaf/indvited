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
import com.ef.model.member.MemberLoginControl;
import com.ef.model.member.MemberTokenAuthBindingModel;

@Component(value = "loginMemberByIdAndAuthToken")
public class LoginMemberByIdAndAuthToken implements Query<MemberTokenAuthBindingModel, Member> {

  private static final Logger logger = LoggerFactory.getLogger(LoginMemberByIdAndAuthToken.class);
  private ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final Query<Integer, MemberLoginControl> queryLoginControlByMemberId;
  private final Query<Integer, Member> queryMemberById;

  @Autowired
  public LoginMemberByIdAndAuthToken(@Qualifier("queryMemberById") Query<Integer, Member> queryMemberById,
      @Qualifier("queryMemberLoginControlByMemberId") Query<Integer, MemberLoginControl> queryLoginControlByMemberId) {

    this.queryLoginControlByMemberId = queryLoginControlByMemberId;
    this.queryMemberById = queryMemberById;
  }

  @Override
  public Member data(MemberTokenAuthBindingModel input) {

    MemberLoginControl memberLoginControl = null;

    try {
      memberLoginControl = queryLoginControlByMemberId.data(input.getMemberId());
    } catch (EmptyResultDataAccessException e) {
      logUtil.info(logger, "No Member login control information found for id[", input.getMemberId(), "]");
      return null;
    }

    Member member = queryMemberById.data(input.getMemberId());

    member.setMemberLoginControl(memberLoginControl);

    return member;
  }

}
