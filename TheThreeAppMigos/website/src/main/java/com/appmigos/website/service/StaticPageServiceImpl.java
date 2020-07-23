/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.daos.PageDao;
import com.appmigos.website.dtos.Page;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.exceptions.PageDaoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Schmi
 */
@Service
public class StaticPageServiceImpl implements StaticPageService {

    @Autowired
    PageDao pageDao;

    @Override
    public Page getPageById(int id) throws InvalidIdException {
        
        try {
            return pageDao.getPageById(id);
        } catch (PageDaoException ex) {
            throw new InvalidIdException("Error, invalid id");
        }
        
    }

    @Override
    public List<Page> getAllPages() throws NoItemsException {

        List<Page> allPages;
        try {
            allPages = pageDao.getAllPages();
        } catch (PageDaoException ex) {
            throw new NoItemsException("No pages to show");
        }

        return allPages;
    }

    @Override
    public Page createPage(Page toCreate) throws InvalidEntityException {
        
        validate(toCreate);
        
        try {
            return pageDao.createPage(toCreate);
        } catch (PageDaoException ex) {
            throw new InvalidEntityException("Error, invalid entity");
        }

    }

    @Override
    public void editPage(Page toEdit) throws InvalidIdException, InvalidEntityException {

        validate(toEdit);

        try {
            pageDao.editPage(toEdit);
        } catch (PageDaoException | BadUpdateException ex) {
            throw new InvalidIdException("Invalid Entity");
        } 

    }

    @Override
    public void deletePage(int id) throws InvalidIdException {

        try {
            pageDao.removePage(id);
        } catch (PageDaoException | BadUpdateException ex) {
            throw new InvalidIdException("Invalid id");
        } 
        
    }

    private void validate(Page toCreate) throws InvalidEntityException {

        if (toCreate.getTitle() == null
                || toCreate.getTitle().trim().length() > 60
                || toCreate.getTitle().trim().length() == 0
                || toCreate.getContent() == null
                || toCreate.getContent().trim().length() > 65535
                || toCreate.getContent().trim().length() == 0) {
            throw new InvalidEntityException("Error, invalid post entity");
        }
    }

}
