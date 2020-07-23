/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.memdaos;

import com.appmigos.website.daos.TagDao;
import com.appmigos.website.dtos.Tag;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.TagDaoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("memory")
public class TagDaoInMem implements TagDao {

    List<Tag> allTags = new ArrayList<>();

    @Override
    public Tag getTagById(int id) throws TagDaoException {
        List<Tag> toCheck = allTags.stream().filter(t -> t.getId() == id).collect(Collectors.toList());
        if (toCheck.isEmpty()) {
            throw new TagDaoException("No tag found for that ID");
        }
        return toCheck.get(0);
    }

    @Override
    public List<Tag> getAllTags() throws TagDaoException {
        if (allTags.isEmpty()) {
            throw new TagDaoException("no items");
        } else {
            return allTags;
        }
    }

    @Override
    public Tag createTag(Tag toAdd) throws TagDaoException {
        toAdd.setId(allTags.size() + 1);
        allTags.add(toAdd);
        return toAdd;
    }

    @Override
    public void editTag(Tag toEdit) throws TagDaoException, BadUpdateException {
        int updated = 0;

        for (Tag t : allTags) {
            if (t.getId() == toEdit.getId()) {
                t.setTag(toEdit.getTag());
                updated = 1;
                break;
            }
        }

        if (updated == 0) {
            throw new TagDaoException("bad update");
        }
    }

    @Override
    public void removeTag(int id) throws TagDaoException, BadUpdateException {
        int deleted = 0;
        Tag toRemove = null;

        for (Tag t : allTags) {
            if (t.getId() == id) {
                toRemove = t;
                deleted = 1;
                break;
            }
        }

        if (deleted == 1) {
            allTags.remove(toRemove);
        } else {
            throw new BadUpdateException("Invalid id");
        }
    }

    public void setUp() {

        allTags.clear();

        Tag tag1 = new Tag(1, "food");
        Tag tag2 = new Tag(2, "grill");
        Tag tag3 = new Tag(3, "summer");

        allTags.add(tag1);
        allTags.add(tag2);
        allTags.add(tag3);
    }

    public void removeAll() {
        allTags.clear();
    }

}
