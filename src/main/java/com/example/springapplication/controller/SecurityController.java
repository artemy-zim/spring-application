package com.example.springapplication.controller;

import com.example.springapplication.dto.UserDto;
import com.example.springapplication.entity.User;
import com.example.springapplication.service.UAService;
import com.example.springapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SecurityController {

    private final UserService userService;
    private final UAService uaService;

    public SecurityController(UserService userService, UAService uaService){
        this.userService = userService;
        this.uaService = uaService;
    }

    @GetMapping("/index")
    public String home(){return "index";}

    @GetMapping("/login")
    public String login(){return "login";}

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByUsername(userDto.getUsername());

        if(existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()){
            result.rejectValue("username", null,
                    "Пользователь с таким именем уже существует!");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        uaService.saveUserAction("Register new user: " + userDto.getUsername());
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
