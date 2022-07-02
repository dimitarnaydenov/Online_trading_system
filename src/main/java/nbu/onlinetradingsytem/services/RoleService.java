package nbu.onlinetradingsytem.services;

import nbu.onlinetradingsytem.model.Role;
import nbu.onlinetradingsytem.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role findById(int id){
        return roleRepository.findById(id);
    }

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }
}
