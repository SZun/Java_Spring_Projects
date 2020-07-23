/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.memdaos;

import com.appmigos.website.daos.PostDao;
import com.appmigos.website.dtos.Post;
import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.PostDaoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("memory")
public class PostDaoInMem implements PostDao {

    List<Post> allPosts = new ArrayList<>();
    Set<Tag> tagSet1 = new HashSet<>();
    Set<Tag> tagSet2 = new HashSet<>();
    Tag tag1 = new Tag(1, "Meat");
    Tag tag2 = new Tag(2, "BBQ");
    Tag tag3 = new Tag(3, "Veggies");

    @Override
    public Post getPostById(int id) throws PostDaoException {
        List<Post> toCheck = allPosts.stream().filter(p -> p.getId() == id).collect(Collectors.toList());
        if (toCheck.isEmpty()) {
            throw new PostDaoException("no posts found for that ID");
        }
        return toCheck.get(0);
    }

    @Override
    public Post getPostByTitle(String title) throws PostDaoException {
        List<Post> toCheck = allPosts.stream().filter(p -> p.getTitle() == title).collect(Collectors.toList());
        if (toCheck.isEmpty()) {
            throw new PostDaoException("no posts found for that Title");
        }
        return toCheck.get(0);
    }

    @Override
    public List<Post> getPostsByTagId(int id) throws PostDaoException {

        Tag currentTag = null;
        List<Post> toReturn = new ArrayList<>();

        switch (id) {
            case 1:
                currentTag = tag1;
                break;
            case 2:
                currentTag = tag2;
                break;
            case 3:
                currentTag = tag3;
                break;
            default:
                throw new PostDaoException("No tag available");
        }

        for (Post post : allPosts) {
            if (post.getTags().contains(currentTag)) {
                toReturn.add(post);
            }
        }

        return toReturn;
    }

    @Override
    public List<Post> getPostsByAuthor(String name) throws PostDaoException {
        List<Post> toCheck = allPosts.stream().filter(p -> p.getAuthor() == name).collect(Collectors.toList());
        if (toCheck.isEmpty()) {
            throw new PostDaoException("No posts found for that author");
        }
        return toCheck;
    }

    @Override
    public List<Post> getAllPosts() throws PostDaoException {
        return allPosts;
    }

    @Override
    public Post createPost(Post toAdd) throws PostDaoException {

        int nextId = allPosts.stream().mapToInt(p -> p.getId()).max().orElse(0) + 1;

        toAdd.setId(nextId);
        toAdd.setApproved(false);
        allPosts.add(toAdd);
        return toAdd;
    }

    @Override
    public void editPost(Post toEdit) throws PostDaoException, BadUpdateException {

        boolean isValid = false;
        for (Post p : allPosts) {
            if (p.getId() == toEdit.getId()) {
                p.setTitle(toEdit.getTitle());
                p.setAuthor(toEdit.getAuthor());
                p.setContent(toEdit.getContent());
                p.setTags(toEdit.getTags());
                p.setApproved(toEdit.isApproved());
                p.setStart(toEdit.getStart());
                p.setEnd(toEdit.getEnd());
                isValid = true;
            }
        }
        if (!isValid) {
            throw new BadUpdateException("Invalid Id");
        }
    }

    @Override
    public void removePost(int id) throws PostDaoException, BadUpdateException {

        int status = 0;
        Post toRemove = null;

        for (Post p : allPosts) {
            if (p.getId() == id) {
                toRemove = p;
                status = 1;
                break;
            }
        }

        if (status == 1) {
            allPosts.remove(toRemove);
        } else {
            throw new BadUpdateException("Invalid id");
        }

    }

    public void setUp() {

        tagSet1.clear();
        tagSet2.clear();
        tagSet1.add(tag1);
        tagSet1.add(tag2);
        tagSet2.add(tag3);

        allPosts.clear();
        Post post1 = new Post(1, "First Post", "First Author", "First Content", tagSet1, true, LocalDate.of(2020, 06, 3), LocalDate.of(2021, 06, 6)); //missing Set<Role>, Boolean approved
        Post post2 = new Post(2, "Second Post", "Second Author", "Second Content", tagSet2, false, LocalDate.of(2020, 03, 9), LocalDate.of(2021, 03, 23));
        Post post3 = new Post(3, "Third Post", "Third Author", "Third Content", tagSet1, true, LocalDate.of(2020, 06, 30), LocalDate.of(2021, 07, 11));

        allPosts.add(post1);
        allPosts.add(post2);
        allPosts.add(post3);

    }

    public void clearPosts() {
        allPosts.clear();
    }

    @Override
    public void approvePost(int id) throws PostDaoException {
        boolean isValid = false;

        for (Post p : allPosts) {
            if (p.getId() == id) {
                p.setApproved(true);
                try {
                    editPost(p);
                } catch (BadUpdateException ex) {
                }
                isValid = true;
                break;

            }
        }

        if (!isValid) {
            throw new PostDaoException("Invalid id");
        }
    }

}
