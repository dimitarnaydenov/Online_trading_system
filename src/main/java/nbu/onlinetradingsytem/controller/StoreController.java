package nbu.onlinetradingsytem.controller;

import nbu.onlinetradingsytem.model.*;
import nbu.onlinetradingsytem.model.help.BoughtProduct;
import nbu.onlinetradingsytem.model.help.CartProduct;
import nbu.onlinetradingsytem.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public String getAddProductPage(Model model)
    {
        model.addAttribute("suppliers",supplierService.findAll());
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute Product product, @RequestParam String supplierId)
    {
        product.setSupplier(supplierService.findById(Integer.parseInt(supplierId)));
        productService.addProduct(product);
        return "redirect:/";
    }

    @GetMapping("/editProduct")
    public String getEditProductPage(@RequestParam String id,Model model)
    {
        model.addAttribute("product",productService.findById(Integer.parseInt(id)));
        model.addAttribute("suppliers",supplierService.findAll());
        return "updateProduct";
    }

    @PostMapping("/editProduct")
    public String editProduct(@RequestParam String id, @ModelAttribute Product product, @RequestParam String supplierId)
    {
        product.setSupplier(supplierService.findById(Integer.parseInt(supplierId)));
        productService.updateProduct(product.getId(),product);
        return "redirect:/";
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
    public String editSupplier(@RequestParam String id, Model model) {
        model.addAttribute("supplier",supplierService.findById(Integer.parseInt(id)));
        return "updateSupplier";
    }

    @PostMapping("/editSupplier")
    public String editSupplier(@ModelAttribute Supplier supplier, @RequestParam String id) {
        supplierService.updateSupplier(Integer.parseInt(id), supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/deleteSupplier")
    public String deleteSupplier(@RequestParam String id) {
        supplierService.deleteSupplier(supplierService.findById(Integer.parseInt(id)));
        return "redirect:/suppliers";
    }

    @GetMapping("/cart")
    public String showCart(Model model,@RequestParam Optional<String> id)
    {
        if (id.isPresent() && id.get().equals("success"))
        model.addAttribute("success",true);
        return "shoppingcart";
    }

    @PostMapping("/buy")
    public String buy(@RequestParam(value="ids[]") Integer[] ids, @RequestParam(value="counts[]") Integer[] counts, Model model)
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

        return "shoppingcart";
    }


    @GetMapping("/statistics")
    public String statistics(Model model)
    {
        return "statistics";
    }

    @GetMapping("/soldProducts")
    public ResponseEntity<List<BoughtProduct>> getData() {
        List<BoughtProduct> results = boughtProductsService.getBoughtProductsByCount();
        return new ResponseEntity<List<BoughtProduct>>(results, HttpStatus.OK);
    }

    @GetMapping("/soldProductsByCategory")
    public ResponseEntity<List<BoughtProduct>> getData2() {
        List<BoughtProduct> results = boughtProductsService.getBoughtProductsByCategory();
        return new ResponseEntity<List<BoughtProduct>>(results, HttpStatus.OK);
    }

    @GetMapping("/getPrice")
    public ResponseEntity<Double> getPrice(@RequestParam String id) {
        Product product = productService.findById(Integer.parseInt(id));
        if (product == null || product.isDeleted()) return new ResponseEntity<Double>(0., HttpStatus.OK); ;
        return new ResponseEntity<Double>(product.getFinalPrice(), HttpStatus.OK);
    }

}
