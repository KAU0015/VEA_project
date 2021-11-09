package cz.vsb.vea.project.repositories;

import cz.vsb.vea.project.models.Comment;

import java.util.List;

public interface CommentRepositoryInterface {

    public List<Comment> getAllComments();
    public Comment save(Comment c);
    public Comment find(long id);
}
