/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.daos.UserDao;
import com.appmigos.website.dtos.User;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.exceptions.UserDaoException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samg.zun
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserDao dao;

    @Override
    public List<User> getAll() throws NoItemsException {
        try {
            return dao.getAllUsers();
        } catch(UserDaoException ex){
            throw new NoItemsException("No Items");
        }
    }

    @Override
    public User getById(int id) throws InvalidIdException {
        try {
            return dao.getUserById(id);
        } catch(UserDaoException ex){
            throw new InvalidIdException("Invalid Id");
        }
    }

    @Override
    public void deleteById(Integer id) throws InvalidIdException {
        try {
            dao.deleteUser(id);
        } catch (UserDaoException | BadUpdateException ex) {
            throw new InvalidIdException("Error, invalid id");
        }
    }

    @Override
    public void editUser(User toEdit) throws InvalidIdException, InvalidEntityException {
        validateUser(toEdit);
        
        try {
            dao.updateUser(toEdit);
        }
        catch (UserDaoException | BadUpdateException ex) {
            throw new InvalidIdException("Error, invalid id");
        }
    }

    private void validateUser(User toEdit) throws InvalidEntityException {
        if(toEdit.getUserName() == null || toEdit.getUserName().trim().length() == 0 
                || toEdit.getUserName().trim().length() > 30 ||
        toEdit.getPassword() == null || toEdit.getPassword().trim().length() == 0 
                || toEdit.getPassword().trim().length() > 100 || 
                toEdit.getRole() == null || toEdit.getRole().size() == 0 ){
            throw new InvalidEntityException("Invalid entity");
        }
    }
    
}
