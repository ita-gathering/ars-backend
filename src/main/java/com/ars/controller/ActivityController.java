package com.ars.controller;

import com.alibaba.fastjson.JSONObject;
import com.ars.dto.ResponseDto;
import com.ars.po.Activity;
import com.ars.service.ActivityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Ocean Liang
 * @date 3/8/2019
 */

@RestController
public class ActivityController {
    @Resource
    private ActivityService activityService;

    @PostMapping("/activity")
    public ResponseDto createActivity(@RequestBody Activity activity) {
        activityService.createActivity(activity);
        return ResponseDto.success(activity);
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
    public ResponseDto updateActivity(@PathVariable String activityId, @RequestBody JSONObject request) {
        if (activityService.updateActivity(activityId, request.get("description"))) {
            return ResponseDto.success();
        }
        return ResponseDto.fail("update description in parkingBoy failed");
    }

    @DeleteMapping("/activity/{activityId}")
    public ResponseDto deleteActivity(@PathVariable String activityId) {
        Activity deletedActivity = activityService.deleteActivity(activityId);
        if (Objects.isNull(deletedActivity)) {
            return ResponseDto.fail("delete activity failed");
        }
        return ResponseDto.success(deletedActivity);
    }
}
