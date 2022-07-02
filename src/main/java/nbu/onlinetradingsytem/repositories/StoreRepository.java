package nbu.onlinetradingsytem.repositories;

import nbu.onlinetradingsytem.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer>{


}