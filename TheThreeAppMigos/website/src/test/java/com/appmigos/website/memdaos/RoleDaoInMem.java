/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.memdaos;

import com.appmigos.website.daos.RoleDao;
import com.appmigos.website.dtos.Role;
import com.appmigos.website.exceptions.RoleDaoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("memory")
public class RoleDaoInMem implements RoleDao{
    
    List<Role> allRoles = new ArrayList<>();

    @Override
    public Role getRoleById(int id) throws RoleDaoException {
        List<Role> toCheck = allRoles.stream().filter(r -> r.getId() == id).collect(Collectors.toList());
        if(toCheck.isEmpty()) throw new RoleDaoException("No role found for that ID");
        return toCheck.get(0);
    }

    @Override
    public Role getRoleByName(String name) throws RoleDaoException {
        List<Role> toCheck = allRoles.stream().filter(r -> r.getRole()== name).collect(Collectors.toList());
        if(toCheck.isEmpty()) throw new RoleDaoException("No role found for that name");
        return toCheck.get(0);
    }

    @Override
    public List<Role> getAllRoles() throws RoleDaoException {
        if( allRoles.isEmpty()){
            throw new RoleDaoException("No Items");
        }
        
        return allRoles;
    }

    @Override
    public void updateRole(Role toEdit) throws RoleDaoException {
        for(Role r: allRoles){
            if(r.getId() == toEdit.getId()){
                r.setRole(toEdit.getRole());
            }
        }
    }

    @Override
    public void removeRole(int id) throws RoleDaoException {
        for(Role r: allRoles){
            if(r.getId() == id){
                allRoles.remove(allRoles.indexOf(r));
            }
        }
    }

    @Override
    public Role createRole(Role toAdd) throws RoleDaoException {
        toAdd.setId(allRoles.size() + 1);
        allRoles.add(toAdd);
        return toAdd;
    }
    
    public void setUp(){
        
        allRoles.clear();
        
        Role role1 = new Role(1, "Admin");
        Role role2 = new Role(2, "Author");
        Role role3 = new Role(3, "Guest");
        
        allRoles.add(role1);
        allRoles.add(role2);
        allRoles.add(role3);
        
    }
    
    public void removeAll(){
        allRoles.clear();
    }
}
