package nbu.onlinetradingsytem.controller;

import nbu.onlinetradingsytem.model.Product;
import nbu.onlinetradingsytem.model.Role;
import nbu.onlinetradingsytem.model.Store;
import nbu.onlinetradingsytem.services.ProductService;
import nbu.onlinetradingsytem.services.StoreService;
import nbu.onlinetradingsytem.services.SupplierService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class StoreController {

    StoreService storeService;
    SupplierService supplierService;
    ProductService productService;

    public StoreController(StoreService storeService, SupplierService supplierService, ProductService productService) {
        this.storeService = storeService;
        this.supplierService = supplierService;
        this.productService = productService;
    }

    @GetMapping("/store")
    public String getStorePage(Model model) {

        if (storeService.findAll().size() < 1) {

            Store store = new Store();
            store.setName("Empty");
            store.setAddress("Empty");
            storeService.addStore(store);
        }

        model.addAttribute("store", storeService.findAll().get(0));
        return "store";
    }

    @GetMapping("/suppliers")
    public String getSuppliers(Model model)
    {
        model.addAttribute("suppliers",supplierService.findAll());
        return "suppliers";
    }

    @GetMapping("/addProduct")
    public String getAddProductPage()
    {
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute Product product)
    {
        productService.addProduct(product);
        return "";
    }

    @GetMapping("/editProduct")
    public String getEditProductPage()
    {
        return "editProduct";
    }

    @PostMapping("/editProduct")
    public String editProduct(@ModelAttribute Product product)
    {
        productService.updateProduct(product.getId(),product);
        return "";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(productService.findById(id));
        return "redirect:/";
    }

}
