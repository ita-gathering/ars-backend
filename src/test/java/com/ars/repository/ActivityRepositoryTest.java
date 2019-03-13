package com.ars.repository;

import com.ars.po.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ActivityRepositoryTest {

  @Autowired
  private ActivityRepository activityRepository;

  @Autowired
  private MongoTemplate mongoOperations;

  public ActivityRepositoryTest() {
  }

  @Test
  public void givenActivityWhoseIdIs1WhenFindByIdThenGetIt() {
    // given
    mongoOperations.save(new Activity("activity1", "testing activity"));

    // when
    Activity activity = activityRepository.findAll().get(0);

    // then
    assertThat(activity.getName(), is("activity1"));
  }
}