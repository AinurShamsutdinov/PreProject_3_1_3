package com.preproject.springbootcrud.controller;

import com.preproject.springbootcrud.model.Role;
import com.preproject.springbootcrud.model.User;
import com.preproject.springbootcrud.service.RoleService;
import com.preproject.springbootcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/")
    public String startingPage(){
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/user")
    public String getUserPage(ModelMap model, Authentication authentication){
        model.addAttribute("userData", userService.getUserByName(authentication.getName()));
        return "user";
    }

    @GetMapping(value = "/admin")
    public String getAdminPage(ModelMap model, Authentication authentication){
        model.addAttribute("userData", userService.getUserByName(authentication.getName()));
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("allroles", roleService.findAllRoles());
        return "admin";
    }

    @PostMapping(value = "/adduser")
    public String addUser( @ModelAttribute("user") User user){
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable long id,  @ModelAttribute User user){
        Role role = roleService.findRole("ROLE_USER");
        user.addRole(role);
        userService.updateUser(user);
        return "redirect:/admin";
    }



    @GetMapping(value = "/deleteuser/{id}")
    public String deleteUser(@PathVariable long id, ModelMap model){
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @RequestMapping("/getuser")
    @ResponseBody
    public Optional<User> getUser(long id){
        return userService.getUserById(id);
    }


}
