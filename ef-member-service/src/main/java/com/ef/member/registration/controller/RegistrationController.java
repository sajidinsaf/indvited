package com.ef.member.registration.controller;

import static com.ef.member.registration.controller.RegistrationControllerConstants.CONFIRM_REGISTER_MEMBER;
import static com.ef.member.registration.controller.RegistrationControllerConstants.REGISTER_MEMBER;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.common.message.Response;
import com.ef.member.registration.service.RegistrationConfirmationService;
import com.ef.member.registration.service.RegistrationService;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;

/**
 * Handles requests for the event service.
 */
@Controller
public class RegistrationController {

  private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final RegistrationService registrationService;
  private final RegistrationConfirmationService registrationConfirmationService;

  @Autowired
  public RegistrationController(@Qualifier("registrationService") RegistrationService registrationService,
      @Qualifier("registrationConfirmationService") RegistrationConfirmationService registrationConfirmationService) {
    this.registrationService = registrationService;
    this.registrationConfirmationService = registrationConfirmationService;
  }

//  curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X POST --data '{"id":"1232455663","type":"PREvent","scheduledDate":"2020/12/25","scheduledTime":"18:03:51","location":"Mainland China","description":"Review Food Event"}' "http://secure.codeczar.co.uk/event-service/rest/event/publish"
  @PostMapping(REGISTER_MEMBER)
  public @ResponseBody ResponseEntity<?> registerMember(
      @RequestBody MemberRegistrationBindingModel memberRegistationData) {
    logUtil.debug(logger, "Registering member: " + memberRegistationData);

    Response<Member> registrationStatus = registrationService.registerMember(memberRegistationData);

    if (registrationStatus.getFailureReasons() != null && !registrationStatus.getFailureReasons().isEmpty()) {
      logUtil.info(logger,
          "Error registering member: " + memberRegistationData + " [" + registrationStatus.getFailureReasons() + "]");
      return new ResponseEntity<List<String>>(registrationStatus.getFailureReasons(),
          HttpStatus.valueOf(registrationStatus.getStatusCode().name()));
    }

    return new ResponseEntity<Member>(registrationStatus.getResponseResult(),
        HttpStatus.valueOf(registrationStatus.getStatusCode().name()));
  }

  @GetMapping(CONFIRM_REGISTER_MEMBER + "/{confirmationCode}")
  public ModelAndView confirmMember(@PathVariable String confirmationCode) {
    logUtil.debug(logger, "Confirmation Code: " + confirmationCode);

    Response<Member> confirmationStatus = registrationConfirmationService.confirmMember(confirmationCode);
    Member member = confirmationStatus.getResponseResult();

    if ((confirmationStatus.getFailureReasons() != null && !confirmationStatus.getFailureReasons().isEmpty())
        || member == null) {
      logUtil.info(logger,
          "Error confirming code: " + confirmationCode + " [" + confirmationStatus.getFailureReasons() + "]");
      throw new RuntimeException("Failed to confirm registration: " + confirmationStatus.getFailureReasons());
//      return new ResponseEntity<List<String>>(confirmationStatus.getFailureReasons(),
//          HttpStatus.valueOf(confirmationStatus.getStatusCode().name()));
    }

    ModelAndView model = new ModelAndView();
    model.setViewName("confirmMemberRegistration");
    model.addObject("member", member);

    return model;

//    return new ResponseEntity<Member>(confirmationStatus.getResponseResult(),
//        HttpStatus.valueOf(confirmationStatus.getStatusCode().name()));
  }
}
