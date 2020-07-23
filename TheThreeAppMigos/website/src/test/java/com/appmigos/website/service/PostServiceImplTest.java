/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.TestAppConfig;
import com.appmigos.website.dtos.Post;
import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.InvalidNameException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.exceptions.PostDaoException;
import com.appmigos.website.memdaos.PostDaoInMem;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
 * @author Schmi
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
public class PostServiceImplTest {

    @Autowired
    PostService service;

    @Autowired
    PostDaoInMem dao;

    Set<Tag> tagSet1 = new HashSet<>();
    Tag tag1 = new Tag(1, "Meat");

    public PostServiceImplTest() {
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
     * Test of getPostById method, of class PostServiceImpl.
     */
    @Test
    public void testGetPostByIdGP() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);
        Post expected = new Post(1, "First Post", "First Author", "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        Post toCheck = service.getPostById(1);
        assertEquals(expected, toCheck);

    }

    @Test
    public void testGetPostByIdInvalidId() {

        try {
            Post toCheck = service.getPostById(-1);
            fail("Should get InvalidIdException in test");
        } catch (InvalidIdException ex) {
        }

    }
    
    /** 
     * Test of getPostsByName method, of class PostServiceImpl.
     */
    @Test
    public void testGetPostsByName() throws InvalidNameException, NoItemsException {
        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);
        
        Post expected = new Post(1, "First Post", "First Author", "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));
        
        List<Post> fromDao = service.getPostsByName("First Author");
        
        assertEquals(1, fromDao.size());
        assertTrue(fromDao.contains(expected));
    }
    
    @Test
    public void testGetPostsByNameNullName() throws NoItemsException {
        
        try {
            service.getPostsByName(null);
            fail("should hit InvalidNameException when name is invalid");
        } catch(InvalidNameException ex){}
        
    }
    
    @Test
    public void testGetPostsByNameBlankName() throws NoItemsException {
        
        try {
            service.getPostsByName("   ");
            fail("should hit InvalidNameException when name is invalid");
        } catch(InvalidNameException ex){}
        
    }
    
    @Test
    public void testGetPostsByNameEmptyName() throws NoItemsException {
        
        try {
            service.getPostsByName("");
            fail("should hit InvalidNameException when name is invalid");
        } catch(InvalidNameException ex){}
        
    }
    
    @Test
    public void testGetPostsByNameNoItems() throws InvalidNameException {
        dao.clearPosts();
        try {
            service.getPostsByName("First Author");
            fail("should hit NoItemsException when no Items");
        } catch(NoItemsException ex){}
        
    }

    /**
     * Test of getPostsByTag method, of class PostServiceImpl.
     */
    @Test
    public void testGetPostsByTagGP() throws InvalidIdException, NoItemsException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(1, "First Post", "First Author", "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));
        Post expected3 = new Post(3, "Third Post", "Third Author", "Third Content", tagSet1, true, LocalDate.of(2020, 06, 30), LocalDate.of(2021, 07, 11));

        List<Post> postsByTag = service.getPostsByTag(1);

        assertEquals(2, postsByTag.size());

        assertTrue(postsByTag.contains(expected));
        assertTrue(postsByTag.contains(expected3));

    }

    @Test
    public void testGetPostsByTagInvalidId() throws NoItemsException {

        try {
            service.getPostsByTag(-1);
            fail("Should get InvalidIdExcepiton");
        } catch (InvalidIdException ex) {

        }

    }

    @Test
    public void testGetPostsByTagNoItemsException() throws InvalidIdException {

        service.deletePost(2);

        try {
            service.getPostsByTag(3);
            fail("Should get NoItemsException");
        } catch (NoItemsException ex) {
        }
    }

    /**
     * Test of getAllPosts method, of class PostServiceImpl.
     */
    @Test
    public void testGetAllPostsGP() throws NoItemsException {

        Tag tag2 = new Tag(2, "BBQ");
        Tag tag3 = new Tag(3, "Veggies");

        Set<Tag> tagSet2 = new HashSet<>();
        tagSet1.add(tag1);
        tagSet1.add(tag2);
        tagSet2.add(tag3);

        Post expected = new Post(1, "First Post", "First Author", "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));
        Post expected2 = new Post(2, "Second Post", "Second Author", "Second Content", tagSet2, false, LocalDate.of(2020, 03, 9), LocalDate.of(2021, 03, 23));
        Post expected3 = new Post(3, "Third Post", "Third Author", "Third Content", tagSet1, true, LocalDate.of(2020, 06, 30), LocalDate.of(2021, 07, 11));

        List<Post> allPosts = service.getAllPosts(true);

        assertEquals(2, allPosts.size());
        assertTrue(allPosts.contains(expected));
        assertTrue(allPosts.contains(expected3));

        allPosts = service.getAllPosts(false);
        assertEquals(1, allPosts.size());
        assertTrue(allPosts.contains(expected2));

    }

    @Test
    public void testGetAllPostsNoItemsException() {

        dao.clearPosts();

        try {
            service.getAllPosts(true);
            fail("should see NoItemsException when there are not items");
        } catch (NoItemsException ex) {

        }

    }
    
    @Test
    public void testGetAllGP() throws NoItemsException {

        Tag tag2 = new Tag(2, "BBQ");
        Tag tag3 = new Tag(3, "Veggies");

        Set<Tag> tagSet2 = new HashSet<>();
        tagSet1.add(tag1);
        tagSet1.add(tag2);
        tagSet2.add(tag3);

        Post expected = new Post(1, "First Post", "First Author", "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));
        Post expected2 = new Post(2, "Second Post", "Second Author", "Second Content", tagSet2, false, LocalDate.of(2020, 03, 9), LocalDate.of(2021, 03, 23));
        Post expected3 = new Post(3, "Third Post", "Third Author", "Third Content", tagSet1, true, LocalDate.of(2020, 06, 30), LocalDate.of(2021, 07, 11));

        List<Post> allPosts = service.getAll(true);

        assertEquals(2, allPosts.size());
        assertTrue(allPosts.contains(expected));
        assertTrue(allPosts.contains(expected3));

        allPosts = service.getAllPosts(false);
        assertEquals(1, allPosts.size());
        assertTrue(allPosts.contains(expected2));

    }

    @Test
    public void testGetAllNoItemsException() {

        dao.clearPosts();

        try {
            service.getAll(true);
            fail("should see NoItemsException when there are not items");
        } catch (NoItemsException ex) {

        }

    }

    @Test
    public void testGetAllPostsNoItemsException2() throws PostDaoException, BadUpdateException {

        dao.removePost(2);

        try {
            service.getAllPosts(false);
            fail("should see NoItemsException when there are not items");
        } catch (NoItemsException ex) {

        }

    }

    /**
     * Test of createPost method, of class PostServiceImpl.
     */
    @Test
    public void testCreatePostGP() throws InvalidEntityException, InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Post", "Fourth Author", "Fourth Content", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        Post toCheck = service.createPost(expected);
        assertEquals(expected, toCheck);

        toCheck = service.getPostById(4);
        assertEquals(expected, toCheck);

    }

    @Test
    public void testCreatePostNullTitle() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, null, "Fourth Author", "Fourth Content", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testCreatePostLongTitle() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "aaaaabbbbbaaaaabbbbbaaaaabbbbbaaaaabbbbbaaaaabbbbbaaaaabbbbbaaaaabbbbb", "Fourth Author", "Fourth Content", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreatePostBlankTitle() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "  ", "Fourth Author", "Fourth Content", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreatePostEmptyTitle() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "", "Fourth Author", "Fourth Content", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreatePostNullAuthor() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", null, "Fourth Content", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testCreatePostLongAuthor() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "012345678901234567890123456789012345678901234567890123456789", "Fourth Content", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreatePostBlankAuthor() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "   ", "Fourth Content", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreatePostEmptyAuthor() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "", "Fourth Content", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreatePostNullContent() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", null, tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testCreatePostLongContent() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        char[] letters = new char[65536];
        Arrays.fill(letters, 'a');

        Post expected = new Post(4, "Fourth Title", "Fourth Author", new String(letters), tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testCreatePostBlankContent() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "   ", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreatePostEmptyContent() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreatePostTagsEmpty() {

        Set<Tag> tagSet3 = new HashSet<>();

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "", tagSet3, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testCreatePostNullTags() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "Fourth Content", null, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreatePostNullStart() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "Fourth Content", null, false, null, LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreatePostNullEnd() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "Fourth Content", null, false, LocalDate.of(2020, 9,25), null);

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreatePostStartBeforeToday() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "Fourth Content", null, false, LocalDate.of(2019, 9,25), LocalDate.of(2020, 9,25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreatePostEndBeforeToday() {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "Fourth Content", null, false, LocalDate.of(2020, 9,25), LocalDate.of(2019, 9, 25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testCreatePostEndBeforeStart() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "Fourth Content", null, false, LocalDate.of(2020, 9,25), LocalDate.of(2019, 9, 25));

        try {
            service.createPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of editPost method, of class PostServiceImpl.
     */
    @Test
    public void testEditPostGP() throws InvalidIdException, InvalidEntityException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);
        Post original = new Post(1, "First Post", "First Author", "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));
        Post expected = new Post(1, "Fourth Post", "Fourth Author", "Fourth Content", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        Post fromDao = service.getPostById(1);
        assertEquals(original, fromDao);

        service.editPost(expected);
        fromDao = service.getPostById(1);
        assertEquals(expected, fromDao);
        assertNotEquals(original, expected);
    }

    @Test
    public void testEditPostInvalidId() throws InvalidEntityException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);
        Post expected = new Post(-1, "Fourth Post", "Fourth Author", "Fourth Content", tagSet1, false, LocalDate.of(2020, 9, 13), LocalDate.of(2020, 9,25));

        try {
            service.editPost(expected);
            fail("Should get InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testEditPostNullTitle() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);
        Post expected = new Post(1, null, "First Author", "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPostLongTitle() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);
        Post expected = new Post(1, "0123456789012345678901234567890123456789012345678901234567890123456789", "First Author", "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditPostBlankTitle() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);
        Post expected = new Post(1, "", "First Author", "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditPostNullAuthor() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(1, "First Post", null, "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPostLongAuthor() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(1, "test Title", "0123456789012345678901234567890123456789012345678901234567890123456789", "test Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditPostBlankAuthor() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(1, "First Post", "", "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditPostNullContent() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(1, "First Post", "First Author", null, tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPostLongContent() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        char[] letters = new char[65536];
        Arrays.fill(letters, 'a');

        Post expected = new Post(1, "test Title", "test Author", new String(letters), tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditPostBlankContent() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(1, "First Post", "First Author", null, tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditPostNullTag() throws InvalidIdException {

        Post expected = new Post(1, "test Title", "test Author", "test Content", null, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditPostEmptyTag() throws InvalidIdException {

        Post expected = new Post(1, "test Title", "test Author", "test Content", new HashSet<>(), true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }
    
        @Test
    public void testEditPostNullStart() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "Fourth Content", null, false, null, LocalDate.of(2020, 9,25));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditPostNullEnd() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "Fourth Content", null, false, LocalDate.of(2020, 9,25), null);

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditPostStartBeforeToday() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "Fourth Content", null, false, LocalDate.of(2019, 9,25), LocalDate.of(2020, 9,25));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditPostEndBeforeToday() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "Fourth Content", null, false, LocalDate.of(2020, 9,25), LocalDate.of(2019, 9, 25));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testEditPostEndBeforeStart() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);

        Post expected = new Post(4, "Fourth Title", "Fourth Author", "Fourth Content", null, false, LocalDate.of(2020, 9,25), LocalDate.of(2019, 9, 25));

        try {
            service.editPost(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    //
    /**
     * Test of deletePost method, of class PostServiceImpl.
     */
    @Test
    public void testDeletePostGP() throws InvalidIdException {

        Set<Tag> tagSet1 = new HashSet<>();
        Tag tag1 = new Tag(1, "Meat");
        Tag tag2 = new Tag(2, "BBQ");
        tagSet1.add(tag1);
        tagSet1.add(tag2);
        Post original = new Post(1, "First Post", "First Author", "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6));

        Post toCheck = service.getPostById(1);
        assertEquals(original, toCheck);

        service.deletePost(1);

        try {
            Post deleted = service.getPostById(1);
            fail("Should get InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testDeletePostInvalidId() {

        try {
            service.deletePost(-1);
            fail("Should get InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }
}
