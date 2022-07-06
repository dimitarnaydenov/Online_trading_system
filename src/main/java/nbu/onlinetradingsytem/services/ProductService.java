package nbu.onlinetradingsytem.services;

import nbu.onlinetradingsytem.model.Product;
import nbu.onlinetradingsytem.model.Role;
import nbu.onlinetradingsytem.model.Supplier;
import nbu.onlinetradingsytem.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id){
        return productRepository.findById(id);
    }

    @Transactional
    public void addProduct(Product product){

        productRepository.save(product);

    }
    
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void updateProduct(int id, Product productDTO) {

        Product product = productRepository.findById(id);

        if (productDTO.getName() != null ) {
            product.setName(productDTO.getName());
        }

        if (productDTO.getPrice() != 0.) {
            product.setPrice(productDTO.getPrice());
        }

        if (productDTO.getSupplier() != null) {
            product.setSupplier(productDTO.getSupplier());
        }

        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

}
