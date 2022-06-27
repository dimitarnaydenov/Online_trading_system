package service;

import model.Store;
import repository.StoreRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository){
        this.storeRepository = storeRepository;
    }

    public Optional<Store> findById(int id) {return storeRepository.findById(id);}
}
