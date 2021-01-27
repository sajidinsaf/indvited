package com.ef.member.login.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private final Query<MemberTokenAuthBindingModel, Member> loginMember;
  private final List<Validator<MemberTokenAuthBindingModel, String>> validators;

  public TokenAuthService(Query<MemberTokenAuthBindingModel, Member> loginMember) {
    this.loginMember = loginMember;
    validators = new ArrayList<Validator<MemberTokenAuthBindingModel, String>>();
  }

  public TokenAuthService(Query<MemberTokenAuthBindingModel, Member> loginMember,
      List<Validator<MemberTokenAuthBindingModel, String>> validators) {
    this.loginMember = loginMember;
    this.validators = validators;
  }

  public Response<Member> loginMember(MemberTokenAuthBindingModel memberLoginData) {
    logUtil.info(logger, "registering member: " + memberLoginData);

    List<String> validationResults = validate(memberLoginData);

    if (validationResults != null && !validationResults.isEmpty()) {
      return new Response<Member>(validationResults, StatusCode.PRECONDITION_FAILED);
    }

    Member member = loginMember.data(memberLoginData);

    MemberLoginControl memberLoginControl = member.getMemberLoginControl();

    if (!memberLoginControl.getToken().equals(memberLoginData.getToken())) {
      logUtil.info(logger, "Invalid token value for email [", memberLoginData.getEmail(),
          "]. Value in request payload [", memberLoginData.getToken(), "]. Value in db [",
          memberLoginControl.getToken(), "]");
    }

    if (memberLoginControl.getExpires() < System.currentTimeMillis()) {
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
    return validationResults;
  }

  public static void main(String[] args) {
    MemberTokenAuthBindingModel mlbm = new MemberTokenAuthBindingModel("ashish@hotmail.com", "Bh1m@n1Street", 123456);
    System.out.println(new Gson().toJson(mlbm));
  }
}
