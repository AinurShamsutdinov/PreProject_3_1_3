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

import java.util.List;
import java.util.Optional;

import static org.springframework.data.repository.init.ResourceReader.Type.JSON;

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
    public String getUserPage(){
        return "user";
    }

    @GetMapping(value = "/admin")
    public String getAdminPage(){
        return "admin";
    }

}
