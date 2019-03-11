package com.ars.repository;

import static org.junit.Assert.assertThat;

import com.ars.po.Activity;

import static org.hamcrest.core.Is.is;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ActivityRepositoryTest {

  @Autowired
  private ActivityRepository activityRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  @After
  public void tearDown() {
    testEntityManager.clear();
  }

  @Test
  public void givenActivityWhoseIdIs1WhenFindByIdThenGetIt() {
    // given
    testEntityManager.persist(new Activity("activity1", "testing activity"));

    // when
    Activity activity = activityRepository.findById("1").orElse(new Activity("activity2", "wrong activity"));

    // then
    assertThat(activity.getName(), is("activity1"));
  }
}