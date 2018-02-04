package com.bbc.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bbc.service.PushbulletService;

@RunWith(SpringRunner.class)
@WebMvcTest(PushbulletController.class)
public class PushbulletControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PushbulletService pushbulletService;

    @Test
    public void testSuccessFullUserCreation() throws Exception {
        final String username = "user1";
        final String pushRequest = "pushbullet data";
        final String pushbulletResponse = "pusbullet response";

        given(pushbulletService.push(username, pushRequest))
                .willReturn(new HttpEntity<String>(pushbulletResponse));

        mvc.perform(post("/api/v1/push/user1")
                .contentType(MediaType.APPLICATION_JSON).content(pushRequest))
                .andExpect(status().isCreated())
                .andExpect(content().string(pushbulletResponse));
    }
}
