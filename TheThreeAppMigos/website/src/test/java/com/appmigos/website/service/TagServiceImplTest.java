/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.TestAppConfig;
import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.memdaos.TagDaoInMem;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author samg.zun
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
public class TagServiceImplTest {
    
    @Autowired
    TagService toTest;
    
    @Autowired
    TagDaoInMem dao;
    
    String testString = "Cf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6z";
    
    public TagServiceImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        dao.setUp();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllTags method, of class TagServiceImpl.
     */
    @Test
    public void testGetAllTags() throws NoItemsException {
        Tag expected = new Tag(1, "food");
        Tag expected2 = new Tag(2, "grill");
        Tag expected3 = new Tag(3, "summer");
        
        List<Tag> fromDao = toTest.getAllTags();
        assertEquals(3, fromDao.size());
        assertTrue(fromDao.contains(expected));
        assertTrue(fromDao.contains(expected2));
        assertTrue(fromDao.contains(expected3));
    }
    
    @Test
    public void testGetAllTagsNoItems() {
        dao.removeAll();
        try {
            toTest.getAllTags();
            fail("should hit NoItemsException when no items available");
        } catch(NoItemsException ex){}
    }

    /**
     * Test of getTagById method, of class TagServiceImpl.
     */
    @Test
    public void testGetTagById() throws InvalidIdException {
        Tag expected = new Tag(1, "food");
        
        Tag fromDao = toTest.getTagById(1);
        
        assertEquals(expected, fromDao);
    }

    @Test
    public void testGetTagByIdInvalidId() {
        try {
            toTest.getTagById(4);
            fail("should hit InvalidIdException when no items available");
        } catch(InvalidIdException ex){}
    }

    
    /**
     * Test of createTag method, of class TagServiceImpl.
     */
    @Test
    public void testCreateTag() throws InvalidEntityException {
        Tag expected = new Tag("banan");
        
        Tag fromDao = toTest.createTag(expected);
        expected.setId(4);
        
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testCreateTagNullTag() {
        Tag testTag = new Tag(null);
        
        try {
            toTest.createTag(testTag);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testCreateTagBlankTag() {
        Tag testTag = new Tag("    ");
        
        try {
            toTest.createTag(testTag);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testCreateTagEmptyTag() {
        Tag testTag = new Tag("");
        
        try {
            toTest.createTag(testTag);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testCreateTagEmptyTooLongTag() {
        Tag testTag = new Tag(testString);
        
        try {
            toTest.createTag(testTag);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }

    /**
     * Test of editTag method, of class TagServiceImpl.
     */
    @Test
    public void testEditTag() throws InvalidIdException, InvalidEntityException {
        Tag expected = new Tag(1, "banan");
        Tag original = new Tag(1, "food");
        
        Tag fromDao = toTest.getTagById(1);
        assertEquals(original, fromDao);
        
        toTest.editTag(expected);
        
        fromDao = toTest.getTagById(1);
        assertEquals(expected, fromDao);
        assertNotEquals(original, fromDao);
    }
    
    @Test
    public void testEditTagNullTag() throws InvalidIdException {
        Tag testTag = new Tag(1, null);
        
        try {
            toTest.editTag(testTag);
            fail("should hit InvalidEntityException when entity is invalid");
        }
        catch(InvalidEntityException ex) {}
    }
    
    @Test
    public void testEditTagBlankTag() throws InvalidIdException {
        Tag testTag = new Tag(1, "   ");
        
        try {
            toTest.editTag(testTag);
            fail("should hit InvalidEntityException when entity is invalid");
        }
        catch(InvalidEntityException ex) {}
    }
    
    @Test
    public void testEditTagEmptyTag() throws InvalidIdException {
        Tag testTag = new Tag(1, "");
        
        try {
            toTest.editTag(testTag);
            fail("should hit InvalidEntityException when entity is invalid");
        }
        catch(InvalidEntityException ex) {}
    }
    
    @Test
    public void testEditTagTooLongTag() throws InvalidIdException {
        Tag testTag = new Tag(1, testString);
        
        try {
            toTest.editTag(testTag);
            fail("should hit InvalidEntityException when entity is invalid");
        }
        catch(InvalidEntityException ex) {}
    }
    
    @Test
    public void testEditTagInvalidId() throws InvalidEntityException {
        Tag testTag = new Tag(4, "banan");
        
        try {
            toTest.editTag(testTag);
            fail("should hit InvalidIdException when id is invalid");
        }
        catch(InvalidIdException ex) {}
    }

    /**
     * Test of deleteTag method, of class TagServiceImpl.
     */
    @Test
    public void testDeleteTag() throws InvalidIdException {
        Tag toRemove = new Tag(1, "food");
        
        Tag fromDao = toTest.getTagById(1);
        assertEquals(toRemove, fromDao);
        
        toTest.deleteTag(1);
        
        try {
            toTest.getTagById(1);
            fail("should hit InvalidIdException when id is invalid");
        } catch(InvalidIdException ex){}
    }
    
    @Test
    public void testDeleteTagInvalidId() {
        try {
            toTest.deleteTag(4);
            fail("should hit InvalidIdException when id is invalid");
        } catch(InvalidIdException ex){}
    }
    
}
