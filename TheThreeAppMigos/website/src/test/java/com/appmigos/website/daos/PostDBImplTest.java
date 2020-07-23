/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.daos;

import com.appmigos.website.TestAppConfig;
import com.appmigos.website.dtos.Post;
import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.PostDaoException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class PostDBImplTest {

    @Autowired
    PostDao pDao;

    @Autowired
    JdbcTemplate template;

    LocalDate testStart = LocalDate.of(2020, 06, 3);
    LocalDate testEnd = LocalDate.of(2020, 06, 6);

    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM Post_Tags");
        template.update("DELETE FROM Tags");
        template.update("ALTER TABLE Tags auto_increment = 1");
        template.update("INSERT INTO Tags(tag) VALUES"
                + "('tag1'),"
                + "('tag2')");
        template.update("DELETE FROM Posts");
        template.update("ALTER TABLE Posts auto_increment = 1");
        template.update("INSERT INTO Posts(title, author, content, approved, start, end) VALUES"
                + "('First Title', 'First Author', 'First Content', 1, '2020-12-8', '2020-12-30'),"
                + "('Second Title', 'Second Author', 'Second Content', 0, '2020-12-13', '2020-12-25'),"
                + "('Third Title', 'Third Author', 'Third Content', 1, '2020-10-11', '2020-10-30')");
        template.update("INSERT INTO Post_Tags(postId, tagId) VALUES"
                + "(1, 1), "
                + "(2, 2), "
                + "(3, 2)");
    }

    /**
     * Test of getPostById method, of class PostDBImpl.
     */
    @Test
    public void testGetPostByIdGoldenPath() {

        try {
            Post toTest = pDao.getPostById(1);
            assertEquals(1, toTest.getId());
            assertEquals("First Title", toTest.getTitle());
            assertEquals("First Author", toTest.getAuthor());
            assertEquals("First Content", toTest.getContent());
            assertEquals(1, toTest.getTags().size());
            assertTrue(toTest.isApproved());
            assertEquals(LocalDate.of(2020, 12, 8), toTest.getStart());
            assertEquals(LocalDate.of(2020, 12, 30), toTest.getEnd());
        } catch (PostDaoException ex) {
            fail("Should not hit PostDaoException in golden path");
        }

    }

    @Test
    public void testGetPostByBadId() {

        try {
            pDao.getPostById(-1);
            fail("Should have hit PostDaoException");
        } catch (PostDaoException ex) {

        }

    }

    /**
     * Test of getPostByTitle method, of class PostDBImpl.
     */
    @Test
    public void testGetPostByTitleGoldenPath() {
        try {
            Post toTest = pDao.getPostByTitle("Third Title");
            assertEquals(3, toTest.getId());
            assertEquals("Third Title", toTest.getTitle());
            assertEquals("Third Author", toTest.getAuthor());
            assertEquals("Third Content", toTest.getContent());
            assertEquals(1, toTest.getTags().size());
            assertTrue(toTest.isApproved());
            assertEquals(LocalDate.of(2020, 10, 11), toTest.getStart());
            assertEquals(LocalDate.of(2020, 10, 30), toTest.getEnd());
        } catch (PostDaoException ex) {
            fail("Should not hit PostDaoException in golden path");
        }
    }

    @Test
    public void testGetPostByBadTitle() {
        try {
            pDao.getPostByTitle("Wrong Title");
            fail("Should have hit PostDaoException");
        } catch (PostDaoException ex) {

        }
    }

    @Test
    public void testGetPostByNullTitle() {
        try {
            pDao.getPostByTitle(null);
            fail("Should have hit PostDaoException");
        } catch (PostDaoException ex) {

        }
    }

    /**
     * Test of getPostsByTag method, of class PostDBImpl.
     */
    @Test
    public void testGetPostsByTagIdGoldenPath() {
        try {
            List<Post> toTest = pDao.getPostsByTagId(2);
            Post first = toTest.get(0);
            assertEquals(1, toTest.size());
            assertEquals(3, first.getId());
            assertEquals("Third Title", first.getTitle());
            assertEquals("Third Author", first.getAuthor());
            assertEquals("Third Content", first.getContent());
            assertEquals(1, first.getTags().size());
            assertTrue(first.isApproved());
            assertEquals(LocalDate.of(2020, 10, 11), first.getStart());
            assertEquals(LocalDate.of(2020, 10, 30), first.getEnd());
        } catch (PostDaoException ex) {
            fail("Should not hit PostDaoException in golden path");
        }
    }

    @Test
    public void testGetPostsByBadTagId() {
        try {
            pDao.getPostsByTagId(-1);
            fail("Should have hit PostDaoException");
        } catch (PostDaoException ex) {

        }
    }

    /**
     * Test of getAllPosts method, of class PostDBImpl.
     */
    @Test
    public void testGetPostsByAuthorGoldenPath() throws PostDaoException {
        List<Post> toCheck = pDao.getPostsByAuthor("First Author");
        Post first = toCheck.get(0);
        Post last = toCheck.get(toCheck.size() - 1);
        assertEquals(1, toCheck.size());
        assertEquals(1, first.getId());
        assertEquals("First Title", first.getTitle());
        assertEquals("First Author", first.getAuthor());
        assertEquals("First Content", first.getContent());
        assertEquals(1, first.getTags().size());
        assertTrue(first.isApproved());
        assertEquals(LocalDate.of(2020, 12, 8), first.getStart());
        assertEquals(LocalDate.of(2020, 12, 30), first.getEnd());
        assertEquals(last, first);
    }

    @Test
    public void testGetPostsByBadAuthorName() {
        try {
            pDao.getPostsByAuthor("Fake Author");
            fail("Should have hit PostDaoException");
        } catch (PostDaoException ex) {

        }

    }

    @Test
    public void testGetPostsByNullAuthorName() {
        try {
            pDao.getPostsByAuthor(null);
            fail("Should have hit PostDaoException");
        } catch (PostDaoException ex) {

        }

    }

    @Test
    public void testGetAllPostsGoldenPath() throws PostDaoException {

        List<Post> allPosts = pDao.getAllPosts();
        Post first = allPosts.get(0);
        Post last = allPosts.get(allPosts.size() - 1);
        assertEquals(3, allPosts.size());
        assertEquals(1, first.getId());
        assertEquals("First Title", first.getTitle());
        assertEquals("First Author", first.getAuthor());
        assertEquals("First Content", first.getContent());
        assertEquals(1, first.getTags().size());
        assertTrue(first.isApproved());
        assertEquals(LocalDate.of(2020, 12, 8), first.getStart());
        assertEquals(LocalDate.of(2020, 12, 30), first.getEnd());
        assertEquals(3, last.getId());
        assertEquals("Third Title", last.getTitle());
        assertEquals("Third Author", last.getAuthor());
        assertEquals("Third Content", last.getContent());
        assertEquals(1, last.getTags().size());
        assertTrue(last.isApproved());
        assertEquals(LocalDate.of(2020, 10, 11), last.getStart());
        assertEquals(LocalDate.of(2020, 10, 30), last.getEnd());
    }

    @Test
    public void testGetAllPostsEmptyReturn() {
        try {
            template.update("DELETE FROM Post_Tags");
            template.update("DELETE FROM Posts");
            pDao.getAllPosts();
            fail("Should have hit PostDaoException");
        } catch (PostDaoException ex) {

        }
    }

    /**
     * Test of createPost method, of class PostDBImpl.
     */
    @Test
    public void testCreatePostGoldenPath() {
        try {
            Set<Tag> tagSet = pDao.getPostById(1).getTags();
            Post toAdd = new Post("Fourth Title", "Fourth Author", "Fourth Content", tagSet, false, testStart, testEnd);
            Post toTest = pDao.createPost(toAdd);
            assertEquals(4, toTest.getId());
            assertEquals(toAdd.getTitle(), toTest.getTitle());
            assertEquals(toAdd.getAuthor(), toTest.getAuthor());
            assertEquals(toAdd.getContent(), toTest.getContent());
            assertEquals(toAdd.getTags(), toTest.getTags());
            assertFalse(toTest.isApproved());
            assertEquals(toAdd.getStart(), toAdd.getStart());
            assertEquals(toAdd.getEnd(), toTest.getEnd());
        } catch (PostDaoException ex) {
            fail("Should not hit PostDaoException in golden path");
        } catch (InvalidEntityException ex) {
            fail("Should not hit InvalidEntityException in golden path");
        }
    }

    @Test
    public void testCreatePostNullObject() throws PostDaoException {
        try {
            Post toAdd = null;
            pDao.createPost(toAdd);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePostNullTitle() throws PostDaoException {
        try {
            Set<Tag> tagSet = pDao.getPostById(1).getTags();
            Post invalidPost = new Post(null, "Fourth Author", "Fourth Content", tagSet, false, testStart, testEnd);
            pDao.createPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePostNullAuthor() throws PostDaoException {
        try {
            Set<Tag> tagSet = pDao.getPostById(1).getTags();
            Post invalidPost = new Post("Fourth Title", null, "Fourth Content", tagSet, false, testStart, testEnd);
            pDao.createPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePostNullContent() throws PostDaoException {
        try {
            Set<Tag> tagSet = pDao.getPostById(1).getTags();
            Post invalidPost = new Post("Fourth Title", "Fourth Author", null, tagSet, false, testStart, testEnd);
            pDao.createPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePostNullTags() throws PostDaoException {
        try {
            Post invalidPost = new Post("Fourth Title", "Fourth Author", "Fourth Content", null, false, testStart, testEnd);
            pDao.createPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePostBadTitleLength() throws PostDaoException {
        try {
            Set<Tag> tagSet = pDao.getPostById(1).getTags();
            Post invalidPost = new Post(createBadTitle(), "Fourth Author", "Fourth Content", tagSet, false, testStart, testEnd);
            pDao.createPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePostBadAuthorLength() throws PostDaoException {
        try {
            Set<Tag> tagSet = pDao.getPostById(1).getTags();
            Post invalidPost = new Post("Fourth Title", createBadAuthor(), "Fourth Content", tagSet, false, testStart, testEnd);
            pDao.createPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testCreatePostBadContentLength() throws PostDaoException {
        try {
            Set<Tag> tagSet = pDao.getPostById(1).getTags();
            Post invalidPost = new Post("Fourth Title", "Fourth Author", createBadContent(), tagSet, false, testStart, testEnd);
            pDao.createPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    /**
     * Test of editPost method, of class PostDBImpl.
     */
    @Test
    public void testEditPostGoldenPath() {
        try {
            Post toEdit = pDao.getPostById(2);
            toEdit.setTitle("Second Title PE");
            toEdit.setAuthor("Second Author PE");
            toEdit.setContent("Second Content PE");
            toEdit.setApproved(true);
            toEdit.setStart(testStart);
            toEdit.setEnd(testEnd);
            pDao.editPost(toEdit);
            Post afterEdit = pDao.getPostById(2);
            assertEquals(toEdit.getId(), afterEdit.getId());
            assertEquals(toEdit.getTitle(), afterEdit.getTitle());
            assertEquals(toEdit.getAuthor(), afterEdit.getAuthor());
            assertEquals(toEdit.getContent(), afterEdit.getContent());
            assertEquals(toEdit.getTags(), afterEdit.getTags());
            assertTrue(afterEdit.isApproved());
            assertEquals(toEdit.getStart(), afterEdit.getStart());
            assertEquals(toEdit.getEnd(), afterEdit.getEnd());
        } catch (PostDaoException ex) {
            fail("Should not hit PostDaoException in golden path");
        } catch (BadUpdateException ex) {
            fail("Should not hit BadUpdateException in golden path");
        } catch (InvalidEntityException ex) {
            fail("Should not hit InvalidEntityException in golden path");
        }
    }

    @Test
    public void testEditPostNullObject() throws PostDaoException, BadUpdateException {
        try {
            Post toEdit = null;
            pDao.editPost(toEdit);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPostBadId() throws PostDaoException, InvalidEntityException {
        try {
            Post toEdit = pDao.getPostById(2);
            toEdit.setId(-1);
            pDao.editPost(toEdit);
            fail("Should have hit BadUpdateException");
        } catch (BadUpdateException ex) {

        }
    }

    @Test
    public void testEditPostNullTitle() throws PostDaoException, BadUpdateException {
        try {
            Set<Tag> tagSet = pDao.getPostById(2).getTags();
            Post invalidPost = new Post(2, null, "Second Author PE", "Second Content PE", tagSet, true, testStart, testEnd);
            pDao.editPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPostNullAuthor() throws PostDaoException, BadUpdateException {
        try {
            Set<Tag> tagSet = pDao.getPostById(2).getTags();
            Post invalidPost = new Post(2, "Second Title PE", null, "Second Content PE", tagSet, true, testStart, testEnd);
            pDao.editPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPostNullContent() throws PostDaoException, BadUpdateException {
        try {
            Set<Tag> tagSet = pDao.getPostById(2).getTags();
            Post invalidPost = new Post(2, "Second Title PE", "Second Author PE", null, tagSet, true, testStart, testEnd);
            pDao.editPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPostNullTags() throws PostDaoException, BadUpdateException {
        try {
            Post invalidPost = new Post(2, "Second Title PE", "Second Author PE", "Second Content PE", null, true, testStart, testEnd);
            pDao.editPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPostNullStartDate() throws PostDaoException, BadUpdateException {
        try {
            Set<Tag> tagSet = pDao.getPostById(2).getTags();
            Post invalidPost = new Post(2, "Second Title PE", "Second Author PE", null, tagSet, true, null, testEnd);
            pDao.editPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPostNullEndDate() throws PostDaoException, BadUpdateException {
        try {
            Set<Tag> tagSet = pDao.getPostById(2).getTags();
            Post invalidPost = new Post(2, "Second Title PE", "Second Author PE", null, tagSet, true, testStart, null);
            pDao.editPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPostBadTitleLength() throws PostDaoException, BadUpdateException {
        try {
            Set<Tag> tagSet = pDao.getPostById(2).getTags();
            Post invalidPost = new Post(2, createBadTitle(), "Second Author PE", "Second Content PE", tagSet, true, testStart, testEnd);
            pDao.editPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPostBadAuthorLength() throws PostDaoException, BadUpdateException {
        try {
            Set<Tag> tagSet = pDao.getPostById(2).getTags();
            Post invalidPost = new Post(2, "Second Title PE", createBadAuthor(), "Second Content PE", tagSet, true, testStart, testEnd);
            pDao.editPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testEditPostBadContentLength() throws PostDaoException, BadUpdateException {
        try {
            Set<Tag> tagSet = pDao.getPostById(2).getTags();
            Post invalidPost = new Post(2, "Second Title PE", "Second Author PE", createBadContent(), tagSet, true, testStart, testEnd);
            pDao.editPost(invalidPost);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    /**
     * Test of removePost method, of class PostDBImpl.
     */
    @Test
    public void testRemovePost() {
        try {
            pDao.removePost(3);
            List<Post> allPosts = pDao.getAllPosts();
            Post first = allPosts.get(0);
            Post last = allPosts.get(allPosts.size() - 1);
            assertEquals(2, allPosts.size());
            assertEquals(1, first.getId());
            assertEquals("First Title", first.getTitle());
            assertEquals("First Author", first.getAuthor());
            assertEquals("First Content", first.getContent());
            assertEquals(1, first.getTags().size());
            assertTrue(first.isApproved());
            assertEquals(LocalDate.of(2020, 12, 8), first.getStart());
            assertEquals(LocalDate.of(2020, 12, 30), first.getEnd());
            assertEquals(2, last.getId());
            assertEquals("Second Title", last.getTitle());
            assertEquals("Second Author", last.getAuthor());
            assertEquals("Second Content", last.getContent());
            assertEquals(1, last.getTags().size());
            assertFalse(last.isApproved());
            assertEquals(LocalDate.of(2020, 12, 13), last.getStart());
            assertEquals(LocalDate.of(2020, 12, 25), last.getEnd());
        } catch (PostDaoException ex) {
            fail("Should not hit PostDaoException in golden path");
        } catch (BadUpdateException ex) {
            fail("Should not hit BadUpdateException in golden path");
        }

    }

    @Test
    public void testRemovePostBadId() throws PostDaoException {
        try {
            pDao.removePost(-1);
            fail("Should have hit BadUpdateException");
        } catch (BadUpdateException ex) {

        }

    }
    
    @Test
    public void testApprovePost() throws PostDaoException  {
        Tag expTag = new Tag(2, "tag2");
        Set<Tag> tags = new HashSet<>();
        tags.add(expTag);
        Post original = new Post(2, "Second Title", "Second Author", "Second Content", tags, false, LocalDate.of(2020,12,13), LocalDate.of(2020,12,25));
        Post expected = new Post(2, "Second Title", "Second Author", "Second Content", tags, true, LocalDate.of(2020,12,13), LocalDate.of(2020,12,25));
        
        Post fromDao = pDao.getPostById(2);
        assertEquals(original, fromDao);
        
        pDao.approvePost(2);
        
        fromDao = pDao.getPostById(2);
        assertEquals(expected, fromDao);
        assertNotEquals(original, fromDao);
    }
    
    @Test
    public void testApprovePostInvalidId() throws PostDaoException  {
        try {
            pDao.approvePost(-2);
            fail("should hit PostDaoException");
        }
        catch(PostDaoException ex){}
    }

    private String createBadTitle() {
        char[] chars = new char[61];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }

    private String createBadAuthor() {
        char[] chars = new char[51];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }

    private String createBadContent() {
        char[] chars = new char[65536];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
}
