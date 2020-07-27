package com.preproject.springbootcrud.service;

import com.preproject.springbootcrud.model.Role;
import com.preproject.springbootcrud.model.User;
import com.preproject.springbootcrud.repository.RoleDao;
import com.preproject.springbootcrud.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService{

    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    public User findById(Long id){
        return userDao.getOne(id);
    }

    public List<User> findAllUsers(){
        return userDao.findAll();
    }

    public User saveUser(User user){
        if (getUserByName(user.getUsername()) == null){
            setUserCredentials(user);
            userDao.save(user);
        }
        return null;
    }

    public User getUserByName(String userName){
        return userDao.findByUserName(userName);
    }

    public User getUserByEmail(String email){
        return userDao.findByEmail(email);
    }

    public void updateUserById(long id, User user){
        if (getUserByName(user.getUsername()) == null){
            setUserCredentials(user);
            userDao.save(user);
        }
    }

    public void deleteUserById(long id){
        userDao.deleteUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails = userDao.findByEmail(email);
        return userDetails;
    }

    public Optional<User> getUserById(long id){
        return userDao.findById(id);
    }

    public void updateUser(User user){
        setUserCredentials(user);
        userDao.save(user);
    }

    public Role findRole(String roleName){
        return roleDao.findByRoleName(roleName);
    }

    public void setUserCredentials(User user){
        user.setIsAccountNonExpired();
        user.setIsAccountNonLocked();
        user.setIsCredentialsNonExpired();
        user.setIsEnabled();
    }
}
