package cz.vsb.vea.project.repositories;

import cz.vsb.vea.project.models.Post;

import java.util.List;

public interface PostRepositoryInterface {

    public List<Post> getAllPosts();
    public Post save(Post p);
    public Post find(long id);
    public List<Post> find10LastPosts(long userId);
}
