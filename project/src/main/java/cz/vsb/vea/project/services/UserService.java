package cz.vsb.vea.project.services;

import cz.vsb.vea.project.models.User;
import cz.vsb.vea.project.repositories.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepositoryInterface userRepository;

    public void createUser(User u){
        userRepository.save(u);
    }

    public List<User> getUsers(){
        return userRepository.getAllUsers();
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getUserList(long id, String name){
        if(name == null || name.length() == 0)
            return userRepository.getAllUsersNoWithId(id);

        return userRepository.getAllUsersNoWithId(id, name);
    }

    public User getUser(long id){
        return userRepository.find(id);
    }
}
