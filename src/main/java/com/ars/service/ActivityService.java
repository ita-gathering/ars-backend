package com.ars.service;

import com.ars.dto.AcitivitySearchCriteria;
import com.ars.dto.ActivityDto;
import com.ars.dto.UserDto;
import com.ars.po.Activity;
import com.ars.repository.ActivityRepository;
import com.ars.utils.WrappedBeanCopier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Ocean Liang
 * @date 3/8/2019
 */
@Service
public class ActivityService {

    @Resource
    private ActivityRepository activityRepository;

    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public Activity getActivityById(String activityId) {
        return activityRepository.findById(activityId).orElse(null);
    }

    public boolean updateActivity(String activityId, Activity newActivity) {
        Activity existedActivity = activityRepository.findById(activityId).orElse(null);
        if (Objects.isNull(existedActivity)) {
            return false;
        }
        existedActivity.setTitle(newActivity.getTitle());
        existedActivity.setContent(newActivity.getContent());
        existedActivity.setStartDate(newActivity.getStartDate());
        existedActivity.setClosingDate(newActivity.getClosingDate());
        activityRepository.save(existedActivity);
        return true;
    }

    public Activity deleteActivity(String activityId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (Objects.isNull(activity)) {
            return null;
        }
        activityRepository.delete(activity);
        return activity;
    }

    public List<ActivityDto> getActivityByCriteria(AcitivitySearchCriteria searchCriteria) {
        List<Activity> activities;
        if (searchCriteria.getAuthor()!=null){
            if (searchCriteria.getTitle()==null){
                activities = activityRepository.findAllByAuthor(searchCriteria.getAuthor());
            }else {
                activities = activityRepository.findAllByTitleLikeAndAuthor(searchCriteria.getTitle(),searchCriteria.getAuthor());
            }
        }else {
            if (searchCriteria.getTitle()!=null){
                activities = activityRepository.findAllByTitleLike(searchCriteria.getTitle());
            }else {
                activities = activityRepository.findAll();
            }
        }
        List<ActivityDto> activityDtos = WrappedBeanCopier.copyPropertiesOfList(activities, ActivityDto.class);
        activityDtos.forEach(activityDto -> {
            List<UserDto> userDtos = WrappedBeanCopier.copyPropertiesOfList(activityDto.getParticipants(), UserDto.class);
            activityDto.setParticipants(userDtos);
        });
        return activityDtos;
    }
}
