package ma.ac.ensias.model.dao;

import ma.ac.ensias.exception.DaoException;
import ma.ac.ensias.model.entity.Rating;

import java.util.Map;

public interface RatingDao {

    boolean isExists(Rating rating) throws DaoException;

    boolean update(Rating rating) throws DaoException;

    boolean save(Rating rating) throws DaoException;

    double calcAverageRating(int movieId) throws DaoException;

    Map<Integer, Double> provideAllRatings() throws DaoException;

}
