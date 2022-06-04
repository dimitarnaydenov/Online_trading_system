package nbu.onlinetradingsytem.controller;

import nbu.onlinetradingsytem.model.User;
import nbu.onlinetradingsytem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
