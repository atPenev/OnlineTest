package com.bbc.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bbc.model.CreateUserRequest;
import com.bbc.model.User;

/**
 * This class is a data access object for adding, removing, getting users.
 * 
 * @author Atanas Penev
 *
 */
public class UserDao {

    private Map<String, User> users = new ConcurrentHashMap<>();

    /**
     * Takes a request for creating a user and verifies if it's a valid request.
     * 
     * @param userRequest
     *            the request that should be verified
     * 
     * @return false if the request has an empty or no username or token
     */
    public boolean validateUserRequest(CreateUserRequest userRequest) {
        return userRequest.getUsername() != null
                && !userRequest.getUsername().isEmpty()
                && userRequest.getAccessToken() != null
                && !userRequest.getAccessToken().isEmpty();
    }

    /**
     * Checks if a user has already been registered.
     * 
     * @param username
     *            of the user that is being checked
     * @return true if the user has already been registered
     */
    public boolean userExists(String username) {
        return users.get(username) != null;
    }

    /**
     * @return Returns a {@link User} or null if no user exists
     */
    public User get(String username) {
        return users.get(username);
    }

    /**
     * {@link HashMap#put(Object, Object)}
     * 
     * @param user
     *            {@link User}
     */
    public void put(String username, User user) {
        users.put(username, user);
    }

    /**
     * @return a list of all users or an empty list if no users exist.
     */
    public List<User> getAllUsers() {
        return new ArrayList<User>(users.values());
    }

    /**
     * Deletes a user.
     * 
     * @return the {@link User} that got deleted
     */
    public User remove(String username) {
        return users.remove(username);
    }
}
