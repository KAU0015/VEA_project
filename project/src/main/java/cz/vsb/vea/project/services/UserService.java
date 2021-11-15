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

        for(User uu:userRepository.getAllUsers()){
            System.out.println(uu.getUsername() + uu.getFirstName());
        }
    }

    public List<User> getUsers(){
        return userRepository.getAllUsers();
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
