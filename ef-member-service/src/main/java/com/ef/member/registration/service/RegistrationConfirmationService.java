package com.ef.member.registration.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.common.MapBasedContext;
import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.MessagePacket;
import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.common.validation.Validator;
import com.ef.common.work.Worker;
import com.ef.dataaccess.Insert;
import com.ef.member.registration.model.RegistrationConfirmationMessageModel;
import com.ef.model.member.Member;

public class RegistrationConfirmationService {
  private static final Logger logger = LoggerFactory.getLogger(RegistrationConfirmationService.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();
  private final Insert<String, Member> confirmMember;
  private final List<Validator<String, String>> validators;
  private final Worker<MessagePacket<RegistrationConfirmationMessageModel>, Response<String>, MapBasedContext> registrationConfirmedEmailSender;

  public RegistrationConfirmationService(Insert<String, Member> confirmMember,
      List<Validator<String, String>> validators,
      Worker<MessagePacket<RegistrationConfirmationMessageModel>, Response<String>, MapBasedContext> registrationConfirmedEmailSender) {
    this.confirmMember = confirmMember;
    this.validators = validators;
    this.registrationConfirmedEmailSender = registrationConfirmedEmailSender;
  }

  public Response<Member> confirmMember(String confirmationCode) {
    logUtil.info(logger, "confirming registration code: " + confirmationCode);

    List<String> validationResults = validate(confirmationCode);

    if (validationResults != null && !validationResults.isEmpty()) {
      return new Response<Member>(validationResults, StatusCode.PRECONDITION_FAILED);
    }

    Member member = confirmMember.data(confirmationCode);
    if (member == null) {
      return new Response<Member>(
          Arrays.asList("Registration confirmation failed. Invalid confirmation code or confirmation deadline passed"),
          StatusCode.PRECONDITION_FAILED);
    }

    String message = sendMessage(member);
    logUtil.debug(logger, message);

    return new Response<Member>(member, StatusCode.OK);
  }

  private String sendMessage(Member member) {
    RegistrationConfirmationMessageModel registrationConfirmationMessageModel = new RegistrationConfirmationMessageModel(
        member);
    @SuppressWarnings("unchecked")
    MessagePacket<RegistrationConfirmationMessageModel> job = new MessagePacket<RegistrationConfirmationMessageModel>(
        registrationConfirmationMessageModel);
    return registrationConfirmedEmailSender.perform(job, new MapBasedContext()).getResponseResult();
  }

  private List<String> validate(String memberRegistationData) {
    List<String> validationResults = new ArrayList<String>();
    for (Validator<String, String> validator : validators) {
      String result = validator.validate(memberRegistationData);
      if (result != null) {
        validationResults.add(result);
      }
    }
    return validationResults;
  }

}
