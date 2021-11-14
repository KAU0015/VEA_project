package cz.vsb.vea.project.services;

import cz.vsb.vea.project.models.User;
import cz.vsb.vea.project.repositories.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepositoryInterface userRepository;

    public void createUser(User u){
        userRepository.save(u);

        for(User uu:userRepository.getAllUsers()){
            System.out.println(u.getUsername());
        }
    }
}
