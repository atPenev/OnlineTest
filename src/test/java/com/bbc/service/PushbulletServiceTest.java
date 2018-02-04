package com.bbc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.bbc.controller.exceptions.BadRequestException;
import com.bbc.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushbulletServiceTest {

    @MockBean
    private UserService userService;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private PushbulletService pushbulletService;

    @Test
    @SuppressWarnings("unchecked")
    public void pushToUserSuccessfully() {
        final String username = "user1";
        final User user = new User(username, "some token");
        final String pushRequest = "some pushbullet request";
        final ResponseEntity<String> pushResponse = new ResponseEntity<String>(
                "some response", HttpStatus.CREATED);

        given(userService.getUser(username)).willReturn(user);
        given(restTemplate.exchange(anyString(), any(HttpMethod.class),
                any(HttpEntity.class), any(Class.class)))
                        .willReturn(pushResponse);

        assertThat(pushbulletService.push(username, pushRequest)
                .equals(pushResponse));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = BadRequestException.class)
    public void pushToUserThatDoesNotExist() {
        final String username = "user1";
        final String pushRequest = "some pushbullet request";
        final ResponseEntity<String> pushResponse = new ResponseEntity<String>(
                "some response", HttpStatus.CREATED);

        given(userService.getUser(username))
                .willThrow(new BadRequestException("User not found"));
        given(restTemplate.exchange(anyString(), any(HttpMethod.class),
                any(HttpEntity.class), any(Class.class)))
                        .willReturn(pushResponse);

        pushbulletService.push(username, pushRequest);
    }
}
