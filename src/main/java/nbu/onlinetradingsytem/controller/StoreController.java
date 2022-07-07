package nbu.onlinetradingsytem.controller;

import nbu.onlinetradingsytem.model.*;
import nbu.onlinetradingsytem.services.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StoreController {

    StoreService storeService;
    SupplierService supplierService;
    ProductService productService;
    UserService userService;
    BoughtProductsService boughtProductsService;

    public StoreController(StoreService storeService, SupplierService supplierService, ProductService productService, UserService userService, BoughtProductsService boughtProductsService) {
        this.storeService = storeService;
        this.supplierService = supplierService;
        this.productService = productService;
        this.userService = userService;
        this.boughtProductsService = boughtProductsService;
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

    @PostMapping("/store")
    public String editStore(@ModelAttribute Store store)
    {
        storeService.editStore(store);
        return "redirect:/store";
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
        return "redirect:/";
    }

    @GetMapping("/editProduct")
    public String getEditProductPage()
    {
        return "editProduct";
    }

    @PostMapping("/editProduct/{id}")
    public String editProduct(@PathVariable("id") int id, @ModelAttribute Product product)
    {
        productService.updateProduct(product.getId(),product);
        return "";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam String id) {
        productService.deleteProduct(productService.findById(Integer.parseInt(id)));
        return "redirect:/";
    }

    @PostMapping("/addSupplier")
    public String addSupplier(@ModelAttribute Supplier supplier)
    {
        supplierService.addSupplier(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/editSupplier")
    public String editSupplier(@ModelAttribute Supplier supplier, @RequestParam String id) {
        supplierService.updateSupplier(Integer.parseInt(id), supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/cart")
    public String showCart() {
        return "shoppingcart";
    }

    @PostMapping("/buy")
    public String buy(@RequestParam(value="ids[]") Integer[] ids, @RequestParam(value="counts[]") Integer[] counts)
    {
        User user = userService.findByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        for (int i = 0; i < ids.length; i++) {
            BoughtProducts boughtProducts = new BoughtProducts();
            Product product = productService.findById(ids[i]);
            boughtProducts.setUser(user);
            boughtProducts.setProduct(product);
            boughtProducts.setCount(counts[i]);
            boughtProductsService.addBoughtProduct(boughtProducts);
        }


        return "redirect:/shoppingcart";
    }

}
