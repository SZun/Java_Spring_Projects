/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.daos.TagDao;
import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.exceptions.TagDaoException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Schmi
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao tagDao;

    @Override
    public List<Tag> getAllTags() throws NoItemsException {

        List<Tag> allTags;

        try {
            allTags = tagDao.getAllTags();
        } catch (TagDaoException ex) {
            throw new NoItemsException("No tags to show");
        }

        return allTags;
    }

    @Override
    public Tag getTagById(int id) throws InvalidIdException {

        try {
            return tagDao.getTagById(id);
        } catch (TagDaoException ex) {
            throw new InvalidIdException("Error, invalid id");
        }
    }

    @Override
    public Tag createTag(Tag toCreate) throws InvalidEntityException {

        validate(toCreate);

        try {
            return tagDao.createTag(toCreate);
        } catch (TagDaoException ex) {
            throw new InvalidEntityException("Invalid entity");
        }
    }

    @Override
    public void editTag(Tag toEdit) throws InvalidIdException, InvalidEntityException {

        validate(toEdit);

        try {
            tagDao.editTag(toEdit);
        } catch (TagDaoException | BadUpdateException ex) {
            throw new InvalidIdException("Invalid Id");
        }
        
    }

    @Override
    public void deleteTag(int id) throws InvalidIdException {

        
        try {
            tagDao.removeTag(id);
        } catch (TagDaoException | BadUpdateException ex) {
            throw new InvalidIdException("Invalid id exception");
        } 

    }

    private void validate(Tag toCheck) throws InvalidEntityException {

        if (toCheck.getTag() == null
                || toCheck.getTag().trim().length() > 60
                || toCheck.getTag().trim().length() == 0) {

            throw new InvalidEntityException("Error, invalid tag entity");
        }

    }

}
