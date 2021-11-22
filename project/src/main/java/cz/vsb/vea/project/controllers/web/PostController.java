package cz.vsb.vea.project.controllers.web;

import cz.vsb.vea.project.models.Comment;
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
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("mainPost", new MainPost());
        return "post/addNewPost";
    }

    @RequestMapping("/post/add")
    public String addNewPost(@ModelAttribute @Validated MainPost mainPost, BindingResult postError, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if(postError.hasErrors()) {
            model.addAttribute("mainPost", mainPost);
            System.out.println("error");
            return "post/addNewPost";
        }

        postService.addNewPost(mainPost, user);

        return "redirect:/posts";
    }

    @RequestMapping("/post/{id}/detail")
    public String postDetail(@PathVariable long id, Model model){
        MainPost mainPost = postService.findMainPost(id);
        model.addAttribute("mainPost", mainPost);
        model.addAttribute("newComment", new Comment());
        return "post/postDetail";
    }

    @RequestMapping("main/{mainId}/post/{id}/comment/add")
    public String addComment(@PathVariable long mainId, @PathVariable long id, @ModelAttribute @Validated Comment newComment, Model model, BindingResult commentError, Principal principal){

        if(commentError.hasErrors()) {
            model.addAttribute("newComment", newComment);
            System.out.println("error");
            return "redirect:/post/" + mainId + "/detail";
        }


        Post post = postService.findPost(id);
        User user = userService.findByUsername(principal.getName());

        postService.addComment(post, newComment, user);

        return "redirect:/post/" + mainId + "/detail";
    }
}
