package com.preproject.springbootcrud.service;

import com.preproject.springbootcrud.model.Role;
import com.preproject.springbootcrud.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    RoleDao roleDao;

    @Autowired
    public StringToRoleConverter(RoleDao roleDao){
        this.roleDao = roleDao;
    }

    @Override
    public Role convert(String str) {
        if(str.equals("ROLE_ADMIN")){
            return roleDao.findByRoleName("ROLE_ADMIN");
        } else if (str.equals("ROLE_USER")){
            return roleDao.findByRoleName("ROLE_USER");
        }
        return null;
    }
}
