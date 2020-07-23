/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.daos.PostDao;
import com.appmigos.website.dtos.Post;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.InvalidNameException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.exceptions.PostDaoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Schmi
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostDao postDao;

    @Override
    public Post getPostById(int id) throws InvalidIdException {

        try {
            return postDao.getPostById(id);
        } catch (PostDaoException ex) {
            throw new InvalidIdException("Error, cannnot get post by id");
        }
    }

    @Override
    public List<Post> getPostsByTag(int id) throws InvalidIdException, NoItemsException {

        List<Post> allPosts;
        try {
            allPosts = postDao.getPostsByTagId(id).stream().filter(p -> !p.getStart().isAfter(LocalDate.now())
                    && p.getEnd().isAfter(LocalDate.now())
            ).collect(Collectors.toList());
        } catch (PostDaoException ex) {
            throw new InvalidIdException("Invalid id");
        }

        if (allPosts.isEmpty()) {
            throw new NoItemsException("No Items");
        }

        return allPosts;
    }

    @Override
    public List<Post> getAllPosts(boolean isApproved) throws NoItemsException {

        List<Post> allPosts;
        List<Post> filteredPosts = new ArrayList<>();
        try {
            allPosts = postDao.getAllPosts();
            filteredPosts = allPosts.stream()
                    .filter(p -> {
                        boolean isApprovedBool = p.isApproved() == isApproved;
                        boolean isInRange = !p.getStart().isAfter(LocalDate.now()) && p.getEnd().isAfter(LocalDate.now());
                        return isApprovedBool && isInRange;
                    }).collect(Collectors.toList());
        } catch (PostDaoException ex) {
            throw new NoItemsException("No posts to show");
        }

        if (filteredPosts.isEmpty()) {
            throw new NoItemsException("No items available");
        }

        return filteredPosts;
    }
    
    @Override
    public List<Post> getAll(boolean isApproved) throws NoItemsException {

        List<Post> allPosts;
        try {
            allPosts = postDao.getAllPosts();
        } catch (PostDaoException ex) {
            throw new NoItemsException("No posts to show");
        }

        List<Post> filteredPosts = allPosts.stream().filter(p -> p.isApproved() == isApproved).collect(Collectors.toList());
        
        if (filteredPosts.isEmpty()) {
            throw new NoItemsException("No items available");
        }

        return filteredPosts;
    }

    @Override
    public Post createPost(Post toCreate) throws InvalidEntityException {

        validate(toCreate);

        try {
            return postDao.createPost(toCreate);
        } catch (PostDaoException ex) {
            throw new InvalidEntityException("Invalid entity");
        }

    }

    @Override
    public void editPost(Post toEdit) throws InvalidIdException, InvalidEntityException {

        validate(toEdit);

        try {
            postDao.editPost(toEdit);
        } catch (PostDaoException | BadUpdateException ex) {
            throw new InvalidIdException(" Invalid id");
        }

    }

    @Override
    public void deletePost(int id) throws InvalidIdException {

        try {
            postDao.removePost(id);
        } catch (PostDaoException | BadUpdateException ex) {
            throw new InvalidIdException("Error, invalid id");
        }

    }

    @Override
    public List<Post> getPostsByName(String name) throws InvalidNameException, NoItemsException {
        if (name == null || name.trim().length() == 0) {
            throw new InvalidNameException("Name is invalid");
        }

        try {
            return postDao.getPostsByAuthor(name);
        } catch (PostDaoException ex) {
            throw new NoItemsException("No Items");
        }
    }
    
    @Override
    public void approvePost(int id) throws InvalidIdException {
        try {
            postDao.approvePost(id);
        } catch(PostDaoException ex){
            throw new InvalidIdException("Invalid Id");
        }
    }

    private void validate(Post toCheck) throws InvalidEntityException {

        if (toCheck.getTitle() == null
                || toCheck.getTitle().trim().length() > 60
                || toCheck.getTitle().trim().length() == 0
                || toCheck.getAuthor() == null
                || toCheck.getAuthor().trim().length() > 50
                || toCheck.getAuthor().trim().length() == 0
                || toCheck.getContent() == null
                || toCheck.getContent().trim().length() > 65535
                || toCheck.getContent().trim().length() == 0
                || toCheck.getTags() == null
                || toCheck.getTags().size() == 0
                || toCheck.getStart() == null
                || toCheck.getEnd() == null
                || toCheck.getEnd().isBefore(LocalDate.now())
                || toCheck.getEnd().isBefore(toCheck.getStart())) {
            throw new InvalidEntityException("Error, invalid post entity");
        }
    }

}
