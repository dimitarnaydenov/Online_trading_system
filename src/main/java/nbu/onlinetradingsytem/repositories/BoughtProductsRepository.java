package nbu.onlinetradingsytem.repositories;


import nbu.onlinetradingsytem.model.BoughtProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoughtProductsRepository extends JpaRepository<BoughtProducts, Integer> {

    @Query(value = "SELECT product_id, SUM(count) FROM trading_system.bought_products GROUP BY product_id",
            nativeQuery = true)
    List<Object[]> getBoughtProductsByCount();

    @Query(value = "SELECT category, SUM(count) FROM trading_system.bought_products INNER JOIN products ON bought_products.product_id=products.id group by category",
            nativeQuery = true)
    List<Object[]> getBoughtProductsByCategory();
}
