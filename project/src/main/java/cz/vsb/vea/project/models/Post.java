package cz.vsb.vea.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    protected LocalDateTime date;

    @NotEmpty(message = "Cannot be empty!")
    protected String content;

    @Column(name = "user_id", insertable = false, updatable = false)
    protected long userId;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    protected User user;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    protected List<Comment> comments;

    public Post(){

    }

    public Post(LocalDateTime date, @NotEmpty String content, User user, List<Comment> comments) {
        this.date = date;
        this.content = content;
        this.user = user;
        this.comments = comments;

        if (user != null) {
            this.userId = user.getId();
        }

    }

    public Post(long id, LocalDateTime date, @NotEmpty String content, User user, List<Comment> comments) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.user = user;
        this.comments = comments;

        if (user != null) {
            this.userId = user.getId();
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
