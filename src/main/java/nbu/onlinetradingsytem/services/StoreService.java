package nbu.onlinetradingsytem.services;

import nbu.onlinetradingsytem.model.Store;
import nbu.onlinetradingsytem.repositories.StoreRepository;
import java.util.Optional;

public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository){
        this.storeRepository = storeRepository;
    }

    public Optional<Store> findById(int id) {return storeRepository.findById(id);}
}
