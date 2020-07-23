/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.daos.UserDao;
import com.appmigos.website.dtos.Role;
import com.appmigos.website.dtos.User;
import com.appmigos.website.exceptions.UserDaoException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Isaia
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    UserDao uDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User toLoad = uDao.getUserByName(username);
            Set<GrantedAuthority> fakeRoles = new HashSet<>();
            
            for(Role r: toLoad.getRole()){
                fakeRoles.add(new SimpleGrantedAuthority(r.getRole()));
            }
            
            return new org.springframework.security.core.userdetails.User(username, toLoad.getPassword(), fakeRoles);
        } catch (UserDaoException ex) {
            throw new UsernameNotFoundException("Username not found", ex);
        }
        
    }
    
}
