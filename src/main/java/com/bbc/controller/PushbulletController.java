package com.bbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bbc.service.PushbulletService;

@Controller
@RequestMapping("api/v1")
public class PushbulletController {

    @Autowired
    private PushbulletService pushbulletService;

    @RequestMapping(value = "/push/{username}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public String createPush(@PathVariable String username,
            @RequestBody String pushRequest) {
        return pushbulletService.push(username, pushRequest).getBody();
    }

}