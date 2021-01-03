package com.ef.registration.service.member;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.validation.Validator;
import com.ef.dataaccess.Insert;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;
import com.ef.model.response.Response;
import com.ef.model.response.StatusCode;

public class RegistrationService {
  private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();
  private final Insert<MemberRegistrationBindingModel, Member> insertMember;
  private final List<Validator<MemberRegistrationBindingModel, String>> validators;

  public RegistrationService(Insert<MemberRegistrationBindingModel, Member> insertMember) {
    this.insertMember = insertMember;
    validators = new ArrayList<Validator<MemberRegistrationBindingModel, String>>();
  }

  public RegistrationService(Insert<MemberRegistrationBindingModel, Member> insertMember,
      List<Validator<MemberRegistrationBindingModel, String>> validators) {
    this.insertMember = insertMember;
    this.validators = validators;
  }

  public Response<Member> registerMember(MemberRegistrationBindingModel memberRegistationData) {
    logUtil.info(logger, "registering member: " + memberRegistationData);

    List<String> validationResults = validate(memberRegistationData);

    if (validationResults != null && !validationResults.isEmpty()) {
      return new Response<Member>(validationResults, StatusCode.PRECONDITION_FAILED);
    }

    Member member = insertMember.data(memberRegistationData);
    return new Response<Member>(member, StatusCode.OK);
  }

  private List<String> validate(MemberRegistrationBindingModel memberRegistationData) {
    List<String> validationResults = new ArrayList<String>();
    for (Validator<MemberRegistrationBindingModel, String> validator : validators) {
      String result = validator.validate(memberRegistationData);
      if (result != null) {
        validationResults.add(result);
      }
    }
    return validationResults;
  }
}
