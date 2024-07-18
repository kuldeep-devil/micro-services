package com.java.userservice.controller;

import com.java.userservice.VO.ResponseTemplateVO;
import com.java.userservice.entity.User;
import com.java.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public User saveUser(@RequestBody User user){
        log.info("inside UserController saveUser" ,user);
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public ResponseTemplateVO getUserWithDepartment(@PathVariable("id") Long userId){
        log.info("inside UserController getUserWithDepartment" ,userId);
        return userService.findUserWithDepartment(userId);
    }


}
