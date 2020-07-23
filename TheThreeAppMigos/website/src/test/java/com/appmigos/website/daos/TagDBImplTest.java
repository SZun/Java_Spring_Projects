/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.daos;

import com.appmigos.website.TestAppConfig;
import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.TagDaoException;
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
public class TagDBImplTest {
    
    @Autowired
    TagDao tDao;
    
    @Autowired
    JdbcTemplate template;
    
    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM Post_Tags");
        template.update("DELETE FROM Tags");
        template.update("ALTER TABLE Tags auto_increment = 1");
        template.update("INSERT INTO Tags(tag) VALUES"
                + "('Meat'),"
                + "('Grill'),"
                + "('Summer')");
    }
    
    /**
     * Test of getTagById method, of class TagDBImpl.
     */
    @Test
    public void testGetTagByIdGoldenPath() throws TagDaoException {
        Tag toTest = tDao.getTagById(2);
        assertEquals(2, toTest.getId());
        assertEquals("Grill", toTest.getTag());
    }
    
    @Test
    public void testGetTagByBadId() {
        try {
            tDao.getTagById(-1);
            fail("should hit TagDaoException");
        } catch (TagDaoException ex) {

        }
    }

    /**
     * Test of getAllTags method, of class TagDBImpl.
     */
    @Test
    public void testGetAllTags() throws TagDaoException {
        List<Tag> allTags = tDao.getAllTags();
        Tag first = allTags.get(0);
        Tag last = allTags.get(allTags.size() - 1);
        assertEquals(3, allTags.size());
        assertEquals(1, first.getId());
        assertEquals("Meat", first.getTag());
        assertEquals(3, last.getId());
        assertEquals("Summer", last.getTag());
    }
    
    @Test
    public void testGetAllTagsEmptyResult() {
        try {
            template.update("DELETE FROM Post_Tags");
            template.update("DELETE FROM Tags");
            tDao.getAllTags();
        } catch (TagDaoException ex) {

        }
        
    }

    /**
     * Test of createTag method, of class TagDBImpl.
     */
    @Test
    public void testCreateTag() throws TagDaoException, InvalidEntityException {
        Tag created = tDao.createTag(new Tag("Spatula"));
        assertEquals(4, created.getId());
        assertEquals("Spatula", created.getTag());
    }
    
    @Test
    public void testCreateTagNullObject() throws TagDaoException {
        try {
            Tag nullTag = null;
            tDao.createTag(nullTag);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }
    
    @Test
    public void testCreateTagNullTagName() throws TagDaoException {
        try {
            Tag invalidTag = new Tag(null);
            tDao.createTag(invalidTag);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }
    
    @Test
    public void testCreateTagBadTagName() throws TagDaoException {
        try {
            Tag invalidTag = new Tag(createBadTagName());
            tDao.createTag(invalidTag);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    /**
     * Test of editTag method, of class TagDBImpl.
     */
    @Test
    public void testEditTag() throws TagDaoException, BadUpdateException, InvalidEntityException {
        Tag toEdit = tDao.getTagById(2);
        assertEquals(2, toEdit.getId());
        assertEquals("Grill", toEdit.getTag());
        toEdit.setTag("Gas Grill");
        tDao.editTag(toEdit);
        Tag postEdit = tDao.getTagById(2);
        assertEquals(2, postEdit.getId());
        assertEquals("Gas Grill", postEdit.getTag());
        
    }
    
    @Test
    public void testEditTagNullObject() throws TagDaoException, BadUpdateException {
        try {
            Tag nullTag = null;
            tDao.editTag(nullTag);
        } catch (InvalidEntityException ex) {

        }  
    }
    
    @Test
    public void testEditTagNullTagName() throws TagDaoException, BadUpdateException {
        try {
            Tag invalidTag = new Tag(null);
            tDao.editTag(invalidTag);
        } catch (InvalidEntityException ex) {

        }  
    }
    
    @Test
    public void testEditTagBadTagName() throws TagDaoException, BadUpdateException {
        try {
            Tag invalidTag = new Tag(createBadTagName());
            tDao.editTag(invalidTag);
        } catch (InvalidEntityException ex) {

        }  
    }

    /**
     * Test of removeTag method, of class TagDBImpl.
     */
    @Test
    public void testRemoveTag() throws TagDaoException, BadUpdateException {
        List<Tag> preRemove = tDao.getAllTags();
        Tag first = preRemove.get(0);
        tDao.removeTag(first.getId());
        List<Tag> postRemove = tDao.getAllTags();
        assertFalse(postRemove.contains(first));   
    }
    
    @Test
    public void testRemoveTagBadId() throws TagDaoException {
        try {
            tDao.removeTag(-1);
            fail("Should have hit BadUpdateException");
        } catch (BadUpdateException ex) {

        }    
    }
    
    private String createBadTagName() {
        char[] chars = new char[61];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
    
}
