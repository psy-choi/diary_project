package com.example.Diary.Controller;


import com.example.Diary.Security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SubController {
    private SecurityService securityService;

    @Autowired
    public SubController(SecurityService securityService){
        this.securityService = securityService;
    }

    @GetMapping("/refresh")
    public String validateRefresh(@CookieValue(name = "refresh") String refresh, HttpServletResponse response, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String accesstoken = securityService.validateRefreshToken(refresh);
        if (accesstoken == null){
            return "redirect:/";
        } else {
            Cookie cookie_access = new Cookie("User", accesstoken);
            cookie_access.setMaxAge(60);
            cookie_access.setPath("/");
            response.addCookie(cookie_access);
        }
        return "redirect:"+ referer;
    }
}
