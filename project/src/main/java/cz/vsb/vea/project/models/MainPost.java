package cz.vsb.vea.project.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
public class MainPost extends Post{

    @NotNull
    private String category;

    public MainPost(@NotNull String category) {
        super();
        this.category = category;
    }

    public MainPost(@NotNull LocalDate date, @NotEmpty String content, User user, List<Comment> comments, @NotNull String category) {
        super(date, content, user, comments);
        this.category = category;
    }

    public MainPost(long id, @NotNull LocalDate date, @NotEmpty String content, User user, List<Comment> comments, @NotNull String category) {
        super(id, date, content, user, comments);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
