package com.ef.member.registration.controller;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Random;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import com.ef.common.message.Response;
import com.ef.common.message.StatusCode;
import com.ef.member.registration.service.RegistrationConfirmationService;
import com.ef.member.registration.service.RegistrationService;
import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;

public class RegistrationControllerTest {

  private RegistrationController registrationController;
  @Mock
  private MemberRegistrationBindingModel memberRegistationData;

  @Mock
  private RegistrationService registrationService;

  @Mock
  private RegistrationConfirmationService registrationConfirmationService;

  @Mock
  private Response<Member> registrationStatus;

  @Mock
  private Member member;

  private StatusCode statusCode;

  @Before
  public void setUp() throws Exception {
    openMocks(this);
    registrationController = new RegistrationController(registrationService, registrationConfirmationService);
  }

  @Test
  public void shouldCallRegistrationService() {

    when(registrationService.registerMember(memberRegistationData)).thenReturn(registrationStatus);
    when(registrationStatus.getResponseResult()).thenReturn(member);
    statusCode = StatusCode.OK;
    when(registrationStatus.getStatusCode()).thenReturn(statusCode);

    ResponseEntity<?> registrationResult = registrationController.registerMember(memberRegistationData);
    assertThat((Member) registrationResult.getBody(), Matchers.is(member));
    assertThat(registrationResult.getStatusCode(), Matchers.is(HttpStatus.OK));
    verify(registrationService).registerMember(memberRegistationData);
  }

  @Test
  public void shouldCallRegistrationConfirmationService() {

    String confirmationCode = "xyz" + new Random().nextInt(1000);
    when(registrationConfirmationService.confirmMember(confirmationCode)).thenReturn(registrationStatus);
    when(registrationStatus.getResponseResult()).thenReturn(member);
    statusCode = StatusCode.OK;
    when(registrationStatus.getStatusCode()).thenReturn(statusCode);

    ModelAndView mav = registrationController.confirmMember(confirmationCode);
    System.out.println(mav);
//    ResponseEntity<?> registrationResult = registrationController.confirmMember(confirmationCode);
//    assertThat((Member) registrationResult.getBody(), Matchers.is(member));
//    assertThat(registrationResult.getStatusCode(), Matchers.is(HttpStatus.OK));
//    verify(registrationConfirmationService).confirmMember(confirmationCode);
  }
}
