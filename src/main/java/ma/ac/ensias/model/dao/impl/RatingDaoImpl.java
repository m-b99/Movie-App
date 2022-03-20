package ma.ac.ensias.model.dao.impl;

import ma.ac.ensias.exception.DaoException;
import ma.ac.ensias.model.dao.RatingDao;
import ma.ac.ensias.model.dao.query.RatingQuery;
import ma.ac.ensias.util.DaoUtil;
import ma.ac.ensias.model.entity.Rating;
import ma.ac.ensias.model.pool.DynamicConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RatingDaoImpl implements RatingDao {
    @Override
    public boolean isExists(Rating rating) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        Connection connection = DynamicConnectionPool.getInstance().provideConnection();
        ResultSet resultSet = null;
        String query = "";
        try{
            query = RatingQuery.SELECT_RATING_BY_USER_ID_MOVIE_ID;
            statement = connection.prepareStatement(query);
            statement.setInt(1, rating.getUserId());
            statement.setInt(2, rating.getMovieId());
            resultSet = statement.executeQuery();
            result = resultSet.next();
        }catch (SQLException  e) {
            throw new DaoException("Error executing query " + query, e);
        } finally {
            DaoUtil.releaseResources(connection, statement, resultSet);
        }
        return result;
    }

    @Override
    public boolean update(Rating rating) throws DaoException {
        PreparedStatement statement = null;
        Connection connection = DynamicConnectionPool.getInstance().provideConnection();
        String query = "";
        try{
            query = RatingQuery.UPDATE_RATING;
            statement = connection.prepareStatement(query);
            statement.setInt(1, rating.getValue());
            statement.setInt(2, rating.getMovieId());
            statement.setInt(3, rating.getUserId());
            statement.executeUpdate();
        }catch (SQLException  e) {
            throw new DaoException("Error executing query " + query, e);
        } finally {
            DaoUtil.releaseResources(connection, statement);
        }
        return true;
    }

    @Override
    public boolean save(Rating rating) throws DaoException {
        PreparedStatement statement = null;
        Connection connection = DynamicConnectionPool.getInstance().provideConnection();
        String query = "";
        try{
            query = RatingQuery.INSERT_RATING;
            statement = connection.prepareStatement(query);
            statement.setInt(1, rating.getUserId());
            statement.setInt(2, rating.getMovieId());
            statement.setInt(3, rating.getValue());
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new DaoException("Error executing query " + query, e);
        } finally {
            DaoUtil.releaseResources(connection, statement);
        }
        return true;
    }

    @Override
    public double calcAverageRating(int movieId) throws DaoException {
        double result;
        PreparedStatement statement = null;
        Connection connection = DynamicConnectionPool.getInstance().provideConnection();
        ResultSet resultSet = null;
        String query = "";
        try{
            query = RatingQuery.SELECT_AVG_RATING_BY_MOVIE_ID;
            statement = connection.prepareStatement(query);
            statement.setInt(1, movieId);
            resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getDouble(1);
        }catch (SQLException  e) {
            throw new DaoException("Error executing query " + query, e);
        } finally {
            DaoUtil.releaseResources(connection, statement, resultSet);
        }
        return result;
    }

    @Override
    public Map<Integer, Double> provideAllRatings() throws DaoException {
        Map<Integer, Double> result = new HashMap<>();
        PreparedStatement statement = null;
        Connection connection = DynamicConnectionPool.getInstance().provideConnection();
        ResultSet resultSet = null;
        String query = "";
        try{
            query = RatingQuery.SELECT_ALL_RATINGS_GROUP_BY_MOVIE;
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                int key = resultSet.getInt(1);
                double value = resultSet.getDouble(2);
                result.put(key, value);
            }
        }catch (SQLException  e) {
            throw new DaoException("Error executing query " + query, e);
        } finally {
            DaoUtil.releaseResources(connection, statement, resultSet);
        }
        return result;
    }
}
