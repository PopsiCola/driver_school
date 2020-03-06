package com.llb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 登录
 * @Author llb
 * Date on 2020/3/4
 */
@Controller
@RequestMapping("")
public class LoginController {

    @RequestMapping("/index")
    public String toLogin() {
        return "index";
    }

    /**
     * 登录 用户/教练/管理员 登录
     * @param username 登录名
     * @param password 密码
     * @param type 类型:1.用户 2.管理员 3.教练
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam(required = true) String username,
                                     @RequestParam(required = true) String password,
                                     @RequestParam(required = true) Integer type) {

        return null;
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
