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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("database")
public class TagDBImpl implements TagDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Tag getTagById(int id) throws TagDaoException {
        try {
            return template.queryForObject("SELECT * FROM Tags WHERE id = ?", new TagMapper(), id);
        } catch (DataAccessException ex) {
            throw new TagDaoException("Failed to reach database in getTagById");
        }
    }

    @Override
    public List<Tag> getAllTags() throws TagDaoException {
        try {
            List<Tag> allTags = template.query("SELECT * FROM Tags", new TagMapper());
            if (allTags.isEmpty()) {
                throw new TagDaoException("No tags found");
            }
            return allTags;
        } catch (DataAccessException ex) {
            throw new TagDaoException("Failed to reach database in getAllTags");
        }
    }

    @Override
    public Tag createTag(Tag toAdd) throws TagDaoException, InvalidEntityException {
        validateTagData(toAdd);
        try {
            template.update("INSERT INTO Tags(tag) VALUES(?)", toAdd.getTag());
            int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            toAdd.setId(newId);
            return toAdd;
        } catch (DataAccessException ex) {
            throw new TagDaoException("Failed to reach database in createTag");
        }
    }

    @Override
    public void editTag(Tag toEdit) throws TagDaoException, BadUpdateException, InvalidEntityException {
        validateTagData(toEdit);
        try {
            int affectedRows = template.update("UPDATE Tags SET tag = ? WHERE id = ?",
                    toEdit.getTag(), toEdit.getId());
            if (affectedRows < 1) {
                throw new BadUpdateException("No rows affected in editTag");
            }
            if (affectedRows > 1) {
                throw new BadUpdateException("More than one row affected in editTag");
            }
        } catch (DataAccessException ex) {
            throw new TagDaoException("Failed to reach database in editTag");
        }
    }

    @Override
    public void removeTag(int id) throws TagDaoException, BadUpdateException {
        try{
        template.update("DELETE FROM Post_Tags WHERE tagId = ?", id);
        int affectedRows = template.update("DELETE FROM Tags WHERE id = ?", id);
        if (affectedRows < 1) {
                throw new BadUpdateException("No rows affected in editTag");
            }
            if (affectedRows > 1) {
                throw new BadUpdateException("More than one row affected in editTag");
            }
        } catch(DataAccessException ex){
                throw new TagDaoException("Failed to reach database in removeTag");
        }    
    }
    
    private void validateTagData(Tag toCheck) throws InvalidEntityException{
        if(toCheck == null) throw new InvalidEntityException("Tag Object cannot be null");
        if(toCheck.getTag() == null) throw new InvalidEntityException("Tag Object properties cannot be null");
        if(toCheck.getTag().length() > 60) throw new InvalidEntityException("Tag name must be 60 characters or less");
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
