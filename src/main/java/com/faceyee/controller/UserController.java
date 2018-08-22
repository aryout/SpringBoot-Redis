package com.faceyee.controller;

import com.faceyee.domain.entity.User;
import com.faceyee.domain.repository.UserRepository;
import com.faceyee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 97390 on 8/21/2018.
 */
@RestController
@RequestMapping(value = "/user") // 类声明为一级URL
public class UserController {
    @Autowired // @Resource
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/index")// 类声明为二级URL
    public String index(){
        return "/user/index";
    } // 方法名往往和该级URL对应

    @RequestMapping(value = "/show")  // http://localhost:8080/user/show?name=
    @ResponseBody
    public String show(@RequestParam(value = "userName") String userName){
        User user = userService.findUserByName(userName);

        if (user != null){
            return user.getId()+"/"+user.getUserName()+"/"+user.getPassWord();
        }else return "null";
    }

    @RequestMapping("/getUser")
    @Cacheable(value="user-key")
    public User getUser() {
        User user=userRepository.findByUserName("aa");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }

    @RequestMapping("/getUsers")
    @Cacheable(value="key-Users")
    public List<User> getUsers() {
        List<User> users=userRepository.findAll();
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return users;
    }
}
