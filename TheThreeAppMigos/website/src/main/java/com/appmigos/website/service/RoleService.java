/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.dtos.Role;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public interface RoleService {
    
    public List<Role> getAll() throws NoItemsException;
    
    public Role getById(int id) throws InvalidIdException;
    
}
