/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.daos;

import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.TagDaoException;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Isaia
 */
public interface TagDao {
    
    public Tag getTagById(int id) throws TagDaoException;
    
    public List<Tag> getAllTags() throws TagDaoException;
    
    public Tag createTag(Tag toAdd) throws TagDaoException, InvalidEntityException;
    
    public void editTag(Tag toEdit) throws TagDaoException, BadUpdateException, InvalidEntityException;
    
    public void removeTag(int id) throws TagDaoException, BadUpdateException;
}
