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
  private MongoTemplate mongoTemplate;

  public ActivityRepositoryTest() {
  }

  @Test
  public void givenActivityWhoseIdIs1WhenFindByNameThenGetIt() {
    // given
    Activity activity1 = new Activity();
    activity1.setTitle("activity1");
    mongoTemplate.save(activity1);
    Activity activity2 = new Activity();
    activity2.setTitle("activity2");

    // when
    Activity getActivity = activityRepository.findByTitle("activity1").orElse(activity2);

    // then
    assertThat(getActivity.getTitle(), is("activity1"));
  }
}