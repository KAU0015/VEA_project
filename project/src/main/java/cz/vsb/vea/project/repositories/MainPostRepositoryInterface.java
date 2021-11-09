package cz.vsb.vea.project.repositories;

import cz.vsb.vea.project.models.MainPost;

import java.util.List;

public interface MainPostRepositoryInterface {

    public List<MainPost> getAllMainPosts();
    public MainPost save(MainPost mp);
    public MainPost find(long id);
}
