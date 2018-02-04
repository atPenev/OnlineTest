package com.bbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.bbc.dao.UserDao;
import com.bbc.service.PushbulletService;
import com.bbc.service.UserService;

/**
 * Beans can be defined in this class and {@link Autowired} in any other class.
 * 
 * @author Atanas Penev
 *
 */
public class ApplicationContext {

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public PushbulletService pushbulletService() {
        return new PushbulletService();
    }

    @Bean
    public UserDao userDao() {
        return new UserDao();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}