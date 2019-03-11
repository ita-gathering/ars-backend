package com.ars.service;

import com.ars.po.Activity;
import com.ars.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public boolean updateActivity(String activityId, Object description) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (Objects.isNull(activity)) {
            return false;
        }
        activity.setDescription((String) description);
        activityRepository.save(activity);
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
}
