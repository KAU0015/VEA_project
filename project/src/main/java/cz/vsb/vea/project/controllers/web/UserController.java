package cz.vsb.vea.project.controllers.web;

import cz.vsb.vea.project.models.User;
import cz.vsb.vea.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/add")
    public String addNewUser(@ModelAttribute @Validated User user, BindingResult userError, Model model) {
        if(userError.hasErrors()) {
            model.addAttribute("user", user);
            System.out.println("error");
            return "createNewAccount";
        }

        userService.createUser(user);
        //model.addAttribute("users", userService.getUsers());
        return "redirect:/";
    }

    @RequestMapping("/user/profile")
    public String viewProfile(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/userProfile";
    }
}
