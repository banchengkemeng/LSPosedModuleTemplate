package com.example.lsposedmoduletemplate.controller;

import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RestController;

@RestController
public class DemoController {
    @RequestMapping("/ping")
    public String ping() {
        return "pong";
    }
}
