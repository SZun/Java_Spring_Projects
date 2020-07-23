/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.daos;

import com.appmigos.website.dtos.Page;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.PageDaoException;
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
public class PageDBImpl implements PageDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Page getPageById(int id) throws PageDaoException {
        try {
            return template.queryForObject("SELECT * FROM Pages WHERE id = ?", new PageMapper(), id);
        } catch (DataAccessException ex) {
            throw new PageDaoException("Failed to reach database in getPageById");
        }
    }

    @Override
    public Page getPageByTitle(String title) throws PageDaoException {
        try {
            return template.queryForObject("SELECT * FROM Pages WHERE title = ?", new PageMapper(), title);
        } catch (DataAccessException ex) {
            throw new PageDaoException("Failed to reach database in getPageByTitle");
        }
    }

    @Override
    public List<Page> getAllPages() throws PageDaoException {
        try {
            List<Page> allPages = template.query("SELECT * FROM Pages", new PageMapper());
            if (allPages.isEmpty()) {
                throw new PageDaoException("No pages found");
            }
            return allPages;
        } catch (DataAccessException ex) {
            throw new PageDaoException("Failed to reach database in getAllPages");
        }
    }

    @Override
    public Page createPage(Page toAdd) throws PageDaoException, InvalidEntityException {
        validatePageData(toAdd);
        try {
            template.update("INSERT INTO Pages(title, content) VALUES(?, ?)",
                    toAdd.getTitle(), toAdd.getContent());
            int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            toAdd.setId(newId);
            return toAdd;
        } catch (DataAccessException ex) {
            throw new PageDaoException("Failed to reach database in createPage");
        }
    }

    @Override
    public void editPage(Page toEdit) throws PageDaoException, BadUpdateException, InvalidEntityException {
        validatePageData(toEdit);
        try {
            int affectedRows = template.update("UPDATE Pages SET title = ?, content = ? WHERE id = ?",
                    toEdit.getTitle(), toEdit.getContent(), toEdit.getId());
            if (affectedRows < 1) {
                throw new BadUpdateException("No rows affected in editPage");
            }
            if (affectedRows > 1) {
                throw new BadUpdateException("More than one row affecte in editPage");
            }
        } catch (DataAccessException ex) {
            throw new PageDaoException("Failed to reach database in editPage");
        }
    }

    @Override
    public void removePage(int id) throws PageDaoException, BadUpdateException {
        try {
            int affectedRows = template.update("DELETE FROM Pages WHERE id = ?", id);
            if (affectedRows < 1) {
                throw new BadUpdateException("No rows affected in removePage");
            }
            if (affectedRows > 1) {
                throw new BadUpdateException("More than one row affecte in removePage");
            }
        } catch (DataAccessException ex) {
            throw new PageDaoException("Failed to reach database in removePage");
        }
    }
    
    private void validatePageData(Page toCheck) throws InvalidEntityException{
        if(toCheck == null) throw new InvalidEntityException("Page object cannot be null");
        if(toCheck.getTitle() == null || toCheck.getContent() == null) throw new InvalidEntityException("No fields can be null on Page object");
        if(toCheck.getTitle().length() > 60) throw new InvalidEntityException("Page Title cannot have more than 60 characters");
        if(toCheck.getContent().length() > 65535) throw new InvalidEntityException("Page Content cannot have more than 65535 characters");
    }

    private static class PageMapper implements RowMapper<Page> {

        @Override
        public Page mapRow(ResultSet rs, int i) throws SQLException {
            Page toReturn = new Page();
            toReturn.setId(rs.getInt("id"));
            toReturn.setTitle(rs.getString("title"));
            toReturn.setContent(rs.getString("content"));
            return toReturn;
        }

    }

}
