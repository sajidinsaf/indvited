package com.ef.dataaccess.member;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

public class EmailFormatterForDbTest {

  @Test
  public void shouldReturnInLowercase() {
    assertThat(new EmailFormatterForDb().data("Test@test.Com"), Matchers.is("test@test.com"));
    assertThat(new EmailFormatterForDb().data("test@test.com"), Matchers.is("test@test.com"));
  }

}
