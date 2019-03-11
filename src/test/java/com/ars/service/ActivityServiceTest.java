package com.ars.service;


import com.ars.po.Activity;
import com.ars.repository.ActivityRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

/**
 * @author Ocean Liang
 * @date 3/11/2019
 */
public class ActivityServiceTest {

    @Tested
    private ActivityService activityService;

    @Injectable
    private ActivityRepository activityRepository;

    @Test
    public void should_return_activity_when_given_activity_id_existed_in_DB() {
        Activity activity = new Activity("ocean", "welcome");

        new Expectations() {{
            activityRepository.findById(anyString);
            result = Optional.of(activity);
        }};

        Activity resultActivity = activityService.getActivityById("00303");

        assertThat(resultActivity.getName()).isEqualTo("ocean");
        assertThat(resultActivity.getDescription()).isEqualTo("welcome");
    }

    @Test
    public void should_return_created_activity_when_createActivity() throws Exception {
        Activity activity = new Activity("ocean", "welcome");
        new Expectations(){{
            activityRepository.save(activity);
            result = activity;
        }};

        Activity resultActivity = activityService.createActivity(activity);

        assertThat(resultActivity.getName()).isEqualTo("ocean");
        assertThat(resultActivity.getDescription()).isEqualTo("welcome");
    }

    @Test
    public void should_return_updated_activity_when_updateActivity() throws Exception {
        Activity activity = new Activity("ocean", "welcome");
        new Expectations(){{
            activityRepository.findById(anyString);
            result = Optional.of(activity);
            activityRepository.save(activity);
            result = new Activity("ocean","description");
        }};

        boolean result = activityService.updateActivity("1", (Object)"description");

        assertThat(result).isTrue();
    }

    @Test
    public void should_deleted_success_repository_when_given_deletedActivity_id_existed() throws Exception {
        Activity activity = new Activity("ocean", "welcome");
        new Expectations(){{
            activityRepository.findById("1");
            result = Optional.of(activity);
            activityRepository.delete(activity);
        }};

        Activity deleteActivity = activityService.deleteActivity("1");

        assertThat(deleteActivity.getName()).isEqualTo("ocean");
        assertThat(deleteActivity.getDescription()).isEqualTo("welcome");
    }
}