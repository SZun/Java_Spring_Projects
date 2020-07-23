/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.daos;

import com.appmigos.website.dtos.Page;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.PageDaoException;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Isaia
 */
public interface PageDao {
 
    public Page getPageById(int id) throws PageDaoException;
    
    public Page getPageByTitle(String title) throws PageDaoException;
    
    public List<Page> getAllPages() throws PageDaoException;
    
    public Page createPage(Page toAdd) throws PageDaoException, InvalidEntityException;
    
    public void editPage(Page toEdit) throws PageDaoException, BadUpdateException, InvalidEntityException;
    
    public void removePage(int id) throws PageDaoException, BadUpdateException;
}
