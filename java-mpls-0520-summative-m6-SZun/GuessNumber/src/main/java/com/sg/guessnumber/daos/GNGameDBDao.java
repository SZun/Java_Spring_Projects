/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.daos;

import com.sg.guessnumber.models.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author samg.zun
 */
@Repository
@Profile("prod")
public class GNGameDBDao implements GNGameDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public GNGameDBDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Game getGameById(int id) throws GNGameDaoException {
        validateId(id);
        
        final String SELECT_GAME_BY_ID = "SELECT * FROM Games WHERE GameId = ?";
        
        try {
            return jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), id);
        } catch(EmptyResultDataAccessException ex){
            throw new GNGameDaoException("Game not found");
        }
        
    }

    @Override
    public List<Game> getAll() {
        final String SELECT_ALL_GAMES = "SELECT * FROM Games";
        return jdbc.query(SELECT_ALL_GAMES, new GameMapper());
    }

    @Override
    public boolean updateGameFinished(int gameId) throws GNGameDaoException {
        validateId(gameId);
        final String UPDATE_GAME_BY_ID = "UPDATE Games SET finished = ? WHERE GameId = ?";
        return jdbc.update(UPDATE_GAME_BY_ID, true, gameId) > 0;
    }

    @Override
    public int createGame(String generatedAnswer) {
        final String INSERT_NEW_GAME = "INSERT INTO Games(Answer) VALUES(?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {

            PreparedStatement pStmt = conn.prepareStatement(INSERT_NEW_GAME, Statement.RETURN_GENERATED_KEYS);
            pStmt.setString(1, generatedAnswer);
            
            return pStmt;

        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {
            return new Game(rs.getInt("GameId"), rs.getString("Answer"), rs.getBoolean("Finished"));
        }
    }
    
    private void validateId(int id) throws GNGameDaoException {
        if(id == 0){
            throw new GNGameDaoException("Invalid ID");
        }
    }

}
