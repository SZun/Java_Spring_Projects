/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.dtos.Post;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.InvalidNameException;
import com.appmigos.website.exceptions.NoItemsException;
import java.util.List;

/**
 *
 * @author Schmi
 */
public interface PostService {

    Post getPostById(int id) throws InvalidIdException;

    List<Post> getPostsByTag(int id) throws InvalidIdException, NoItemsException;

    List<Post> getPostsByName(String name) throws InvalidNameException, NoItemsException;

    List<Post> getAllPosts(boolean isApproved) throws NoItemsException;

    public List<Post> getAll(boolean isApproved) throws NoItemsException;

    Post createPost(Post toCreate) throws InvalidEntityException;

    void editPost(Post toEdit) throws InvalidIdException, InvalidEntityException;

    void deletePost(int id) throws InvalidIdException;

    public void approvePost(int id) throws InvalidIdException;

}
