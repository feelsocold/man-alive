package com.bohan.manalive.web.controller;
import com.bohan.manalive.config.auth.LoginUser;
import com.bohan.manalive.config.auth.dto.RegisterUser;
import com.bohan.manalive.config.auth.dto.SessionUser;
import com.bohan.manalive.domain.user.User;
import com.bohan.manalive.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final HttpSession httpSession;
    Logger logger = LoggerFactory.getLogger(getClass());
    //private final HttpSession httpSession;

    private final UserService userService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        logger.info("INDEX()");

        //SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("user", user);

            logger.info(user.getRole().getKey());
             if(user.getRole().getKey().equals("ROLE_GUEST")){
//                 model.addAttribute("user", user);
                return "redirect:/register";
            }
        }
        return "index";
    }

    @GetMapping("/main")
    public String main() {
        return "index";
    }

    @GetMapping("/register")
    public void register(Model model, @LoginUser SessionUser user) {

        if(user != null) {

            if(user.getRole().getKey().equals("ROLE_GUEST")){
                httpSession.removeAttribute("user");
            }
            model.addAttribute("user", user );
        }
    }

    @PostMapping("/user_register")
    public String register(RegisterUser user) throws Exception {
        userService.generalRegister(user);
        return "redirect:/";
    }

}

