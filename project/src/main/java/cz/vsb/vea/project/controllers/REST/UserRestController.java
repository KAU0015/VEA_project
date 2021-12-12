package cz.vsb.vea.project.controllers.REST;

import cz.vsb.vea.project.models.MainPost;
import cz.vsb.vea.project.models.User;
import cz.vsb.vea.project.services.PostService;
import cz.vsb.vea.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @RequestMapping("/rest/user/add")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        if(existingUser != null){
            return ResponseEntity.badRequest().body(user);
        }
        userService.createUser(user);
        return ResponseEntity.ok(user);
    }

    @RequestMapping("/rest/users/{id}")
    public List<User> allUsers(@PathVariable long id){
        User user = userService.findById(id);
        List<User> users = userService.getUserList(user.getId(), null);
        return users;
    }

    @RequestMapping("/rest/user/{id}/detail")
    public User userDetail(@PathVariable long id){
        return userService.getUser(id);
    }

    @RequestMapping("/rest/user/profile/edit")
    public ResponseEntity editUser(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        if(existingUser != null && user.getId() != existingUser.getId()){
            return ResponseEntity.badRequest().body(user);
        }

        userService.createUser(user);
        return ResponseEntity.ok(user);
    }

    @RequestMapping("/rest/post/add")
    public ResponseEntity addNewPost(@RequestBody MainPost mainPost) {
        User user = userService.findById(mainPost.getUserId());
        return ResponseEntity.ok(postService.addNewPost(mainPost, user));
    }
}
