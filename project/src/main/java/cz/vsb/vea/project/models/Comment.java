package cz.vsb.vea.project.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Comment extends Post{

    @NotNull
    private Post post;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private long postId;

    public Comment(@NotNull Post post) {
        super();
        this.post = post;
    }

    public Comment(@NotNull LocalDate date, @NotEmpty String content, User user, List<Comment> comments, @NotNull Post post) {
        super(date, content, user, comments);
        this.post = post;

        if (post != null) {
            this.postId = post.getId();
        }
    }

    public Comment(long id, @NotNull LocalDate date, @NotEmpty String content, User user, List<Comment> comments, @NotNull Post post) {
        super(id, date, content, user, comments);
        this.post = post;

        if (post != null) {
            this.postId = post.getId();
        }
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
