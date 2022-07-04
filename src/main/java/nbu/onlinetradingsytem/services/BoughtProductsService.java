package nbu.onlinetradingsytem.services;

import nbu.onlinetradingsytem.model.BoughtProducts;
import nbu.onlinetradingsytem.repositories.BoughtProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoughtProductsService {

    @Autowired
    private BoughtProductsRepository boughtProductsRepository;

    @Transactional
    public void addBoughtProduct(BoughtProducts boughtProducts){

        boughtProductsRepository.save(boughtProducts);

    }
}
