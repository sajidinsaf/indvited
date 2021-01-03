package com.ef.registration.controller;

import static com.ef.registration.controller.RegistrationControllerConstants.REGISTER_MEMBER;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;
import com.ef.model.response.Response;
import com.ef.registration.service.member.RegistrationService;

/**
 * Handles requests for the event service.
 */
@Controller
public class RegistrationController {

  private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

  private final RegistrationService registrationService;

  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  @Autowired
  public RegistrationController(@Qualifier("registrationService") RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

//  curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X POST --data '{"id":"1232455663","type":"PREvent","scheduledDate":"2020/12/25","scheduledTime":"18:03:51","location":"Mainland China","description":"Review Food Event"}' "http://secure.codeczar.co.uk/event-service/rest/event/publish"
  @PostMapping(REGISTER_MEMBER)
  public ResponseEntity<?> registerMember(@RequestBody MemberRegistrationBindingModel memberRegistationData) {
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

}
