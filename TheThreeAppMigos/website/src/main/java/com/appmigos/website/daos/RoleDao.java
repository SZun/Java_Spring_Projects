/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.daos;

import com.appmigos.website.dtos.Role;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.RoleDaoException;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Isaia
 */
public interface RoleDao {
    
    public Role getRoleById(int id) throws RoleDaoException;
    
    public Role getRoleByName(String name) throws RoleDaoException;
    
    public List<Role> getAllRoles() throws RoleDaoException;
    
    public void updateRole(Role toEdit) throws RoleDaoException, BadUpdateException, InvalidEntityException;
    
    public void removeRole(int id) throws RoleDaoException, BadUpdateException;
    
    public Role createRole(Role toAdd) throws RoleDaoException, InvalidEntityException;
}
