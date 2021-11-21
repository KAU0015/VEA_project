package cz.vsb.vea.project.controllers.web;

import cz.vsb.vea.project.models.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {


    @RequestMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {

            return "redirect:/dashboard";
        }

        return "index";
    }

    @RequestMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) Boolean error){
      /*  if (error != null) {
            model.addAttribute("loginError", true);
            return "index";
        }*/

        return "index";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "index";
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
