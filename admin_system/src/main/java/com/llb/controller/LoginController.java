package com.llb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
