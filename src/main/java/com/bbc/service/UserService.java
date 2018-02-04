package com.bbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bbc.controller.exceptions.BadRequestException;
import com.bbc.controller.exceptions.ConflictException;
import com.bbc.dao.UserDao;
import com.bbc.model.CreateUserRequest;
import com.bbc.model.User;

/**
 * For operations on the {@link UserDao}.
 * 
 * @author Atanas Penev
 *
 */
public class UserService {

    @Autowired
    private UserDao usersDao;

    /**
     * Update an existing user.
     * 
     * @param userRequest
     *            the request should contain the username of the user that is to
     *            be updated and the new access token of the user
     * @return the updated {@link User}
     * @throws BadRequestException
     *             if the request is missing data or the user does not exist
     */
    public User updateUser(CreateUserRequest userRequest)
            throws BadRequestException {
        if (!usersDao.validateUserRequest(userRequest))
            throw new BadRequestException(
                    "The request is missing mandatory elements.");

        if (!usersDao.userExists(userRequest.getUsername()))
            throw new BadRequestException("No such user exists.");

        User user = new User(userRequest.getUsername(),
                userRequest.getAccessToken());
        usersDao.put(userRequest.getUsername(), user);

        return user;
    }

    /**
     * Creates a user, if the user does not already exist.
     * 
     * @param userRequest
     *            the request should contain the username of the user and the
     *            access token of the user
     * 
     * @return the newly created {@link User}
     * 
     * @throws BadRequestException
     *             if the request is missing data
     * @throws ConflictException
     *             if the user already exists
     */
    public User createUser(CreateUserRequest userRequest)
            throws BadRequestException, ConflictException {
        if (!usersDao.validateUserRequest(userRequest))
            throw new BadRequestException(
                    "The request is missing mandatory elements.");

        if (usersDao.userExists(userRequest.getUsername()))
            throw new ConflictException("The user already exists.");

        User user = new User(userRequest.getUsername(),
                userRequest.getAccessToken());
        usersDao.put(userRequest.getUsername(), user);

        return user;
    }

    /**
     * Get a user
     * 
     * @param username
     * @return the user
     * @throws BadRequestException
     *             if the user does not exist
     */
    public User getUser(String username) throws BadRequestException {
        if (!usersDao.userExists(username))
            throw new BadRequestException("No such user exists.");

        return usersDao.get(username);
    }

    /**
     * Delete a user
     * 
     * @param username
     * @return the user that got deleted
     * @throws BadRequestException
     *             if the user does not exist
     */
    public User removeUser(String username) {
        if (!usersDao.userExists(username))
            throw new BadRequestException("No such user exists.");

        return usersDao.remove(username);
    }

    /**
     * {@link UserDao#getAllUsers()}
     */
    public List<User> getUsers() {
        return usersDao.getAllUsers();
    }

    /**
     * Increments the push notifications counter of a user.
     * 
     * @param username
     *            of the user
     */
    public void incrementNumOfNotificationsPushed(String username) {
        User user = getUser(username);
        user.incrementNumOfNotificationsPushed();
        usersDao.put(username, user);
    }
}
