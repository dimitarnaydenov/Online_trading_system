package nbu.onlinetradingsytem.services;

import nbu.onlinetradingsytem.model.User;
import nbu.onlinetradingsytem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User findById(int id){
        User user = userRepository.findById(id);
        return user;
    }

    @Transactional
    public void updateUser(int id, User userDTO) {

        User user = userRepository.findById(id);

        if (userDTO.getRole() != null) {
            user.setRole(userDTO.getRole());
        }

        userRepository.save(user);
    }
}
