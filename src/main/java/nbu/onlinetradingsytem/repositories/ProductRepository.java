package nbu.onlinetradingsytem.repositories;

import nbu.onlinetradingsytem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(int id);
}
