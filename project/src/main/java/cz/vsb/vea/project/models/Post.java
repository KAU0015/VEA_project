package cz.vsb.vea.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;

    @NotEmpty
    private String content;

    @Column(name = "user_id", insertable = false, updatable = false)
    private long userId;

    @JsonIgnore
    @ManyToOne
    private User user;

    public Post(){
        System.out.println("Post constructor");
    }

    public Post(long id, LocalDate date, @NotEmpty String content, User user) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.user = user;
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
}
