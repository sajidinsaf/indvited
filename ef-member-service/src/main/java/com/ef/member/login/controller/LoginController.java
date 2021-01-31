package com.ef.member.login.controller;

import static com.ef.member.login.controller.LoginControllerConstants.LOGIN_WITH_EMAIL_AND_TOKEN_ADMIN;
import static com.ef.member.login.controller.LoginControllerConstants.LOGIN_WITH_EMAIL_AND_TOKEN_BLOGGER;
import static com.ef.member.login.controller.LoginControllerConstants.LOGIN_WITH_EMAIL_AND_TOKEN_PR;
import static com.ef.member.login.controller.LoginControllerConstants.LOGIN_WITH_USERNAME_AND_PASSWORD_ADMIN;
import static com.ef.member.login.controller.LoginControllerConstants.LOGIN_WITH_USERNAME_AND_PASSWORD_BLOGGER;
import static com.ef.member.login.controller.LoginControllerConstants.LOGIN_WITH_USERNAME_AND_PASSWORD_PR;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.ef.common.message.Response;
import com.ef.dataaccess.member.MemberTypeCache;
import com.ef.member.login.service.LoginService;
import com.ef.member.login.service.TokenAuthService;
import com.ef.model.member.Member;
import com.ef.model.member.MemberLoginBindingModel;
import com.ef.model.member.MemberLoginControl;
import com.ef.model.member.MemberTokenAuthBindingModel;
import com.ef.model.member.MemberType;

/**
 * Handles requests for the event service.
 */
@Controller
public class LoginController {

  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

  private final LoginService loginService;
  private final TokenAuthService tokenAuthService;

  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final MemberType PR;
  private final MemberType ADMIN;
  private final MemberType BLOGGER;

  @Autowired
  public LoginController(@Qualifier("loginService") LoginService loginService,
      @Qualifier("tokenAuthService") TokenAuthService tokenAuthService,
      @Qualifier("memberTypeCache") MemberTypeCache memberTypeCache) {
    this.loginService = loginService;
    this.tokenAuthService = tokenAuthService;
    this.ADMIN = memberTypeCache.getMemberType(1);
    this.PR = memberTypeCache.getMemberType(2);
    this.BLOGGER = memberTypeCache.getMemberType(3);

  }

  @PostMapping(LOGIN_WITH_USERNAME_AND_PASSWORD_ADMIN)
  public ResponseEntity<?> adminLoginWithEmailAndPassword(@RequestBody MemberLoginBindingModel memberLoginData,
      HttpSession httpSession) {
    memberLoginData.setMemberType(ADMIN);
    return loginWithEmailAndPassword(memberLoginData, httpSession);
  }

//  curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X POST --data '{"id":"1232455663","type":"PREvent","scheduledDate":"2020/12/25","scheduledTime":"18:03:51","location":"Mainland China","description":"Review Food Event"}' "http://secure.codeczar.co.uk/event-service/rest/event/publish"
  @PostMapping(LOGIN_WITH_USERNAME_AND_PASSWORD_PR)
  public ResponseEntity<?> prLoginWithEmailAndPassword(@RequestBody MemberLoginBindingModel memberLoginData,
      HttpSession httpSession) {
    memberLoginData.setMemberType(PR);
    return loginWithEmailAndPassword(memberLoginData, httpSession);
  }

  @PostMapping(LOGIN_WITH_USERNAME_AND_PASSWORD_BLOGGER)
  public ResponseEntity<?> bloggerLoginWithEmailAndPassword(@RequestBody MemberLoginBindingModel memberLoginData,
      HttpSession httpSession) {

    memberLoginData.setMemberType(BLOGGER);
    return loginWithEmailAndPassword(memberLoginData, httpSession);
  }

  private ResponseEntity<?> loginWithEmailAndPassword(MemberLoginBindingModel memberLoginData,
      HttpSession httpSession) {
    logUtil.debug(logger, "Logging in member: " + memberLoginData);

    Response<Member> loginStatus = loginService.loginMember(memberLoginData);

    if (loginStatus.getFailureReasons() != null && !loginStatus.getFailureReasons().isEmpty()) {
      logUtil.info(logger,
          "Error logging in member: " + memberLoginData + " [" + loginStatus.getFailureReasons() + "]");
      if (httpSession != null) {
        httpSession.invalidate();
      }
      return new ResponseEntity<List<String>>(loginStatus.getFailureReasons(),
          HttpStatus.valueOf(loginStatus.getStatusCode().name()));
    }

    Member member = loginStatus.getResponseResult();
    httpSession.setAttribute("member", member);

    return new ResponseEntity<MemberLoginControl>(member.getMemberLoginControl(),
        HttpStatus.valueOf(loginStatus.getStatusCode().name()));
  }

  @PostMapping(LOGIN_WITH_EMAIL_AND_TOKEN_ADMIN)
  public ResponseEntity<?> loginWithEmailAndTokenAdmin(@RequestBody MemberTokenAuthBindingModel memberLoginData,
      HttpSession httpSession) {
    memberLoginData.setMemberType(ADMIN);
    return loginWithEmailAndToken(memberLoginData, httpSession);
  }

  @PostMapping(LOGIN_WITH_EMAIL_AND_TOKEN_PR)
  public ResponseEntity<?> loginWithEmailAndTokenPr(@RequestBody MemberTokenAuthBindingModel memberLoginData,
      HttpSession httpSession) {
    memberLoginData.setMemberType(PR);
    return loginWithEmailAndToken(memberLoginData, httpSession);
  }

  @PostMapping(LOGIN_WITH_EMAIL_AND_TOKEN_BLOGGER)
  public ResponseEntity<?> loginWithEmailAndTokenBlogger(@RequestBody MemberTokenAuthBindingModel memberLoginData,
      HttpSession httpSession) {
    memberLoginData.setMemberType(BLOGGER);
    return loginWithEmailAndToken(memberLoginData, httpSession);
  }

  private ResponseEntity<?> loginWithEmailAndToken(MemberTokenAuthBindingModel memberLoginData,
      HttpSession httpSession) {
    logUtil.debug(logger, "Logging in member: " + memberLoginData);

    Response<Member> loginStatus = tokenAuthService.loginMember(memberLoginData);

    if (loginStatus.getFailureReasons() != null && !loginStatus.getFailureReasons().isEmpty()) {
      logUtil.info(logger,
          "Error logging in member: " + memberLoginData + " [" + loginStatus.getFailureReasons() + "]");
      if (httpSession != null) {
        httpSession.invalidate();
      }
      return new ResponseEntity<List<String>>(loginStatus.getFailureReasons(),
          HttpStatus.valueOf(loginStatus.getStatusCode().name()));
    }

    Member member = loginStatus.getResponseResult();
    httpSession.setAttribute("member", member);

    return new ResponseEntity<MemberLoginControl>(member.getMemberLoginControl(),
        HttpStatus.valueOf(loginStatus.getStatusCode().name()));
  }
}
