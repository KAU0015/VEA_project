package cz.vsb.vea.project.services;

import cz.vsb.vea.project.models.MainPost;
import cz.vsb.vea.project.models.User;
import cz.vsb.vea.project.repositories.CommentRepositoryInterface;
import cz.vsb.vea.project.repositories.MainPostRepositoryInterface;
import cz.vsb.vea.project.repositories.PostRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepositoryInterface postRepository;

    @Autowired
    MainPostRepositoryInterface mainPostRepository;

    @Autowired
    CommentRepositoryInterface commentRepository;


    public List<MainPost> getAllMainPosts(){
        return mainPostRepository.getAllMainPosts();
    }

    public void addNewPost(MainPost post, User user){
        post.setDate(LocalDateTime.now());
        post.setUser(user);
        mainPostRepository.save(post);
    }
}
