package org.example.service;

import org.example.entity.User;
import org.example.exception.GeneralException;
import org.example.exception.UserAlreadyExistsException;
import org.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public String addUser(User user) throws Exception {
        User oldUser = userRepository.findByEmail(user.getEmail());
        User user1 = userRepository.findByUserName(user.getUserName());

        if (oldUser == null && user1 == null) {
            userRepository.save(user);
        } else {
            throw new UserAlreadyExistsException("user already exists");
        }

        return "success";
    }

    public User login(User user) throws Exception {
        User oldUser = userRepository.findByEmail(user.getEmail());

        if (oldUser == null) {
            throw new GeneralException("No account present");
        } else {
            return oldUser;
        }
    }
}
