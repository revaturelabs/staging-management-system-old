package com.revature.sms.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class DefaultRoutingController {
    @RequestMapping(value = {"/", "/login", "/attendance", "/associate-attendance"} )
    public String routeToHome(){
        return "forward:index.html";
    }
}