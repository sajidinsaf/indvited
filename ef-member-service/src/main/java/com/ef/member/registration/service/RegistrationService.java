package com.ef.member.registration.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.MessagePacket;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.common.validation.Validator;
import com.ef.common.work.Worker;
import com.ef.dataaccess.Insert;
import com.ef.member.registration.model.RegistrationPreconfirmationMessageModel;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;
import com.ef.model.member.PreconfirmationMemberRegistrationModel;

public class RegistrationService {
  private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();
  private final Insert<MemberRegistrationBindingModel, PreconfirmationMemberRegistrationModel> insertMember;
  private final List<Validator<MemberRegistrationBindingModel, String>> validators;
  private final Worker<MessagePacket<RegistrationPreconfirmationMessageModel>, Response<String>> confirmationEmailSender;

  public RegistrationService(
      Insert<MemberRegistrationBindingModel, PreconfirmationMemberRegistrationModel> insertMember,
      Worker<MessagePacket<RegistrationPreconfirmationMessageModel>, Response<String>> confirmationEmailSender) {
    this.insertMember = insertMember;
    validators = new ArrayList<Validator<MemberRegistrationBindingModel, String>>();
    this.confirmationEmailSender = confirmationEmailSender;
  }

  public RegistrationService(
      Insert<MemberRegistrationBindingModel, PreconfirmationMemberRegistrationModel> insertMember,
      List<Validator<MemberRegistrationBindingModel, String>> validators,
      Worker<MessagePacket<RegistrationPreconfirmationMessageModel>, Response<String>> confirmationEmailSender) {
    this.insertMember = insertMember;
    this.validators = validators;
    this.confirmationEmailSender = confirmationEmailSender;
  }

  public Response<Member> registerMember(MemberRegistrationBindingModel memberRegistationData) {
    logUtil.info(logger, "registering member: " + memberRegistationData);

    List<String> validationResults = validate(memberRegistationData);

    if (validationResults != null && !validationResults.isEmpty()) {
      return new Response<Member>(validationResults, StatusCode.PRECONDITION_FAILED);
    }

    PreconfirmationMemberRegistrationModel preRegisteredMember = insertMember.data(memberRegistationData);

    String message = sendMessage(preRegisteredMember);
    logUtil.debug(logger, message);

    return new Response<Member>(preRegisteredMember.getMember(), StatusCode.OK);
  }

  private String sendMessage(PreconfirmationMemberRegistrationModel preRegisteredMember) {
    RegistrationPreconfirmationMessageModel rcmModel = new RegistrationPreconfirmationMessageModel(preRegisteredMember);

    @SuppressWarnings("unchecked")
    MessagePacket<RegistrationPreconfirmationMessageModel> job = new MessagePacket<RegistrationPreconfirmationMessageModel>(
        rcmModel);
    job.setPayload(rcmModel);
    return confirmationEmailSender.perform(job).getResponseResult();
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
