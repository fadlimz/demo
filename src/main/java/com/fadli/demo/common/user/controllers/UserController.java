package com.fadli.demo.common.user.controllers;

import com.fadli.demo.base.constants.UriConstants;
import com.fadli.demo.base.responses.ApiResponse;
import com.fadli.demo.common.user.models.User;
import com.fadli.demo.common.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = UriConstants.USER)
public class UserController {

    @Autowired private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse getList() {
        List<User> resultList = userService.getList();

        return ApiResponse.data("users", resultList);
    }

    @RequestMapping(method = RequestMethod.GET, value = UriConstants.ID)
    public ApiResponse getById(@PathVariable String id) {
        User result = userService.findById(id);

        return ApiResponse.data("user", result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/by-user-code")
    public ApiResponse getByCode(@RequestParam String userCode) {
        User result =  userService.findByBk(userCode);

        return ApiResponse.data("user", result);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse add(@RequestBody User user) {
        User result = userService.add(user);

        return ApiResponse.data("user", result);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ApiResponse edit(@RequestBody User user) {
        User result = userService.edit(user);

        return ApiResponse.data("user", result);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ApiResponse delete(@RequestBody User user) {
        userService.delete(user);

        return ApiResponse.ok();
    }
}
