package com.ef.member.profile.controller;

import static com.ef.member.profile.controller.MemberProfileConstants.GET_PROFILE_FORM_DATA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ef.common.logging.ServiceLoggingUtil;
import com.ef.dataaccess.event.DomainCache;
import com.ef.dataaccess.event.EventCriteriaMetadataCache;
import com.ef.model.member.MemberRegistrationBindingModel;

/**
 * Handles requests for the event service.
 */
@Controller
public class MemberProfileController {

  private static final Logger logger = LoggerFactory.getLogger(MemberProfileController.class);
  private final ServiceLoggingUtil logUtil = new ServiceLoggingUtil();

  private final DomainCache domainCache;
  private final EventCriteriaMetadataCache eventCriteriaMetadataCache;

  @Autowired
  public MemberProfileController(DomainCache domainCache, EventCriteriaMetadataCache eventCriteriaMetadataCache) {
    this.domainCache = domainCache;
    this.eventCriteriaMetadataCache = eventCriteriaMetadataCache;
  }

//  curl -i -H "Accept: application/json" -H "Content-Type:application/json" -X POST --data '{"id":"1232455663","type":"PREvent","scheduledDate":"2020/12/25","scheduledTime":"18:03:51","location":"Mainland China","description":"Review Food Event"}' "http://secure.codeczar.co.uk/event-service/rest/event/publish"
  @PostMapping(GET_PROFILE_FORM_DATA)
  public @ResponseBody ResponseEntity<?> getProfileFormInfo(
      @RequestBody MemberRegistrationBindingModel memberRegistrationData) {

    MemberProfileFormInfo mpfi = new MemberProfileFormInfo(domainCache.getDomains(),
        eventCriteriaMetadataCache.getEventCriteriaMetadataList());
    return new ResponseEntity<MemberProfileFormInfo>(mpfi, HttpStatus.OK);

  }

//  @PostMapping(REGISTER_MEMBER_BLOGGER)
//  public @ResponseBody ResponseEntity<?> registerMemberBlogger(
//      @RequestBody MemberRegistrationBindingModel memberRegistrationData) {
//
//    memberRegistrationData.setMemberType(memberTypeCache.getMemberType(MemberType.KNOWN_MEMBER_TYPE_BLOGGER).getName());
//
//    return registerMember(memberRegistrationData);
//  }
//
//  public @ResponseBody ResponseEntity<?> registerMember(
//      @RequestBody MemberRegistrationBindingModel memberRegistrationData) {
//    logUtil.debug(logger, "Registering member: " + memberRegistrationData);
//
//    Response<Member> registrationStatus = registrationService.registerMember(memberRegistrationData);
//
//    if (registrationStatus.getFailureReasons() != null && !registrationStatus.getFailureReasons().isEmpty()) {
//      logUtil.info(logger,
//          "Error registering member: " + memberRegistrationData + " [" + registrationStatus.getFailureReasons() + "]");
//      return new ResponseEntity<List<String>>(registrationStatus.getFailureReasons(),
//          HttpStatus.valueOf(registrationStatus.getStatusCode().name()));
//    }
//
//    return new ResponseEntity<Member>(registrationStatus.getResponseResult(),
//        HttpStatus.valueOf(registrationStatus.getStatusCode().name()));
//  }
//
//
}
