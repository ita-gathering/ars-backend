package com.ars.controller;

import com.ars.dto.ActivityDto;
import com.ars.dto.ResponseDto;
import com.ars.dto.UserDto;
import com.ars.po.Activity;
import com.ars.service.ActivityService;
import com.ars.utils.WrappedBeanCopier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Ocean Liang
 * @date 3/8/2019
 */
@Slf4j
@RestController
public class ActivityController {
    @Resource
    private ActivityService activityService;

    @PostMapping("/activity")
    public ResponseDto createActivity(@RequestBody Activity activity) {
        if (Objects.isNull(activity.getAuthor()) || Objects.isNull(activity.getTitle())
                || Objects.isNull(activity.getContent())) {
            return ResponseDto.fail("author,title,content should not be empty");
        }
        activityService.createActivity(activity);
        return ResponseDto.success(activity);
    }

    @GetMapping("/activity")
    public ResponseDto getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        List<ActivityDto> activityDtos = WrappedBeanCopier.copyPropertiesOfList(activities, ActivityDto.class);
        activityDtos.forEach(activityDto -> {
            List<UserDto> userDtos = WrappedBeanCopier.copyPropertiesOfList(activityDto.getParticipants(), UserDto.class);
            activityDto.setParticipants(userDtos);
        });
        return ResponseDto.success(activityDtos);
    }
    
    @GetMapping("/activity/{activityId}")
    public ResponseDto getActivityById(@PathVariable String activityId) {
        Activity activity = activityService.getActivityById(activityId);
        if (Objects.isNull(activity)) {
            return ResponseDto.fail("can not find activity");
        }
        return ResponseDto.success(activity);
    }

    @PutMapping("/activity/{activityId}")
    public ResponseDto updateActivity(@PathVariable String activityId, @RequestBody Activity newActivity) {
        if (activityService.updateActivity(activityId, newActivity)) {
            return ResponseDto.success();
        }
        return ResponseDto.fail("update activity failed");
    }

    @DeleteMapping("/activity/{activityId}")
    public ResponseDto deleteActivity(@PathVariable String activityId) {
        Activity deletedActivity = activityService.deleteActivity(activityId);
        if (Objects.isNull(deletedActivity)) {
            return ResponseDto.fail("delete activity failed");
        }
        return ResponseDto.success(deletedActivity);
    }

    @PostMapping("/activity/{userName}")
    public ResponseDto participateActivity(@PathVariable String userName) {
        activityService.participateActivity(userName);
        return ResponseDto.success();
    }

}
