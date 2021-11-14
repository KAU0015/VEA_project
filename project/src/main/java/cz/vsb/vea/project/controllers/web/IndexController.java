package cz.vsb.vea.project.controllers.web;

import cz.vsb.vea.project.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {

        return "index";
    }

    @RequestMapping("/test")
    public String test(Model model) {

        return "test";
    }

    @RequestMapping("/create_account")
    public String createNewAccount(Model model) {
        model.addAttribute("user", new User());
        return "createNewAccount";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "accessDenied";
    }
}
