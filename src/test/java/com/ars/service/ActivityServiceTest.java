package com.ars.service;


import com.ars.dto.AcitivitySearchCriteria;
import com.ars.dto.ActivityDto;
import com.ars.po.Activity;
import com.ars.po.User;
import com.ars.repository.ActivityRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        Activity activity = new Activity("ocean", "welcome","content");

        new Expectations() {{
            activityRepository.findById(anyString);
            result = Optional.of(activity);
        }};

        Activity resultActivity = activityService.getActivityById("00303");

        assertThat(resultActivity.getAuthor()).isEqualTo("ocean");
        assertThat(resultActivity.getTitle()).isEqualTo("welcome");
    }

    @Test
    public void should_return_created_activity_when_createActivity() throws Exception {
        Activity activity = new Activity("ocean", "welcome","content");
        new Expectations(){{
            activityRepository.save(activity);
            result = activity;
        }};

        Activity resultActivity = activityService.createActivity(activity);

        assertThat(resultActivity.getAuthor()).isEqualTo("ocean");
        assertThat(resultActivity.getTitle()).isEqualTo("welcome");
    }

    @Test
    public void should_return_updated_activity_when_updateActivity() throws Exception {
        Activity activity = new Activity("ocean", "welcome","content");
        Activity newActivity = new Activity("ocean","description","content");
        new Expectations(){{
            activityRepository.findById(anyString);
            result = Optional.of(activity);
            activityRepository.save(activity);
            result = newActivity;
        }};

        boolean result = activityService.updateActivity("1", newActivity);

        assertThat(result).isTrue();
    }

    @Test
    public void should_deleted_success_repository_when_given_deletedActivity_id_existed() throws Exception {
        Activity activity = new Activity("ocean", "welcome","content");
        new Expectations(){{
            activityRepository.findById("1");
            result = Optional.of(activity);
            activityRepository.delete(activity);
        }};

        Activity deleteActivity = activityService.deleteActivity("1");

        assertThat(deleteActivity.getAuthor()).isEqualTo("ocean");
        assertThat(deleteActivity.getTitle()).isEqualTo("welcome");
    }

    @Test
    public void should_return_all_activities_name_are_search_name_when_only_given_search_name() throws Exception {
        AcitivitySearchCriteria searchCriteria = new AcitivitySearchCriteria();
        searchCriteria.setAuthor("ocean");
        Activity activity = new Activity("ocean", "welcome", "content");
        List<User> userList = getUserList("userName");
        activity.setParticipants(userList);
        new Expectations(){{
            activityRepository.findAllByAuthor(anyString);
            result = Arrays.asList(activity);
        }};

        List<ActivityDto> activityByCriteria = activityService.getActivityByCriteria(searchCriteria);

        assertThat(activityByCriteria.get(0).getAuthor()).isEqualTo("ocean");
        assertThat(activityByCriteria.get(0).getTitle()).isEqualTo("welcome");
        assertThat(activityByCriteria.get(0).getContent()).isEqualTo("content");
        assertThat(activityByCriteria.get(0).getParticipants().get(0).getUserName()).isEqualTo("userName");
    }

    @Test
    public void should_return_all_activities_name_and_title_as_search_name_and_title_when_given_search_name_and_title() throws Exception {
        AcitivitySearchCriteria searchCriteria = new AcitivitySearchCriteria();
        searchCriteria.setAuthor("ocean");
        searchCriteria.setTitle("welcome");
        Activity activity = new Activity("ocean", "welcome", "content");
        List<User> userList = getUserList("userName");
        activity.setParticipants(userList);
        new Expectations(){{
            activityRepository.findAllByTitleLikeAndAuthor(anyString,anyString);
            result = Arrays.asList(activity);
        }};

        List<ActivityDto> activityByCriteria = activityService.getActivityByCriteria(searchCriteria);

        assertThat(activityByCriteria.get(0).getAuthor()).isEqualTo("ocean");
        assertThat(activityByCriteria.get(0).getTitle()).isEqualTo("welcome");
        assertThat(activityByCriteria.get(0).getContent()).isEqualTo("content");
        assertThat(activityByCriteria.get(0).getParticipants().get(0).getUserName()).isEqualTo("userName");
    }

    @Test
    public void should_return_all_activities_title_as_search_title_when_only_given_search_title() throws Exception {
        AcitivitySearchCriteria searchCriteria = new AcitivitySearchCriteria();
        searchCriteria.setTitle("welcome");
        Activity activity = new Activity("ocean", "welcome", "content");
        List<User> userList = getUserList("userName");
        activity.setParticipants(userList);
        new Expectations(){{
            activityRepository.findAllByTitleLike(anyString);
            result = Arrays.asList(activity);
        }};

        List<ActivityDto> activityByCriteria = activityService.getActivityByCriteria(searchCriteria);

        assertThat(activityByCriteria.get(0).getAuthor()).isEqualTo("ocean");
        assertThat(activityByCriteria.get(0).getTitle()).isEqualTo("welcome");
        assertThat(activityByCriteria.get(0).getContent()).isEqualTo("content");
        assertThat(activityByCriteria.get(0).getParticipants().get(0).getUserName()).isEqualTo("userName");
    }

    @Test
    public void should_return_all_activities_when_not_given_search_title_and_name() throws Exception {
        AcitivitySearchCriteria searchCriteria = new AcitivitySearchCriteria();
        Activity activity = new Activity("ocean", "welcome", "content");
        List<User> userList = getUserList("userName");
        activity.setParticipants(userList);
        new Expectations(){{
            activityRepository.findAll();
            result = Arrays.asList(activity);
        }};

        List<ActivityDto> activityByCriteria = activityService.getActivityByCriteria(searchCriteria);

        assertThat(activityByCriteria.get(0).getAuthor()).isEqualTo("ocean");
        assertThat(activityByCriteria.get(0).getTitle()).isEqualTo("welcome");
        assertThat(activityByCriteria.get(0).getContent()).isEqualTo("content");
        assertThat(activityByCriteria.get(0).getParticipants().get(0).getUserName()).isEqualTo("userName");
    }

    private List<User> getUserList(String userName){
        List<User> userDtoList = new ArrayList<>();
        User user = new User(userName,null);
        userDtoList.add(user);
        return userDtoList;
    }
}