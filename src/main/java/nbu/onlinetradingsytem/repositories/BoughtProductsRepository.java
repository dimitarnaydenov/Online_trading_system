package nbu.onlinetradingsytem.repositories;


import nbu.onlinetradingsytem.model.BoughtProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoughtProductsRepository extends JpaRepository<BoughtProducts, Integer> {
}
