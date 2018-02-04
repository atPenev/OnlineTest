package com.bbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.bbc.model.User;

/**
 * For pushing notifications using the pushbullet API.
 * 
 * @author Atanas Penev
 *
 */
public class PushbulletService {

    private static final String PUSHBULLET_PUSHES = "https://api.pushbullet.com/v2/pushes";

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Creates a pushbullet push notification.
     * 
     * @param username
     *            of the user who will receive the notification
     * @param pushRequest
     *            the notification info
     * 
     *            <pre>
     *  {@code {
     *            "body":"Space Elevator, Mars Hyperloop, Space Model S (Model Space?)",
     *            "title":"Space Travel Ideas",
     *            "type":"note"
     *          }}
     *            </pre>
     * 
     * @return an {@link HttpEntity} that contains the response to the
     *         pushbullet request
     */
    public HttpEntity<String> push(String username, String pushRequest) {

        User user = userService.getUser(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Content-Type", "application/json");
        headers.add("Access-Token", user.getAccessToken());

        HttpEntity<String> entity = restTemplate.exchange(PUSHBULLET_PUSHES,
                HttpMethod.POST, new HttpEntity<>(pushRequest, headers),
                String.class);

        userService.incrementNumOfNotificationsPushed(username);

        return entity;
    }
}
