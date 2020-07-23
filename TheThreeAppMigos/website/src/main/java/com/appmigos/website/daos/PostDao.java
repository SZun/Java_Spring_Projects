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
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Isaia
 */
public interface PostDao {
 
    public Post getPostById(int id) throws PostDaoException;
    
    public Post getPostByTitle(String title) throws PostDaoException;
    
    public List<Post> getPostsByTagId(int id) throws PostDaoException;
    
    public List<Post> getPostsByAuthor(String name) throws PostDaoException;
    
    public List<Post> getAllPosts() throws PostDaoException;
    
    public Post createPost(Post toAdd) throws PostDaoException, InvalidEntityException;
    
    public void editPost(Post toEdit) throws PostDaoException, BadUpdateException, InvalidEntityException;
    
    public void removePost(int id) throws PostDaoException, BadUpdateException;

    public void approvePost(int id) throws PostDaoException;
}
