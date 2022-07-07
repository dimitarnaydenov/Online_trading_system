package nbu.onlinetradingsytem.controller;

import nbu.onlinetradingsytem.model.Product;
import nbu.onlinetradingsytem.model.Role;
import nbu.onlinetradingsytem.model.User;
import nbu.onlinetradingsytem.services.ProductService;
import nbu.onlinetradingsytem.services.RoleService;
import nbu.onlinetradingsytem.services.StoreService;
import nbu.onlinetradingsytem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    UserService userService;
    ProductService productService;
    RoleService roleService;
    StoreService storeService;

    @Autowired
    public HomeController(UserService userService, ProductService productService, RoleService roleService, StoreService storeService)
    {
        this.userService = userService;
        this.productService = productService;
        this.roleService = roleService;
        this.storeService = storeService;
    }

    @GetMapping("/")
    public String showHome(Model model) {


        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("store", storeService.findAll().get(0));
        return "index";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        User userToRegister = userService.registerUser(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());
        return /*userToRegister == null ? "error_page" :*/ "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest");
        return "login";
    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute User user, Model model) {
//        System.out.println("login request: " + user);
//        User authUser = userService.auth(user.getUsername(), user.getPassword());
//        if(authUser != null) {
//            model.addAttribute("userLogin", authUser.getUsername());
//            return "users";
//        } else {
//            return "error_page";
//        }
//    }

    @GetMapping("/users")
    public String showUsers(Model model) {

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam String id, Model model) {

        User user = userService.findById(Integer.parseInt(id));
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/editUser")
    public String editContact(@ModelAttribute User user, @RequestParam("roleName") String roleName, @RequestParam String id) {
        user.setRole(roleService.findByName(roleName));
        userService.updateUser(Integer.parseInt(id),user);
        return "redirect:/users";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam String id) {
        userService.removeUser(Integer.parseInt(id));
        return "redirect:/users";
    }

    @GetMapping("/users/new")
    public String CreateUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "users";
    }


}
