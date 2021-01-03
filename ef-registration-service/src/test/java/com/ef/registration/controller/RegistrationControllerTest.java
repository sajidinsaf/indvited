package com.ef.registration.controller;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ef.model.member.Member;
import com.ef.model.member.MemberRegistrationBindingModel;
import com.ef.model.response.Response;
import com.ef.model.response.StatusCode;
import com.ef.registration.service.member.RegistrationService;

public class RegistrationControllerTest {

  private RegistrationController registrationController;
  @Mock
  private MemberRegistrationBindingModel memberRegistationData;

  @Mock
  private RegistrationService registrationService;

  @Mock
  private Response<Member> registrationStatus;

  @Mock
  private Member member;

  private StatusCode statusCode;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    registrationController = new RegistrationController(registrationService);
  }

  @Test
  public void test() {

    when(registrationService.registerMember(memberRegistationData)).thenReturn(registrationStatus);
    when(registrationStatus.getResponseResult()).thenReturn(member);
    statusCode = StatusCode.OK;
    when(registrationStatus.getStatusCode()).thenReturn(statusCode);

    ResponseEntity<?> registrationResult = registrationController.registerMember(memberRegistationData);
    assertThat((Member) registrationResult.getBody(), Matchers.is(member));
    assertThat(registrationResult.getStatusCode(), Matchers.is(HttpStatus.OK));
    verify(registrationService).registerMember(memberRegistationData);
  }

}
