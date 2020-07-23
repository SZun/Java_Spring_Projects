/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.daos;

import com.appmigos.website.TestAppConfig;
import com.appmigos.website.dtos.Page;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.PageDaoException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Isaia
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
@ActiveProfiles("database")
public class PageDBImplTest {

    @Autowired
    PageDao pDao;

    @Autowired
    JdbcTemplate template;

    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM Pages");
        template.update("ALTER TABLE Pages auto_increment = 1");
        template.update("INSERT INTO Pages(title, content) VALUES"
                + "('First Page', 'First Content'),"
                + "('Second Page', 'Second Content'),"
                + "('Third Page', 'Third Content')");
    }

    /**
     * Test of getPageById method, of class PageDBImpl.
     */
    @Test
    public void testGetPageByIdGoldenPath() {
        try {
            Page toTest = pDao.getPageById(2);
            assertEquals(2, toTest.getId());
            assertEquals("Second Page", toTest.getTitle());
            assertEquals("Second Content", toTest.getContent());
        } catch (PageDaoException ex) {
            fail("Should not hit PageDaoException in Golden Path");
        }

    }

    @Test
    public void testGetPageByBadId() {
        try {
            Page toTest = pDao.getPageById(-1);
            fail("Should have hit PageDaoException");
        } catch (PageDaoException ex) {

        }

    }

    /**
     * Test of getPageByTitle method, of class PageDBImpl.
     */
    @Test
    public void testGetPageByTitleGoldenPath() {
        try {
            Page toTest = pDao.getPageByTitle("Third Page");
            assertEquals(3, toTest.getId());
            assertEquals("Third Page", toTest.getTitle());
            assertEquals("Third Content", toTest.getContent());
        } catch (PageDaoException ex) {
            fail("Should not hit PageDaoException in Golden Path");
        }
    }

    @Test
    public void testGetPageByBadTitle() {
        try {
            Page toTest = pDao.getPageByTitle("Fake Page");
            fail("Should have hit PageDaoException");
        } catch (PageDaoException ex) {

        }
    }

    /**
     * Test of getAllPages method, of class PageDBImpl.
     */
    @Test
    public void testGetAllPagesGoldenPath() {
        try {
            List<Page> allPages = pDao.getAllPages();
            Page first = allPages.get(0);
            Page last = allPages.get(allPages.size() - 1);
            assertEquals(3, allPages.size());
            assertEquals(1, first.getId());
            assertEquals("First Page", first.getTitle());
            assertEquals("First Content", first.getContent());
            assertEquals(3, last.getId());
            assertEquals("Third Page", last.getTitle());
            assertEquals("Third Content", last.getContent());
        } catch (PageDaoException ex) {
            fail("Should not hit PageDaoException in Golden Path");
        }
    }

    @Test
    public void testGetAllPagesEmptyReturn() {
        try {
            template.update("DELETE FROM Pages");
            List<Page> allPages = pDao.getAllPages();
            fail("Should have hit PageDaoException");
        } catch (PageDaoException ex) {

        }
    }

    /**
     * Test of createPage method, of class PageDBImpl.
     */
    @Test
    public void testCreatePageGoldenPath() throws PageDaoException, InvalidEntityException {

        Page toAdd = new Page("Fourth Page", "Fourth Content");
        Page toCheck = pDao.createPage(toAdd);
        assertEquals(4, toCheck.getId());
        assertEquals(toAdd.getTitle(), toCheck.getTitle());
        assertEquals(toAdd.getContent(), toCheck.getContent());

    }

    @Test
    public void testCreatePageNullObject() throws PageDaoException {
        try {
            Page toAdd = null;
            pDao.createPage(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePageNullTitle() throws PageDaoException {
        try {
            Page invalidPage = new Page(null, "Fourth Content");
            pDao.createPage(invalidPage);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePageNullContent() throws PageDaoException {
        try {
            Page invalidPage = new Page("Fourth Page", null);
            pDao.createPage(invalidPage);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePageBadTitleLength() throws PageDaoException {
        try {
            Page invalidPage = new Page(createBadTitle(), "Fourth Content");
            pDao.createPage(invalidPage);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePageBadContentLength() throws PageDaoException {
        try {
            Page invalidPage = new Page("Fourth Page", createBadContent());
            pDao.createPage(invalidPage);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of editPage method, of class PageDBImpl.
     */
    @Test
    public void testEditPageGoldenPath() throws PageDaoException, BadUpdateException, InvalidEntityException {
        Page toEdit = pDao.getPageById(1);
        toEdit.setTitle("First Page Altered");
        toEdit.setContent("First Content Altered");
        pDao.editPage(toEdit);
        Page postEdit = pDao.getPageById(1);
        assertEquals(toEdit.getId(), postEdit.getId());
        assertEquals(toEdit.getTitle(), postEdit.getTitle());
        assertEquals(toEdit.getContent(), postEdit.getContent());
    }

    @Test
    public void testEditPageBadId() throws PageDaoException, InvalidEntityException {
        try {
            Page toEdit = new Page(5, "Fake Page", "Fake Content");
            pDao.editPage(toEdit);
            fail("Should have hit BadUpdateException");
        } catch (BadUpdateException ex) {

        }

    }

    @Test
    public void testEditPageNullObject() throws PageDaoException, BadUpdateException {
        try {
            Page toEdit = null;
            pDao.editPage(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }

    }

    @Test
    public void testEditPageNullTitle() throws PageDaoException, BadUpdateException {
        try {
            Page invalidPage = new Page(1, null, "Fake Content");
            pDao.editPage(invalidPage);
            fail("Should have hit BadUpdateException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPageNullContent() throws PageDaoException, BadUpdateException {
        try {
            Page invalidPage = new Page(1, "Fake Page", null);
            pDao.editPage(invalidPage);
            fail("Should have hit BadUpdateException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPageBadTitleLength() throws PageDaoException, BadUpdateException {
        try {
            Page invalidPage = new Page(1, createBadTitle(), "Fake Content");
            pDao.editPage(invalidPage);
            fail("Should have hit BadUpdateException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPageBadContentLength() throws PageDaoException, BadUpdateException {
        try {
            Page invalidPage = new Page(1, "Fake Page", createBadContent());
            pDao.editPage(invalidPage);
            fail("Should have hit BadUpdateException");
        } catch (InvalidEntityException ex) {

        }
    }

    /**
     * Test of removePage method, of class PageDBImpl.
     */
    @Test
    public void testRemovePage() {
        try {
            List<Page> ogList = pDao.getAllPages();
            Page toRemove = ogList.get(ogList.size() - 1);
            pDao.removePage(toRemove.getId());
            List<Page> testList = pDao.getAllPages();
            Page first = testList.get(0);
            Page last = testList.get(testList.size() - 1);
            assertEquals(3, ogList.size()); // test ogList size, don't care about contents
            assertEquals(3, toRemove.getId()); // test to see the expected Page is being removed
            assertEquals(2, testList.size());
            assertEquals(1, first.getId());
            assertEquals("First Page", first.getTitle());
            assertEquals("First Content", first.getContent());
            assertEquals(2, last.getId());
            assertEquals("Second Page", last.getTitle());
            assertEquals("Second Content", last.getContent());

        } catch (PageDaoException ex) {
            fail("Should not hit PageDaoException in Golden Path");
        } catch (BadUpdateException ex) {
            fail("Should not hit BadUpdateException in Golden Path");
        }
    }

    @Test
    public void testRemovePageBadId() throws PageDaoException {
        try {
            pDao.removePage(-1);
            fail("Should have hit BadUpdateException");
        } catch (BadUpdateException ex) {

        }
    }

    private String createBadTitle() {
        char[] chars = new char[61];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }

    private String createBadContent() {
        char[] chars = new char[65536];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }

}
