/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.dtos.Page;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Schmi
 */
public interface StaticPageService {
    
    Page getPageById (int id) throws InvalidIdException;
    List<Page> getAllPages() throws NoItemsException;
    Page createPage (Page toCreate) throws InvalidEntityException;
    void editPage (Page toEdit) throws InvalidIdException, InvalidEntityException;
    void deletePage (int id) throws InvalidIdException;
    
    
}
