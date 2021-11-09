package cz.vsb.vea.project.repositories;

import cz.vsb.vea.project.models.User;

import java.util.List;

public interface UserRepositoryInterface {

    public List<User> getAllUsers();
    public User save(User u);
    public User find(long id);
}
