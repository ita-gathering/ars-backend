package com.ars.controller;

import com.alibaba.fastjson.JSONObject;
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
    public JSONObject createActivity(@RequestBody Activity activity) {
        JSONObject res = new JSONObject();
        activityService.createActivity(activity);
        res.put("activity", activity);
        return res;
    }

    @GetMapping("/activity/{activityId}")
    public JSONObject getActivityById(@PathVariable String activityId) {
        JSONObject res = new JSONObject();
        Activity activity = activityService.getActivityById(activityId);
        if (Objects.isNull(activity)) {
            res.put("message", "can not find activity");
        } else {
            res.put("acvitity", activity);
        }
        return res;
    }

    @PutMapping("/activity/{activityId}")
    public JSONObject updateActivity(@PathVariable String activityId, @RequestBody JSONObject request) {
        JSONObject res = new JSONObject();
        if (activityService.updateActivity(activityId, request.get("description"))) {
            res.put("message", "update description in activity successfully");
        } else {
            res.put("message", "update description in parkingBoy failed");
        }
        return res;
    }

    @DeleteMapping("/activity/{activityId}")
    public JSONObject deleteActivity(@PathVariable String activityId) {
        JSONObject res = new JSONObject();
        Activity deletedActivity = activityService.deleteActivity(activityId);
        if (Objects.isNull(deletedActivity)) {
            res.put("message", "delete activity failed");
        } else {
            res.put("activity", deletedActivity);
        }
        return res;
    }
}
