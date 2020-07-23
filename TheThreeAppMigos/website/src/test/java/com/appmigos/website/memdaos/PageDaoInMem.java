/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.memdaos;

import com.appmigos.website.daos.PageDao;
import com.appmigos.website.dtos.Page;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.PageDaoException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class PageDaoInMem implements PageDao {

    List<Page> allPages = new ArrayList<>();

    @Override
    public Page getPageById(int id) throws PageDaoException {
        List<Page> toCheck = allPages.stream().filter(p -> p.getId() == id).collect(Collectors.toList());
        if(toCheck.isEmpty()) throw new PageDaoException("No pages found for that ID");
        return toCheck.get(0);
    }

    @Override
    public Page getPageByTitle(String title) throws PageDaoException {
        List<Page> toCheck = allPages.stream().filter(p -> p.getTitle() == title).collect(Collectors.toList());
        if(toCheck.isEmpty()) throw new PageDaoException("No pages found for that Title");
        return toCheck.get(0);
    }

    @Override
    public List<Page> getAllPages() throws PageDaoException {
        if(allPages.isEmpty()) throw new PageDaoException("No pages found");
        return allPages;
    }

    @Override
    public Page createPage(Page toAdd) throws PageDaoException, InvalidEntityException {
        if(toAdd == null) throw new InvalidEntityException("Page toAdd cannot be null");
        toAdd.setId(allPages.size() + 1);
        allPages.add(toAdd);
        return toAdd;
    }

    @Override
    public void editPage(Page toEdit) throws PageDaoException, BadUpdateException, InvalidEntityException {
        if(toEdit == null) throw new InvalidEntityException("Page toEdit cannot be null");
        boolean isValid = false;
        for(Page p: allPages){
            if(p.getId() == toEdit.getId()){
                p.setTitle(toEdit.getTitle());
                p.setContent(toEdit.getContent());
                isValid = true;
                break;
            }
        }
        if(!isValid) throw new BadUpdateException("No pages updated");
    }

    @Override
    public void removePage(int id) throws PageDaoException, BadUpdateException {
        boolean isValid = false;
        for(Page p: allPages){
            if(p.getId() == id){
                allPages.remove(allPages.indexOf(p));
                isValid = true;
                break;
            }
        }
        if(!isValid) throw new BadUpdateException("No pages removed");
    }

    public void setUp() {
        allPages.clear();

        Page page1 = new Page(1, "First Page", "First Content");
        Page page2 = new Page(2, "Second Page", "Second Content");
        Page page3 = new Page(3, "Third Page", "Third Content");

        allPages.add(page1);
        allPages.add(page2);
        allPages.add(page3);
    }
    
    public void clearList(){
        allPages.clear();
    }
    
    public String createInvalidContent(){
        char[] characters = new char[65536];
        Arrays.fill(characters, 'a');
        return new String(characters);
    }
    
    public String createInvalidTitle(){
        char[] characters = new char[61];
        Arrays.fill(characters, 'a');
        return new String(characters);
    }

}
