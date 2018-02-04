package com.bbc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.bbc.controller.exceptions.BadRequestException;
import com.bbc.controller.exceptions.ConflictException;
import com.bbc.dao.UserDao;
import com.bbc.model.CreateUserRequest;
import com.bbc.model.User;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Test
    public void createUserSuccessfully() {
        final CreateUserRequest userRequest = new CreateUserRequest("bbcUser1",
                "accessToken1");

        given(userDao.validateUserRequest(userRequest)).willReturn(true);
        given(userDao.userExists(userRequest.getUsername())).willReturn(false);

        User createdUser = userService.createUser(userRequest);

        assertThat(createdUser.getUsername())
                .isEqualTo(userRequest.getUsername());
        assertThat(createdUser.getAccessToken())
                .isEqualTo(userRequest.getAccessToken());
        assertThat(createdUser.getNumOfNotificationsPushed()).isEqualTo(0);
    }

    @Test(expected = ConflictException.class)
    public void createUserIfUserExists() {
        final CreateUserRequest userRequest = new CreateUserRequest("bbcUser1",
                "accessToken1");

        given(userDao.validateUserRequest(userRequest)).willReturn(true);
        given(userDao.userExists(userRequest.getUsername())).willReturn(true);

        userService.createUser(userRequest);
    }

    @Test(expected = BadRequestException.class)
    public void createUserIfRequestIsInvalid() {
        final CreateUserRequest userRequest = new CreateUserRequest("bbcUser1",
                "");

        given(userDao.validateUserRequest(userRequest)).willReturn(false);

        userService.createUser(userRequest);
    }

    @Test
    public void updateUserSuccessfully() {
        final CreateUserRequest userRequest = new CreateUserRequest("bbcUser1",
                "accessToken1");

        given(userDao.validateUserRequest(userRequest)).willReturn(true);
        given(userDao.userExists(userRequest.getUsername())).willReturn(true);

        User createdUser = userService.updateUser(userRequest);

        assertThat(createdUser.getUsername())
                .isEqualTo(userRequest.getUsername());
        assertThat(createdUser.getAccessToken())
                .isEqualTo(userRequest.getAccessToken());
        assertThat(createdUser.getNumOfNotificationsPushed()).isEqualTo(0);
    }

    @Test(expected = BadRequestException.class)
    public void updateUserIfUserDoesNotExists() {
        final CreateUserRequest userRequest = new CreateUserRequest("bbcUser1",
                "accessToken1");

        given(userDao.validateUserRequest(userRequest)).willReturn(true);
        given(userDao.userExists(userRequest.getUsername())).willReturn(false);

        userService.updateUser(userRequest);
    }

    @Test(expected = BadRequestException.class)
    public void updateUserIfRequestIsInvalid() {
        final CreateUserRequest userRequest = new CreateUserRequest("bbcUser1",
                "");

        given(userDao.validateUserRequest(userRequest)).willReturn(false);

        userService.updateUser(userRequest);
    }

    @Test
    public void getUserSuccessfully() {
        final User user = new User("bbcUser1", "accessToken1");

        given(userDao.userExists(user.getUsername())).willReturn(true);
        given(userDao.get(user.getUsername())).willReturn(user);

        assertThat(userService.getUser(user.getUsername())).isEqualTo(user);
    }

    @Test(expected = BadRequestException.class)
    public void getUserIfUserDoesNotExists() {
        final User user = new User("bbcUser1", "accessToken1");

        given(userDao.userExists(user.getUsername())).willReturn(false);

        userService.getUser(user.getUsername());
    }

    @Test
    public void deleteUserSuccessfully() {
        final User user = new User("bbcUser1", "accessToken1");

        given(userDao.userExists(user.getUsername())).willReturn(true);

        userService.removeUser(user.getUsername());

        verify(userDao, times(1)).remove(user.getUsername());
    }

    @Test(expected = BadRequestException.class)
    public void deleteUserIfUserDoesNotExists() {
        final User user = new User("bbcUser1", "accessToken1");

        given(userDao.userExists(user.getUsername())).willReturn(false);

        userService.getUser(user.getUsername());
    }

    @Test
    public void incrementUserPushesSuccessfully() {
        final User user = new User("bbcUser1", "accessToken1");

        given(userDao.userExists(user.getUsername())).willReturn(true);
        given(userDao.get(user.getUsername())).willReturn(user);

        userService.incrementNumOfNotificationsPushed(user.getUsername());

        assertThat(user.getNumOfNotificationsPushed()).isEqualTo(1);
        verify(userDao, times(1)).put(user.getUsername(), user);
    }

    @Test(expected = BadRequestException.class)
    public void incrementUserPushesIfUserDoesNotExists() {
        final User user = new User("bbcUser1", "accessToken1");

        given(userDao.userExists(user.getUsername())).willReturn(false);

        userService.incrementNumOfNotificationsPushed(user.getUsername());
    }
}
