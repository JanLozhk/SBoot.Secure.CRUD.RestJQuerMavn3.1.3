package app.controller;

import app.model.User;
import app.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserService userService;
    //начиная с версии Framework Spring 4.3 помечать конструктор аннотацией @Autowered не обязательно,
    // если целевой компонент определяет только один конструктор.
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String getAllUsers(Principal princip, Model model) {
//        model.addAttribute("roles", userService.getAllRoles)
        User user = userService.findByLogin(princip.getName());
        model.addAttribute("authorizedUser", user);
        model.addAttribute("user", new User());
        return "users";
    }

}
