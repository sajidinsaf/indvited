package com.ef.registration.service.member.validation;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.ef.model.member.MemberRegistrationBindingModel;

public class PasswordValidatorTest {

  private PasswordValidator passwordValidator;
  @Mock
  private MemberRegistrationBindingModel member;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    passwordValidator = new PasswordValidator();

  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testValidate() {

    String validPassword = "Geeks@portal20";
    when(member.getPassword()).thenReturn(validPassword);
    assertThat(passwordValidator.validate(member), Matchers.nullValue());

    String noNumberCharacter = "Geeksf@rgeeks";
    when(member.getPassword()).thenReturn(noNumberCharacter);
    assertThat(passwordValidator.validate(member), Matchers.is(PasswordValidator.PASSWORD_VALID_CRITERIA_MESSAGE));

    String noSpecialCharacter = "Geeksforgeek$";
    when(member.getPassword()).thenReturn(noSpecialCharacter);
    assertThat(passwordValidator.validate(member), Matchers.is(PasswordValidator.PASSWORD_VALID_CRITERIA_MESSAGE));

    String containsSpace = "Geeks@ portal9";
    when(member.getPassword()).thenReturn(containsSpace);
    assertThat(passwordValidator.validate(member), Matchers.is(PasswordValidator.PASSWORD_VALID_CRITERIA_MESSAGE));

    String noUpperCaseCharacter = "346346rwwew";
    when(member.getPassword()).thenReturn(noUpperCaseCharacter);
    assertThat(passwordValidator.validate(member), Matchers.is(PasswordValidator.PASSWORD_VALID_CRITERIA_MESSAGE));

    String noLowerCaseCharacter = "34634RDFGTE";
    when(member.getPassword()).thenReturn(noLowerCaseCharacter);
    assertThat(passwordValidator.validate(member), Matchers.is(PasswordValidator.PASSWORD_VALID_CRITERIA_MESSAGE));

    String lessThan8Characters = "A3rt";
    when(member.getPassword()).thenReturn(lessThan8Characters);
    assertThat(passwordValidator.validate(member), Matchers.is(PasswordValidator.PASSWORD_VALID_CRITERIA_MESSAGE));

    String moreThan8Characters = "A3sdsdfsgsgsgsgsgsgsgadsgrt";
    when(member.getPassword()).thenReturn(moreThan8Characters);
    assertThat(passwordValidator.validate(member), Matchers.is(PasswordValidator.PASSWORD_VALID_CRITERIA_MESSAGE));

  }

  public static void main(String args[]) {

    // Test Case 1:
    String str1 = "Geeks@portal20";
    System.out.println(isValidPassword(str1));

    // Test Case 2:
    String str2 = "Geeksforgeeks";
    System.out.println(isValidPassword(str2));

    // Test Case 3:
    String str3 = "Geeks@ portal9";
    System.out.println(isValidPassword(str3));

    // Test Case 4:
    String str4 = "1234";
    System.out.println(isValidPassword(str4));

    // Test Case 5:
    String str5 = "Gfg@20";
    System.out.println(isValidPassword(str5));

    // Test Case 6:
    String str6 = "geeks@portal20";
    System.out.println(isValidPassword(str6));
  }

  private static boolean isValidPassword(String password) {

    // Regex to check valid password.
    String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

    // Compile the ReGex
    Pattern p = Pattern.compile(regex);

    // If the password is empty
    // return false
    if (password == null) {
      return false;
    }

    // Pattern class contains matcher() method
    // to find matching between given password
    // and regular expression.
    Matcher m = p.matcher(password);

    // Return if the password
    // matched the ReGex
    return m.matches();
  }
}
