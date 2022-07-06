package nbu.onlinetradingsytem.services;

import nbu.onlinetradingsytem.model.Product;
import nbu.onlinetradingsytem.model.Role;
import nbu.onlinetradingsytem.model.User;
import nbu.onlinetradingsytem.repositories.RoleRepository;
import nbu.onlinetradingsytem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;
    
    public UserService(UserRepository repository){
        this.userRepository=repository;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).get();
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
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(roleRepository.findById(3l));
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

        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }

        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }

        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }

        if (userDTO.getRole() != null) {
            user.setRole(userDTO.getRole());
        }

        userRepository.save(user);
    }

    public void removeUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(),
                mapRolesToAuthorities(user.get().getRole())
               );
    }

    private List<SimpleGrantedAuthority> mapRolesToAuthorities(Role role) {
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(role);

        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }
}
