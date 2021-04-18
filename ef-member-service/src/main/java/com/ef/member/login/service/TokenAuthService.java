package com.ef.member.login.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.common.validation.Validator;
import com.ef.dataaccess.Query;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginControl;
import com.ef.model.member.MemberTokenAuthBindingModel;
import com.google.gson.Gson;

public class TokenAuthService {
  private static final Logger logger = LoggerFactory.getLogger(TokenAuthService.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();
  private final Query<MemberTokenAuthBindingModel, Member> loginMemberByIdAndAuthToken;
  private final List<Validator<MemberTokenAuthBindingModel, String>> validators;

  public TokenAuthService(
      @Qualifier("loginMemberByIdAndAuthToken ") Query<MemberTokenAuthBindingModel, Member> loginMemberByIdAndAuthToken) {
    this.loginMemberByIdAndAuthToken = loginMemberByIdAndAuthToken;
    validators = new ArrayList<Validator<MemberTokenAuthBindingModel, String>>();
  }

  public TokenAuthService(
      @Qualifier("loginMemberByIdAndAuthToken ") Query<MemberTokenAuthBindingModel, Member> loginMemberByIdAndAuthToken,
      List<Validator<MemberTokenAuthBindingModel, String>> validators) {
    this.loginMemberByIdAndAuthToken = loginMemberByIdAndAuthToken;
    this.validators = validators;
  }

  public Response<Member> loginMember(MemberTokenAuthBindingModel memberLoginData) {
    logUtil.info(logger, "registering member: " + memberLoginData);

    List<String> validationResults = validate(memberLoginData);

    if (validationResults != null && !validationResults.isEmpty()) {
      return new Response<Member>(validationResults, StatusCode.PRECONDITION_FAILED);
    }

    Member member = loginMemberByIdAndAuthToken.data(memberLoginData);

    MemberLoginControl memberLoginControl = member.getMemberLoginControl();

    if (!memberLoginControl.getToken().equals(memberLoginData.getToken())
        || memberLoginControl.getExpires() < System.currentTimeMillis()) {
      logUtil.info(logger, "Invalid token value for email [", memberLoginData.getEmail(),
          "]. Value in request payload [", memberLoginData.getToken(), "]. Value in db [",
          memberLoginControl.getToken(), "]");

      validationResults = new ArrayList<String>();
      validationResults.add("Authentication token invalid or expired");
      return new Response<Member>(validationResults, StatusCode.PRECONDITION_FAILED);
    }

    return new Response<Member>(member, StatusCode.OK);
  }

  private List<String> validate(MemberTokenAuthBindingModel memberLoginData) {
    List<String> validationResults = new ArrayList<String>();
    for (Validator<MemberTokenAuthBindingModel, String> validator : validators) {
      String result = validator.validate(memberLoginData);
      if (result != null) {
        validationResults.add(result);
      }
    }
    if (memberLoginData == null || memberLoginData.getToken() == null || memberLoginData.getMemberId() < 1) {
      validationResults.add("Please provide Member Id and Token");
    }
    return validationResults;
  }

  public static void main(String[] args) {
    MemberTokenAuthBindingModel mlbm = new MemberTokenAuthBindingModel("ashish@hotmail.com", "Bh1m@n1Street", 123456);
    System.out.println(new Gson().toJson(mlbm));
  }
}
