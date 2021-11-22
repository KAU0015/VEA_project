package cz.vsb.vea.project.repositories;

import cz.vsb.vea.project.models.User;

import java.util.List;

public interface UserRepositoryInterface {

    public List<User> getAllUsers();
    public void save(User u);
    public User find(long id);
    public User findByUsername(String username);
    public List<User> getAllUsersNoWithId(long id);
    public List<User> getAllUsersNoWithId(long id, String name);
}
