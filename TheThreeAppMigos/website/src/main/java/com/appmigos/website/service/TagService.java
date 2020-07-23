/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 *
 * @author Schmi
 */
public interface TagService {
    
    List<Tag> getAllTags() throws NoItemsException;
    Tag getTagById(int id) throws InvalidIdException;
    Tag createTag(Tag toCreate) throws InvalidEntityException;
    void editTag(Tag toEdit) throws InvalidIdException, InvalidEntityException;
    void deleteTag(int id) throws InvalidIdException;
    
}
