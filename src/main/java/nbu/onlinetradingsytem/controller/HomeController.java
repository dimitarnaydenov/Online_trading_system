package nbu.onlinetradingsytem.controller;

import nbu.onlinetradingsytem.model.User;
import nbu.onlinetradingsytem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHome() {

        return "redirect:/users";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        System.out.println("register request: " + user);
        User userToRegister = userService.registerUser(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());
        return userToRegister == null ? "error_page" : "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest");
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        System.out.println("login request: " + user);
        User authUser = userService.auth(user.getUsername(), user.getPassword());
        if(authUser != null) {
            model.addAttribute("userLogin", authUser.getUsername());
            return "users";
        } else {
            return "error_page";
        }
    }

    @GetMapping("/users")
    public String showUsers(Model model) {

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/setRole")
    public String setRole(@RequestParam String id, Model model) {

        User user = userService.findById(Integer.parseInt(id));
        model.addAttribute("user", user);
        return "setRole";
    }

    @PostMapping("/setRole")
    public String editContact(@ModelAttribute User userDTO, @RequestParam String id) {
        userService.updateUser(Integer.parseInt(id),userDTO);
        return "redirect:/users";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/usersPage";
    }

}
