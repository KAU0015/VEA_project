package cz.vsb.vea.project.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotNull
    private LocalDate dayOfBirth;

    @NotEmpty
    private String password;

    @NotNull
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(){
        System.out.println("User constructor");
    }

    public User(@NotEmpty String username, @NotEmpty String firstName, @NotEmpty String lastName, @NotNull LocalDate dayOfBirth, @NotEmpty String password, @NotNull String role, List<Post> posts) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dayOfBirth = dayOfBirth;
        this.password = password;
        this.role = role;
        this.posts = posts;
        System.out.println("User constructor with params");
    }

    public User(long id, @NotEmpty String username, @NotEmpty String firstName, @NotEmpty String lastName, @NotNull LocalDate dayOfBirth, @NotEmpty String password, @NotNull String role, List<Post> posts) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dayOfBirth = dayOfBirth;
        this.password = password;
        this.role = role;
        this.posts = posts;
        System.out.println("User constructor with params");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(LocalDate dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
