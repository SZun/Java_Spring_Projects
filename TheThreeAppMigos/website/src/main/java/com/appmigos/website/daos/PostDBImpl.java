/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.daos;

import com.appmigos.website.dtos.Post;
import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.PostDaoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("database")
public class PostDBImpl implements PostDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Post getPostById(int id) throws PostDaoException {
        try {
            Post toReturn = template.queryForObject("SELECT * FROM Posts WHERE id = ?", new PostMapper(), id);
            Set<Tag> tags = getTagsByPostId(toReturn.getId());
            toReturn.setTags(tags);
            return toReturn;
        } catch (DataAccessException ex) {
            throw new PostDaoException("Failed to reach database in getPostById");
        }
    }

    @Override // 
    public Post getPostByTitle(String title) throws PostDaoException {
        try {
            Post toReturn = template.queryForObject("SELECT * FROM Posts WHERE title = ?", new PostMapper(), title);
            Set<Tag> tags = getTagsByPostId(toReturn.getId());
            toReturn.setTags(tags);
            return toReturn;
        } catch (DataAccessException ex) {
            throw new PostDaoException("Failed to reach database in getPostByName");
        }
    }

    @Override // TODO change Tag toFind to int id
    public List<Post> getPostsByTagId(int id) throws PostDaoException {
        List<Post> postsForTag = template.query("SELECT * FROM Posts ps INNER JOIN Post_Tags pt ON ps.id = pt.postId WHERE pt.tagId = ? AND ps.approved = ?",
                new PostMapper(), id, true);
        if(postsForTag.isEmpty()) throw new PostDaoException("No Posts found for given tag ID");
        associateTagsToPost(postsForTag);
        return postsForTag;
    }
    
    @Override
    public List<Post> getPostsByAuthor(String name) throws PostDaoException{
        List<Post> postsForAuthor = template.query("SELECT * FROM Posts WHERE author = ?",
                new PostMapper(), name);
        if(postsForAuthor.isEmpty()) throw new PostDaoException("No posts found for given author");
        associateTagsToPost(postsForAuthor);
        return postsForAuthor;
    }

    @Override
    public List<Post> getAllPosts() throws PostDaoException {
        try {
            List<Post> allPosts = template.query("SELECT * FROM Posts", new PostMapper());
            if (allPosts.isEmpty()) {
                throw new PostDaoException("No posts found");
            }
            associateTagsToPost(allPosts);
            return allPosts;
        } catch (DataAccessException ex) {
            throw new PostDaoException("Failed to reach database in getAllPosts");
        }
    }

    @Override
    @Transactional
    public Post createPost(Post toAdd) throws PostDaoException, InvalidEntityException {
        validatePostData(toAdd);
        try {
            template.update("INSERT INTO Posts(title, author, content, start, end) VALUES(?, ?, ?, ?, ?)",
                    toAdd.getTitle(), toAdd.getAuthor(), toAdd.getContent(), toAdd.getStart(), toAdd.getEnd());
            int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            toAdd.setId(newId);
            for(Tag t: toAdd.getTags()){
                template.update("INSERT INTO Post_Tags(postId, tagId) VALUES (?, ?)", toAdd.getId(), t.getId());
            }
            return toAdd;
        } catch (DataAccessException ex) {
            throw new PostDaoException("Failed to reach database in createPost");
        }
    }

    @Override
    public void editPost(Post toEdit) throws PostDaoException, BadUpdateException, InvalidEntityException {
        validatePostData(toEdit);
        try {
            int affectedRows = template.update("UPDATE Posts SET title = ?, author = ?, content = ?, approved = ?, start = ?, end = ? WHERE id = ?",
                    toEdit.getTitle(), toEdit.getAuthor(), toEdit.getContent(), toEdit.isApproved(), toEdit.getStart(), toEdit.getEnd(), toEdit.getId());
            if (affectedRows < 1) {
                throw new BadUpdateException("No rows affected in editPost");
            }
            if (affectedRows > 1) {
                throw new BadUpdateException("More than one row affected in editPost");
            }
            template.update("DELETE FROM Post_Tags WHERE postId = ?", toEdit.getId());
            for(Tag t: toEdit.getTags()){
                template.update("INSERT INTO Post_Tags(postId, tagId) VALUES(?, ?)", toEdit.getId(), t.getId());
            }
        } catch (DataAccessException ex) {
            throw new PostDaoException("Failed to reach database in editPost");
        }
    }

    @Override
    public void removePost(int id) throws PostDaoException, BadUpdateException {
        try {
            template.update("DELETE FROM Post_Tags WHERE postId = ?", id);
            int affectedRows = template.update("DELETE FROM Posts WHERE id = ?", id);
            if (affectedRows < 1) {
                throw new BadUpdateException("No rows affected in removePost");
            }
            if (affectedRows > 1) {
                throw new BadUpdateException("More than one row affected in removePost");
            }
        } catch (DataAccessException ex) {
            throw new PostDaoException("Failed to reach database in removePost");
        }
    }

    private Set<Tag> getTagsByPostId(int id) {
        return new HashSet<>(template.query("SELECT * FROM Tags tg INNER JOIN Post_Tags pt ON tg.id = pt.tagId WHERE pt.postId = ?", new TagMapper(), id));
    }
    
    private void associateTagsToPost(List<Post> posts){
        for(Post p: posts){
            p.setTags(getTagsByPostId(p.getId()));
        }
    }
    
    private void validatePostData(Post toCheck) throws InvalidEntityException{
        if(toCheck == null) throw new InvalidEntityException("Post Object cannot be null");
        if(toCheck.getTitle() == null || toCheck.getAuthor() == null || toCheck.getContent() == null 
                || toCheck.getTags() == null || toCheck.getStart() == null || toCheck.getEnd() == null){
            throw new InvalidEntityException("Post Object properties cannot be null");
        }
        if(toCheck.getTitle().length() > 60) throw new InvalidEntityException("Post Title must be 60 characters or less");
        if(toCheck.getAuthor().length() > 50) throw new InvalidEntityException("Post Author must be 50 cahracters or less");
        if(toCheck.getContent().length() > 65535) throw new InvalidEntityException("Post Content must be 65535 characters or less");
    }

    @Override
    public void approvePost(int id) throws PostDaoException {
        final String UPDATE_POST_APPROVED = "UPDATE Posts SET approved = ? WHERE Id = ?";
        int update = template.update(UPDATE_POST_APPROVED, true, id);
        if(update == 0){
            throw new PostDaoException("Invalid id");
        }
    }

    private static class PostMapper implements RowMapper<Post> {

        @Override
        public Post mapRow(ResultSet rs, int i) throws SQLException {
            Post toReturn = new Post();
            toReturn.setId(rs.getInt("id"));
            toReturn.setTitle(rs.getString("title"));
            toReturn.setAuthor(rs.getString("author"));
            toReturn.setContent(rs.getString("content"));
            toReturn.setApproved(rs.getBoolean("approved"));
            toReturn.setStart(rs.getDate("start").toLocalDate());
            toReturn.setEnd(rs.getDate("end").toLocalDate());
            return toReturn;
        }

    }

    private static class TagMapper implements RowMapper<Tag> {

        @Override
        public Tag mapRow(ResultSet rs, int i) throws SQLException {
            Tag toReturn = new Tag();
            toReturn.setId(rs.getInt("id"));
            toReturn.setTag(rs.getString("tag"));
            return toReturn;
        }

    }

}