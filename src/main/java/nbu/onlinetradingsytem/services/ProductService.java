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
    public void updateProduct(int id, Product productDTO) {

        Product product = productRepository.findById(id);

        if (productDTO.getName() != null ) {
            product.setName(productDTO.getName());
        }

        if (productDTO.getPrice() != 0.) {
            product.setPrice(productDTO.getPrice());
        }

        if (productDTO.getImageURL() != null) {
            product.setImageURL(productDTO.getImageURL());
        }

        if (productDTO.getCategory() != null) {
            product.setCategory(productDTO.getCategory());
        }

        if (productDTO.getSupplier() != null) {
            product.setSupplier(productDTO.getSupplier());
        }

        if (productDTO.getStartDiscount() != null) {
            product.setStartDiscount(productDTO.getStartDiscount());
        }

        if (productDTO.getEndDiscount() != null) {
            product.setEndDiscount(productDTO.getEndDiscount());
        }

        if (productDTO.getDiscount() != 0.) {
            product.setDiscount(String.valueOf(productDTO.getDiscount()));
        }

        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Product product) {
        product.setDeleted(true);
        productRepository.save(product);
//        productRepository.delete(product);
    }

}
