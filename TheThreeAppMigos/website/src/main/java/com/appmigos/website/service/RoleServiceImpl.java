/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.daos.RoleDao;
import com.appmigos.website.dtos.Role;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.exceptions.RoleDaoException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samg.zun
 */
@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    RoleDao dao;

    @Override
    public List<Role> getAll() throws NoItemsException {
        try {
            return dao.getAllRoles();
        } catch(RoleDaoException ex){
            throw new NoItemsException("No Items");
        }
    }

    @Override
    public Role getById(int id) throws InvalidIdException {
        try {
            return dao.getRoleById(id);
        } catch(RoleDaoException ex){
            throw new InvalidIdException("Invalid id");
        }
    }
    
    
}
