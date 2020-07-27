package com.preproject.springbootcrud.service;

import com.preproject.springbootcrud.model.Role;
import com.preproject.springbootcrud.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao){
        this.roleDao = roleDao;
    }

    public Role findRole(String roleName){
        return roleDao.findByRoleName(roleName);
    }

    public List<Role> findAllRoles(){
        return roleDao.findAll();
    }
}
