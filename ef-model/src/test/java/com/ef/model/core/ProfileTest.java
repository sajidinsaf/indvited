package com.ef.model.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ProfileTest {

  @Mock
  private Criteria<Profile> matchingCriteria1;

  @Mock
  private Criteria<Profile> matchingCriteria2;

  @Before
  public void setUp() throws Exception {
    openMocks(this);
  }

  @After
  public void tearDown() throws Exception {
  }

  @SuppressWarnings("unchecked")
  @Test
  public void matches() {

    ProfileImpl p = new ProfileImpl(123, "Profile");
    when(matchingCriteria1.isSatisfiedBy(p)).thenReturn(true);
    when(matchingCriteria2.isSatisfiedBy(p)).thenReturn(true);

    assertThat(p.satisfies(new Criteria[] { matchingCriteria1, matchingCriteria2 }), is(true));

    verify(matchingCriteria1).isSatisfiedBy(p);
    verify(matchingCriteria2).isSatisfiedBy(p);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void doesNotMatchSecondCriteria() {

    ProfileImpl p = new ProfileImpl(123, "Profile");
    when(matchingCriteria1.isSatisfiedBy(p)).thenReturn(true);

    assertThat(p.satisfies(new Criteria[] { matchingCriteria1, matchingCriteria2 }), is(false));

    verify(matchingCriteria1).isSatisfiedBy(p);
    verify(matchingCriteria2).isSatisfiedBy(p);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void doesNotMatchFirst() {

    ProfileImpl p = new ProfileImpl(123, "Profile");
    when(matchingCriteria2.isSatisfiedBy(p)).thenReturn(true);

    assertThat(p.satisfies(new Criteria[] { matchingCriteria1, matchingCriteria2 }), is(false));

    verify(matchingCriteria1).isSatisfiedBy(p);
    verify(matchingCriteria2, never()).isSatisfiedBy(p);
  }
}

class ProfileImpl extends Profile {

  /**
   * 
   */
  private static final long serialVersionUID = 6580268066078929865L;

  public ProfileImpl(int id, String name) {
    super(id, name);
  }

}