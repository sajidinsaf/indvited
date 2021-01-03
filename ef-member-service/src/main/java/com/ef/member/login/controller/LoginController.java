package com.ef.member.login.controller;

import static com.ef.member.login.controller.LoginControllerConstants.LOGIN_WITH_USERNAME_AND_PASSWORD;

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
import com.ef.member.login.service.LoginService;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;
import com.ef.model.response.Response;

/**
 * Handles requests for the event service.
 */
@Controller
public class LoginController {

  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

  private final LoginService loginService;

  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  @Autowired
  public LoginController(@Qualifier("loginService") LoginService registrationService) {
    this.loginService = registrationService;
  }

//  curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X POST --data '{"id":"1232455663","type":"PREvent","scheduledDate":"2020/12/25","scheduledTime":"18:03:51","location":"Mainland China","description":"Review Food Event"}' "http://secure.codeczar.co.uk/event-service/rest/event/publish"
  @PostMapping(LOGIN_WITH_USERNAME_AND_PASSWORD)
  public ResponseEntity<?> registerMember(@RequestBody MemberLoginBindingModel memberLoginData) {
    logUtil.debug(logger, "Logging in member: " + memberLoginData);

    Response<Member> loginStatus = loginService.loginMember(memberLoginData);

    if (loginStatus.getFailureReasons() != null && !loginStatus.getFailureReasons().isEmpty()) {
      logUtil.info(logger,
          "Error registering member: " + memberLoginData + " [" + loginStatus.getFailureReasons() + "]");
      return new ResponseEntity<List<String>>(loginStatus.getFailureReasons(),
          HttpStatus.valueOf(loginStatus.getStatusCode().name()));
    }

    return new ResponseEntity<Member>(loginStatus.getResponseResult(),
        HttpStatus.valueOf(loginStatus.getStatusCode().name()));
  }

}
