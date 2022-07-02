package nbu.onlinetradingsytem.repositories;

import nbu.onlinetradingsytem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findById(long id);

    Role findByName(String name);
}
