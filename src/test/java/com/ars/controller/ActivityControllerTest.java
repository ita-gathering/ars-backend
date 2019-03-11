package com.ars.controller;

import com.ars.po.Activity;
import com.ars.service.ActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by guowanyi on 2019/3/10.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ActivityController.class)
public class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ActivityService activityService;

    @Test
    public void should_return_activity_when_create_activity() throws Exception {
        Activity activity = new Activity("Active_1","description");
        when(activityService.createActivity(any(Activity.class))).thenReturn(activity);

        ResultActions resultActions = this.mockMvc.perform(post("/activity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(activity)));;

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name",is("Active_1")))
                .andExpect(jsonPath("$.data.description",is("description")));

    }

    @Test
    public void should_return_activity_when_given_activity_id() throws Exception {
        Activity activity = new Activity("Active_1","description");
        given(activityService.getActivityById("1")).willReturn(activity);

        ResultActions resultActions = this.mockMvc.perform(get("/activity/1"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name",is("Active_1")))
                .andExpect(jsonPath("$.data.description",is("description")));

    }

    @Test
    public void should_return_can_not_find_activity_when_given_not_exist_activity_id() throws Exception {
        given(activityService.getActivityById("1")).willReturn(null);

        ResultActions resultActions = this.mockMvc.perform(get("/activity/1"));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message",is("can not find activity")));

    }

    @Test
    public void should_update_activity_description_when_given_activity_id_and_description() throws Exception {
        Activity activity = new Activity("Active_1","description");
        when(activityService.updateActivity(anyString(),any())).thenReturn(true);

        ResultActions resultActions = this.mockMvc.perform(put("/activity/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(activity)));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void should_return_update_fail_when_given_activity_id_and_description_invalid() throws Exception {
        Activity activity = new Activity("Active_1","description");
        when(activityService.updateActivity(anyString(),any())).thenReturn(false);

        ResultActions resultActions = this.mockMvc.perform(put("/activity/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(activity)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message",is("update description in parkingBoy failed")));
    }

    @Test
    public void should_return_delete_activity_when_given_activity_existed_in_DB() throws Exception {
        Activity activity = new Activity("Active_1","description");
        given(activityService.deleteActivity(anyString())).willReturn(activity);

        ResultActions resultActions = this.mockMvc.perform(delete("/activity/1"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void should_return_delete_fail_when_given_activity_not_existed_in_DB() throws Exception {
        given(activityService.deleteActivity(anyString())).willReturn(null);

        ResultActions resultActions = this.mockMvc.perform(delete("/activity/1"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message",is("delete activity failed")));
    }



}
