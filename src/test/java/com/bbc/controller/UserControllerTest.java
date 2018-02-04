package com.bbc.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bbc.controller.exceptions.BadRequestException;
import com.bbc.controller.exceptions.ConflictException;
import com.bbc.model.CreateUserRequest;
import com.bbc.model.User;
import com.bbc.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService usereService;

    @Test
    public void userCreationSuccess() throws Exception {
        final User newUser = new User("bbcUser1", "accessToken1");
        final CreateUserRequest userRequest = new CreateUserRequest(
                newUser.getUsername(), newUser.getAccessToken());

        given(usereService.createUser(userRequest)).willReturn(newUser);

        mvc.perform(post("/api/v1/user").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated()).andExpect(
                        content().string(mapper.writeValueAsString(newUser)));
    }

    @Test
    public void userCreationIfUserAlreadyExists() throws Exception {
        final CreateUserRequest userRequest = new CreateUserRequest(
                "duplicateUser", "someToken");

        given(usereService.createUser(userRequest))
                .willThrow(new ConflictException("User already exists"));

        mvc.perform(post("/api/v1/user").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    public void userCreationIfRequestIsBad() throws Exception {
        final CreateUserRequest userRequest = new CreateUserRequest(
                "duplicateUser", "");

        given(usereService.createUser(userRequest))
                .willThrow(new BadRequestException(
                        "Request is missing mandatory elements."));

        mvc.perform(post("/api/v1/user").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUsersIfNoUsersExist() throws Exception {
        given(usereService.getUsers()).willReturn(Collections.emptyList());

        mvc.perform(get("/api/v1/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().string(
                        mapper.writeValueAsString(Collections.emptyList())));
    }

    @Test
    public void getUsersIfUsersExist() throws Exception {
        final List<User> users = Arrays.asList(new User("user1", "token1"),
                new User("user2", "token2"));
        given(usereService.getUsers()).willReturn(users);

        mvc.perform(get("/api/v1/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(users)));
    }

    @Test
    public void userUpdateSuccess() throws Exception {
        final User updateUser = new User("bbcUser1", "accessToken1");
        final CreateUserRequest userRequest = new CreateUserRequest(
                updateUser.getUsername(), updateUser.getAccessToken());

        given(usereService.updateUser(userRequest)).willReturn(updateUser);

        mvc.perform(put("/api/v1/user").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk()).andExpect(content()
                        .string(mapper.writeValueAsString(updateUser)));
    }

    @Test
    public void userUpdateIfUserDoesNotExist() throws Exception {
        final CreateUserRequest userRequest = new CreateUserRequest(
                "duplicateUser", "someToken");

        given(usereService.updateUser(userRequest))
                .willThrow(new BadRequestException("User does not exist"));

        mvc.perform(put("/api/v1/user").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void userUpdateIfRequestIsBad() throws Exception {
        final CreateUserRequest userRequest = new CreateUserRequest(
                "duplicateUser", "");

        given(usereService.updateUser(userRequest))
                .willThrow(new BadRequestException(
                        "Request is missing mandatory elements."));

        mvc.perform(put("/api/v1/user").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUserSuccess() throws Exception {
        final User user = new User("bbcUser1", "accessToken1");

        given(usereService.getUser(user.getUsername())).willReturn(user);

        mvc.perform(get("/api/v1/user/" + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(user)));
    }

    @Test
    public void getUserIfNoUser() throws Exception {
        final User user = new User("bbcUser1", "accessToken1");

        given(usereService.getUser(user.getUsername()))
                .willThrow(new BadRequestException("No such user exists."));

        mvc.perform(get("/api/v1/user/" + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUserSuccess() throws Exception {
        final User user = new User("bbcUser1", "accessToken1");

        given(usereService.removeUser(user.getUsername())).willReturn(user);

        mvc.perform(delete("/api/v1/user/" + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserIfNoUser() throws Exception {
        final User user = new User("bbcUser1", "accessToken1");

        given(usereService.removeUser(user.getUsername()))
                .willThrow(new BadRequestException("No such user exists."));

        mvc.perform(delete("/api/v1/user/" + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
