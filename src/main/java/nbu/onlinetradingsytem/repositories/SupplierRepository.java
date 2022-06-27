package nbu.onlinetradingsytem.repositories;

import nbu.onlinetradingsytem.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    Supplier findById(int id);
}
