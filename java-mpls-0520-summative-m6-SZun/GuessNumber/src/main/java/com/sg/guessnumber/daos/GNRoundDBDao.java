/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessnumber.daos;

import com.sg.guessnumber.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
public class GNRoundDBDao implements GNRoundDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public GNRoundDBDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Round createRound(Round toAdd) throws GNRoundDaoException {
        validateRound(toAdd);
        final String INSERT_NEW_ROUND = "INSERT INTO Rounds(GameId, Guess, Time, Result) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {

            PreparedStatement pStmt = conn.prepareStatement(INSERT_NEW_ROUND, Statement.RETURN_GENERATED_KEYS);
            pStmt.setInt(1, toAdd.getGameId());
            pStmt.setString(2, toAdd.getGuess());
            pStmt.setTimestamp(3, Timestamp.valueOf(toAdd.getTime()));
            pStmt.setString(4, toAdd.getResult()); //TODO convert to char
            
            return pStmt;

        }, keyHolder);

        toAdd.setRoundId(keyHolder.getKey().intValue());

        return toAdd;
    }

    @Override
    public List<Round> getRoundsByGameId(int gameId) throws GNRoundDaoException {
        validateId(gameId);
        final String SELECT_ROUND_BY_ID = "SELECT * FROM Rounds WHERE GameId = ?";
        return jdbc.query(SELECT_ROUND_BY_ID, new RoundMapper(), gameId);
    }
    
    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            return new Round(rs.getInt("RoundId"), rs.getInt("GameId"), rs.getString("Guess"), 
                    rs.getTimestamp("Time").toLocalDateTime(), rs.getString("result"));
        }
    }
    
    private void validateId(int id) throws GNRoundDaoException{
        if(id == 0){
            throw new GNRoundDaoException("The ID entered is invalid");
        }
    }
    
    private void validateRound(Round toValidate) throws GNRoundDaoException {
        if (toValidate.getGuess() == null || toValidate.getGuess().trim().length() != 4 || 
            toValidate.getResult() == null || toValidate.getResult().trim().length() != 7) {
            throw new GNRoundDaoException("The Round is invalid");
        }
    }

}
