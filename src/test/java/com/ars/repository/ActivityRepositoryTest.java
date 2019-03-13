package com.ars.repository;

import static org.junit.Assert.assertThat;

import com.ars.po.Activity;

import static org.hamcrest.core.Is.is;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureDataMongo
public class ActivityRepositoryTest {

  @Autowired
  private ActivityRepository activityRepository;

  @Autowired
  private MongoOperations mongoOperations;

  public ActivityRepositoryTest() {
  }

  @Test
  public void givenActivityWhoseIdIs1WhenFindByIdThenGetIt() {
    // given
    mongoOperations.insert(new Activity("activity1", "testing activity"));

    // when
    Activity activity = activityRepository.findById("1").orElse(new Activity("activity2", "wrong activity"));

    // then
    assertThat(activity.getName(), is("activity1"));
  }
}