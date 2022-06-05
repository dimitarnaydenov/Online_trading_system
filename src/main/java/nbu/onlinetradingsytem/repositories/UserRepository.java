package nbu.onlinetradingsytem.repositories;

import nbu.onlinetradingsytem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);
    
    Optional<User> findByUsernameAndPassword(String username, String password);
    
    Optional<User> findByUsername(String username);
}
