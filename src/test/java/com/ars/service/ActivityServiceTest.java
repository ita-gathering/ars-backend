package com.ars.service;


import com.ars.po.Activity;
import com.ars.repository.ActivityRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Assert;
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
    public void test() {
        Activity activity = new Activity("ocean", "welcome");

        new Expectations() {{
            activityRepository.findById(anyString);
            result = Optional.of(activity);
        }};

        Activity resultActivity = activityService.getActivityById("00303");

        assertThat(resultActivity.getName()).isEqualTo("ocean");
        assertThat(resultActivity.getDescription()).isEqualTo("welcome");
    }


}