package nbu.onlinetradingsytem.services;

import nbu.onlinetradingsytem.model.User;
import nbu.onlinetradingsytem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public UserService(UserRepository repository){
        this.userRepository=repository;
    }
    public User registerUser(String firstName, String lastName, String username, String password) {
        boolean userExists = userRepository.findByUsername(username).isPresent();

        if(userExists) {
            return null;
        } else if (firstName == null || lastName == null || username == null || password == null) {
            return null;
        } else {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setPassword(password);
            return userRepository.save(user);
        }
    }
    public User auth(String username,String password){
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

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

    public void removeUser(int id) {
        userRepository.deleteById(id);
    }

}
