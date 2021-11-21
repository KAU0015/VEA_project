package cz.vsb.vea.project.controllers.web;

import cz.vsb.vea.project.models.MainPost;
import cz.vsb.vea.project.models.Post;
import cz.vsb.vea.project.models.User;
import cz.vsb.vea.project.services.PostService;
import cz.vsb.vea.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @RequestMapping("/posts")
    public String allMainPosts(Model model){
        List<MainPost> mainPosts = postService.getAllMainPosts();
        model.addAttribute("mainPosts", mainPosts);
        return "post/allPosts";
    }

    @RequestMapping("/add_post")
    public String createNewPost(Model model) {
        model.addAttribute("post", new MainPost());
        return "post/addNewPost";
    }

    @RequestMapping("/post/add")
    public String addNewPost(@ModelAttribute @Validated MainPost post, BindingResult postError, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if(postError.hasErrors()) {
            model.addAttribute("post", post);
            System.out.println("error");
            return "post/addNewPost";
        }

        postService.addNewPost(post, user);

        return "redirect:/posts";
    }
}
