package nbu.onlinetradingsytem.services;

import nbu.onlinetradingsytem.model.Store;
import nbu.onlinetradingsytem.model.Supplier;
import nbu.onlinetradingsytem.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Transactional
    public void addSupplier(Supplier supplier) {

        supplierRepository.save(supplier);

    }

    @Transactional
    public void updateSupplier(int id, Supplier supplierDTO) {

        Supplier supplier = supplierRepository.findById(id);

        if (supplierDTO.getFirstName() != null ) {
            supplier.setFirstName(supplierDTO.getFirstName());
        }

        if (supplierDTO.getLastName() != null ) {
            supplier.setLastName(supplierDTO.getLastName());
        }

        supplierRepository.save(supplier);
    }

    @Transactional
    public void deleteSupplier(Supplier supplier) {
        supplierRepository.delete(supplier);
    }

    @Transactional
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }
}
