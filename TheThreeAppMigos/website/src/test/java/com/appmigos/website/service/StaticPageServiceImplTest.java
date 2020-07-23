/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.TestAppConfig;
import com.appmigos.website.dtos.Page;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.exceptions.PageDaoException;
import com.appmigos.website.memdaos.PageDaoInMem;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Isaia
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
public class StaticPageServiceImplTest {

    @Autowired
    StaticPageService service;

    @Autowired
    PageDaoInMem dao;

    @BeforeEach
    public void setUp() {
        dao.setUp();
    }

    /**
     * Test of getPageById method, of class StaticPageServiceImpl.
     */
//    Page page1 = new Page(1, "First Page", "First Content");
//    Page page2 = new Page(2, "Second Page", "Second Content");
//    Page page3 = new Page(3, "Third Page", "Third Content");
    @Test
    public void testGetPageByIdGoldenPath() throws InvalidIdException {
        Page toCheck = service.getPageById(3);
        assertEquals(3, toCheck.getId());
        assertEquals("Third Page", toCheck.getTitle());
        assertEquals("Third Content", toCheck.getContent());
    }

    @Test
    public void testGetPageByBadId() {
        try {
            service.getPageById(-1);
            fail("Should have thrown InvalidIdException");
        } catch (InvalidIdException ex) {

        }
    }

    /**
     * Test of getAllPages method, of class StaticPageServiceImpl.
     */
    @Test
    public void testGetAllPagesGoldenPath() throws NoItemsException {
        List<Page> allPages = service.getAllPages();
        Page first = allPages.get(0);
        Page last = allPages.get(allPages.size() - 1);
        assertEquals(3, allPages.size());
        assertEquals(1, first.getId());
        assertEquals("First Page", first.getTitle());
        assertEquals("First Content", first.getContent());
        assertEquals(3, last.getId());
        assertEquals("Third Page", last.getTitle());
        assertEquals("Third Content", last.getContent());
    }

    @Test
    public void testGetAllPagesEmptyResult() {
        try {
            dao.clearList();
            service.getAllPages();
            fail("Should have hit NoItemsException");
        } catch (NoItemsException ex) {

        }
    }

    /**
     * Test of createPage method, of class StaticPageServiceImpl.
     */
    @Test
    public void testCreatePageGoldenPath() throws InvalidEntityException {
        Page toAdd = new Page("Fourth Page", "Fourth Content");
        Page returned = service.createPage(toAdd);
        assertEquals(4, returned.getId());
        assertEquals(toAdd.getTitle(), returned.getTitle());
        assertEquals(toAdd.getContent(), returned.getContent());
    }

    @Test
    public void testCreatePageNullTitle() {
        try {
            Page toAdd = new Page(null, "Fourth Content");
            service.createPage(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePageEmptyTitle() {
        try {
            Page toAdd = new Page("", "Fourth Content");
            service.createPage(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
            Logger.getLogger(StaticPageServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testCreatePageBadTitleLength() {
        try {
            Page toAdd = new Page(dao.createInvalidTitle(), "Fourth Content");
            service.createPage(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePageNullContent() {
        try {
            Page toAdd = new Page("Fourth Page", null);
            service.createPage(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePageEmptyContent() {
        try {
            Page toAdd = new Page("Fourth Page", "");
            service.createPage(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePageBadContentLength() {
        try {
            Page toAdd = new Page("Fourth Page", dao.createInvalidContent());
            service.createPage(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    /**
     * Test of editPage method, of class StaticPageServiceImpl.
     */
    @Test
    public void testEditPageGoldenPath() throws InvalidIdException, PageDaoException, InvalidEntityException {
        Page toEdit = new Page(1, "First Edit", "Edit Content");
        Page preEdit = dao.getPageById(1);
        assertEquals(1, preEdit.getId());
        assertEquals("First Page", preEdit.getTitle());
        assertEquals("First Content", preEdit.getContent());
        service.editPage(toEdit);
        Page postEdit = dao.getPageById(1);
        assertEquals(1, postEdit.getId());
        assertEquals(toEdit.getTitle(), postEdit.getTitle());
        assertEquals(toEdit.getContent(), postEdit.getContent());

    }

    @Test
    public void testEditPageNullTitle() throws InvalidIdException {
        try {
            Page toEdit = new Page(1, null, "Edit Content");
            service.editPage(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditPageEmptyTitle() throws InvalidIdException {
        try {
            Page toEdit = new Page(1, "", "Edit Content");
            service.editPage(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPageBadTitleLength() throws InvalidIdException {
        try {
            Page toEdit = new Page(1, dao.createInvalidTitle(), "Edit Content");
            service.editPage(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPageNullContent() throws InvalidIdException {
        try {
            Page toEdit = new Page(1, "First Edit", null);
            service.editPage(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPageEmptyContent() throws InvalidIdException {
        try {
            Page toEdit = new Page(1, "First Edit", "");
            service.editPage(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
            Logger.getLogger(StaticPageServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testEditPageBadContentLength() throws InvalidIdException {
        try {
            Page toEdit = new Page(1, "First Edit", dao.createInvalidContent());
            service.editPage(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }

    }

    /**
     * Test of deletePage method, of class StaticPageServiceImpl.
     */
    @Test
    public void testDeletePage() throws PageDaoException, InvalidIdException {
        List<Page> preRemove = dao.getAllPages();
        assertEquals(3, preRemove.size());
        service.deletePage(3);
        List<Page> postRemove = dao.getAllPages();
        assertEquals(2, postRemove.size());
        Page first = postRemove.get(0);
        assertEquals(1, first.getId());
        assertEquals("First Page", first.getTitle());
        assertEquals("First Content", first.getContent());
        Page last = postRemove.get(postRemove.size() - 1);
        assertEquals(2, last.getId());
        assertEquals("Second Page", last.getTitle());
        assertEquals("Second Content", last.getContent());
    }
    
    @Test
    public void testDeletePageInvalidId() throws PageDaoException {        
        try {
            service.deletePage(-1);
            fail("Should have hit InvalidIdException");
        } catch (InvalidIdException ex) {

        }
    }

}
