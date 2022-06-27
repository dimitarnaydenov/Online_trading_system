package nbu.onlinetradingsytem.services;

import nbu.onlinetradingsytem.model.Supplier;
import nbu.onlinetradingsytem.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (supplierDTO.getName() != null ) {
            supplier.setName(supplierDTO.getName());
        }

        supplierRepository.save(supplier);
    }

    @Transactional
    public void deleteSupplier(Supplier supplier) {
        supplierRepository.delete(supplier);
    }
}
