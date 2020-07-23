/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.memdaos;

import com.appmigos.website.daos.UserDao;
import com.appmigos.website.dtos.Role;
import com.appmigos.website.dtos.User;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.UserDaoException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("memory")
public class UserDaoInMem implements UserDao{
    
    List<User> allUsers = new ArrayList<>();
    Set<Role> roleSet1 = new HashSet<>();
    Set<Role> roleSet2 = new HashSet<>();
    Role role1 = new Role(1, "Admin");
    Role role2 = new Role(2, "Author");

    @Override
    public User getUserById(int id) throws UserDaoException {
        List<User> toCheck = allUsers.stream().filter(u -> u.getId() == id).collect(Collectors.toList());
        if(toCheck.isEmpty()) throw new UserDaoException("No users found for that ID");
        return toCheck.get(0);
    }

    @Override
    public User getUserByName(String name) throws UserDaoException {
        List<User> toCheck = allUsers.stream().filter(u -> u.getUserName()== name).collect(Collectors.toList());
        if(toCheck.isEmpty()) throw new UserDaoException("No users found for that Name");
        return toCheck.get(0);
    }

    @Override
    public List<User> getAllUsers() throws UserDaoException {
         if(allUsers.isEmpty()){
             throw new UserDaoException("No Items");
         }
         
         return allUsers;
    }

    @Override
    public void updateUser(User toEdit) throws UserDaoException, BadUpdateException {
        boolean isValid = false;
        for(User u: allUsers){
            if(u.getId() == toEdit.getId()){
                u.setUserName(toEdit.getUserName());
                u.setPassword(toEdit.getPassword());
                u.setEnabled(toEdit.isEnabled());
                u.setRole(toEdit.getRole());
                isValid = true;
            }
        }
        if (!isValid) {
            throw new BadUpdateException("Invalid Id");
        }
    }

    @Override
    public void deleteUser(int id) throws UserDaoException, BadUpdateException {
        int status = 0;
        User toRemove = null;

        for (User u : allUsers) {
            if (u.getId() == id) {
                toRemove = u;
                status = 1;
                break;
            }
        }

        if (status == 1) {
            allUsers.remove(toRemove);
        } else {
            throw new BadUpdateException("Invalid id");
        }
    }

    @Override
    public User createUser(User toAdd) throws UserDaoException {
        toAdd.setId(allUsers.size() + 1);
        allUsers.add(toAdd);
        return toAdd;
    }
    
    public void setUp(){
        allUsers.clear();
        roleSet1.clear();
        roleSet2.clear();
        
        roleSet1.add(role1);
        roleSet2.add(role2);
        
        User user1 = new User(1, "Admin", "password", true, roleSet1);
        User user2 = new User(2, "Jerry", "password", true, roleSet2);
        User user3 = new User(3, "Bill", "password", true, roleSet2);
        
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        
    }
    
    public void removeAll(){
        allUsers = new ArrayList<>();
    }
    
}
