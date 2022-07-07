package nbu.onlinetradingsytem.services;

import nbu.onlinetradingsytem.model.BoughtProducts;
import nbu.onlinetradingsytem.model.help.BoughtProduct;
import nbu.onlinetradingsytem.repositories.BoughtProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoughtProductsService {

    @Autowired
    private BoughtProductsRepository boughtProductsRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public void addBoughtProduct(BoughtProducts boughtProducts){

        boughtProductsRepository.save(boughtProducts);

    }

    public  List<BoughtProduct> getBoughtProductsByCount(){

        List<Object[]> list = boughtProductsRepository.getBoughtProductsByCount();
        List<BoughtProduct> boughtProducts = new ArrayList<>();
        for (Object[] obj : list) {
            BoughtProduct boughtProduct = new BoughtProduct();
            boughtProduct.setName(productService.findById(Integer.parseInt(obj[0].toString())).getName());
            boughtProduct.setCount(Integer.parseInt(obj[1].toString()));
            boughtProducts.add(boughtProduct);
        }
        return boughtProducts;
    }

    public  List<BoughtProduct> getBoughtProductsByCategory(){

        List<Object[]> list = boughtProductsRepository.getBoughtProductsByCategory();
        List<BoughtProduct> boughtProducts = new ArrayList<>();
        for (Object[] obj : list) {
            BoughtProduct boughtProduct = new BoughtProduct();
            boughtProduct.setName(obj[0].toString());
            boughtProduct.setCount(Integer.parseInt(obj[1].toString()));
            boughtProducts.add(boughtProduct);
        }
        return boughtProducts;
    }
}
