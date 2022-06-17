package cn.santeamo.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/index")
    public String test() {
        return "test";
    }

    @RequestMapping("/submitUserList")
    public void submitUserList() {

    }
}
