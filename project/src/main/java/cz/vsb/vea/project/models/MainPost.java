package cz.vsb.vea.project.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MainPost extends Post{

    @NotNull
    private String title;

    public MainPost() {
        super();
    }

    public MainPost(LocalDateTime date, @NotEmpty String content, User user, List<Comment> comments, @NotNull String title) {
        super(date, content, user, comments);
        this.title = title;
    }

    public MainPost(long id, LocalDateTime date, @NotEmpty String content, User user, List<Comment> comments, @NotNull String title) {
        super(id, date, content, user, comments);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
