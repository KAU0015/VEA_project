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

    @ManyToOne
    private Post post;

    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private long parentPostId;

    public Comment(@NotNull Post post) {
        super();
        this.post = post;
    }

    public Comment(@NotNull LocalDate date, @NotEmpty String content, User user, List<Comment> comments, @NotNull Post post) {
        super(date, content, user, comments);
        this.post = post;

        if (post != null) {
            this.parentPostId = post.getId();
        }
    }

    public Comment(long id, @NotNull LocalDate date, @NotEmpty String content, User user, List<Comment> comments, @NotNull Post post) {
        super(id, date, content, user, comments);
        this.post = post;

        if (post != null) {
            this.parentPostId = post.getId();
        }
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public long getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(long postId) {
        this.parentPostId = postId;
    }
}
