package com.revature.sms.controllers;
<<<<<<< HEAD

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultRoutingController {

    @RequestMapping(value = {"/", "/login", "/attendance"} )
    public String routeToHome(){
        return "forward:index.html";
    }
}

=======
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class DefaultRoutingController {
    @RequestMapping(value = {"/", "/login", "/attendance", "/associate-attendance", "/weeklyattendence"} )
    public String routeToHome(){
        return "forward:index.html";
    }
}
>>>>>>> 068c47f5b6c31f7d236924722b3a037d0975aa4c
