package nbu.onlinetradingsytem.services;

import nbu.onlinetradingsytem.model.Product;
import nbu.onlinetradingsytem.model.Store;
import nbu.onlinetradingsytem.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Transactional
    public Optional<Store> findById(int id) {return storeRepository.findById(id);}

    @Transactional
    public List<Store> findAll() {
        if(storeRepository.findAll().isEmpty())
        {
            Store store = new Store();
            store.setName("Empty");
            store.setAddress("Empty");
            addStore(store);
        }
        return storeRepository.findAll();
    }

    @Transactional
    public void addStore(Store store){storeRepository.save(store);}

    @Transactional
    public void editStore(Store storeDTO) {

        Store store = storeRepository.findAll().get(0);

        if (storeDTO.getName() != null ) {
            store.setName(storeDTO.getName());
        }

        if (storeDTO.getAddress() != null) {
            store.setAddress(storeDTO.getAddress());
        }

        storeRepository.save(store);
    }
}
