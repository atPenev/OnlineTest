package com.bbc.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bbc.dao.UserDao;
import com.bbc.model.CreateUserRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void validateCorrectRequest() throws Exception {
        assertTrue(userDao.validateUserRequest(
                new CreateUserRequest("someName", "someToken")));
    }

    @Test
    public void validateRequestWithoutUsername() throws Exception {
        assertFalse(userDao
                .validateUserRequest(new CreateUserRequest("", "someToken")));
    }

    @Test
    public void validateRequestWithoutToken() throws Exception {
        assertFalse(userDao
                .validateUserRequest(new CreateUserRequest("someName", "")));
    }

    @Test
    public void validateRequestWithNoUsernameAndNoToken() throws Exception {
        assertFalse(userDao.validateUserRequest(new CreateUserRequest()));
    }
}
