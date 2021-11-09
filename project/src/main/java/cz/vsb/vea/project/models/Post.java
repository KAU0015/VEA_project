package cz.vsb.vea.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @NotNull
    protected LocalDate date;

    @NotEmpty
    protected String content;

    @Column(name = "user_id")
    protected long userId;

    @ManyToOne
    @JoinColumn(name="user_id")
    protected User user;

    @OneToMany(mappedBy = "post")
    protected List<Comment> comments;

    public Post(){
        System.out.println("Post constructor");
    }

    public Post(@NotNull LocalDate date, @NotEmpty String content, User user, List<Comment> comments) {
        this.date = date;
        this.content = content;
        this.user = user;
        this.comments = comments;

        if (user != null) {
            this.userId = user.getId();
        }

        System.out.println("Post constructor with params");
    }

    public Post(long id, @NotNull LocalDate date, @NotEmpty String content, User user, List<Comment> comments) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.user = user;
        this.comments = comments;

        if (user != null) {
            this.userId = user.getId();
        }

        System.out.println("Post constructor with params");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
