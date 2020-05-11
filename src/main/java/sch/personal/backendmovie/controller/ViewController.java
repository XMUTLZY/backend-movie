package sch.personal.backendmovie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/view")
public class ViewController {
    @RequestMapping("/index")
    public String adminIndex(Model model, HttpServletRequest request) {
        model.addAttribute("admin", request.getSession().getAttribute("ADMIN_USER_NAME"));
        return "/adminIndex";
    }

    @RequestMapping("/login")
    public String adminLogin() {
        return "/adminLogin";
    }
}
