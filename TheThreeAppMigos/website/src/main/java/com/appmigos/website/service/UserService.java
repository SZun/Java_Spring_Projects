/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.dtos.User;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public interface UserService {
    
    public List<User> getAll() throws NoItemsException;
    
    public User getById(int id) throws InvalidIdException;

    public void deleteById(Integer id) throws InvalidIdException;

    public void editUser(User toEdit) throws InvalidIdException, InvalidEntityException;
    
}
