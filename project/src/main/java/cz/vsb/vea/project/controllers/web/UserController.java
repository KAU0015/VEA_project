package cz.vsb.vea.project.controllers.web;

import cz.vsb.vea.project.models.Post;
import cz.vsb.vea.project.models.User;
import cz.vsb.vea.project.services.PostService;
import cz.vsb.vea.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @RequestMapping("/user/add")
    public String addNewUser(@ModelAttribute @Validated User user, BindingResult userError, Model model) {
        User existingUser = userService.findByUsername(user.getUsername());
        if(existingUser != null){
            FieldError error = new FieldError("user", "username", "An account already exists for this username!");
            userError.addError(error);
        }

        if(userError.hasErrors()) {
            model.addAttribute("user", user);
            System.out.println("error");
            return "createNewAccount";
        }

        userService.createUser(user);
        return "redirect:/";
    }

    @RequestMapping("/dashboard")
    public String test(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Post> posts = postService.get10LastPosts(user.getId());
        model.addAttribute("posts", posts);
        return "user/dashboard";
    }

    @RequestMapping("/user/profile")
    public String viewProfile(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/userProfile";
    }

    @RequestMapping("/edit_account")
    public String editProfile(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/editAccount";
    }

    @RequestMapping("/user/profile/edit")
    public String editUser(@ModelAttribute @Validated User user, BindingResult userError, Model model) {
        User existingUser = userService.findByUsername(user.getUsername());
        if(existingUser != null && user.getId() != existingUser.getId()){
            FieldError error = new FieldError("user", "username", "An account already exists for this username!");
            userError.addError(error);
        }

        if(userError.hasErrors()) {
            model.addAttribute("user", user);
            System.out.println("error");
            return "user/editAccount";
        }

        userService.createUser(user);
        model.addAttribute("user", user);
        return "user/userProfile";
    }

    @RequestMapping("/users")
    public String allUsers(@ModelAttribute("searchedUser") User searched, Model model, Principal principal){
        model.addAttribute("searchedUser", searched);
        User user = userService.findByUsername(principal.getName());
        List<User> users = userService.getUserList(user.getId(), searched.getUsername());
        model.addAttribute("users", users);
        return "user/allUsers";
    }

    @RequestMapping("/user/{id}/detail")
    public String userDetail(@PathVariable long id, Model model){
        User userDetail = userService.getUser(id);
        model.addAttribute("userDetail", userDetail);
        return "user/userDetail";
    }
}
