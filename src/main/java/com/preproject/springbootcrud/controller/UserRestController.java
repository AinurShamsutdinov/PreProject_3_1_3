package com.preproject.springbootcrud.controller;

import com.preproject.springbootcrud.DTO.UserDTO;
import com.preproject.springbootcrud.model.Role;
import com.preproject.springbootcrud.model.User;
import com.preproject.springbootcrud.service.RoleService;
import com.preproject.springbootcrud.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserRestController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/userlist")
    public List<User> exampleJson(){
        List<User> userList = userService.findAllUsers();
        return userList;
    }

    @GetMapping(value = "/userget/{id}")
    public User getUser(@PathVariable long id, ModelMap model){
        User user = userService.findById(id);
        return user;
    }

    @GetMapping(value = "/currentuser")
    public User getCurrentUser(Authentication authentication){
        User user = userService.getUserByName(authentication.getName());
        return user;
    }

    @PostMapping (value = "/adduser", consumes = "application/json")
    public ResponseEntity addUser(@RequestBody UserDTO userDto){

        Set<Role> roleSet = new HashSet<>();
        for (String role: userDto.getRoles()){
            if(role.equals("USER")){
                roleSet.add(roleService.findRole("ROLE_USER"));
            } else if(role.equals("ADMIN")){
                roleSet.add(roleService.findRole("ROLE_ADMIN"));
            }
        }
        User user = new User(userDto.getUserName(),
                userDto.getLastName(),
                userDto.getAge(),
                userDto.getEmail(),
                userDto.getPassword(),
                roleSet);
        userService.saveUser(user);
        System.out.println("user added");
        return ResponseEntity.ok("user added");
    }

    @PostMapping (value = "/edituser", consumes = "application/json", produces = "application/json")
    public ResponseEntity editUser(@RequestBody UserDTO userDto){
        User user = userService.findById(userDto.getId());
        user.setUserName(userDto.getUserName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.getRoles().clear();
        for (String role: userDto.getRoles()){
            if (role != null){
                if(role.equals("USER")){
                    user.getRoles().add(userService.findRole("ROLE_USER"));
                } else if(role.equals("ADMIN")){
                    user.getRoles().add(userService.findRole("ROLE_ADMIN"));
                }
            }
        }
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping (value = "/deleteuser/{id}")
    public ResponseEntity deleteUser(@PathVariable long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
