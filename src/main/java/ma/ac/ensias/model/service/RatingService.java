package ma.ac.ensias.model.service;

import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.Movie;
import ma.ac.ensias.model.entity.Rating;

import java.util.List;

/**
 * Interface provides actions on ratings
 *
 * @author Dmitriy Belotskiy
 */
public interface RatingService {

    /**
     * Updates rating in a datasource.
     *
     * @param rating movie entity
     * @return true if rating has been updated successfully, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean update(Rating rating) throws ServiceException;

    /**
     * Calculate rating for movie by movieId
     *
     * @param movieId id of movie
     * @return rating value
     * @throws ServiceException if an error occurs while processing.
     */
    double calcRating(int movieId) throws ServiceException;

    /**
     * Calculate rating for the list of movies
     *
     * @param movies list of movies
     * @return true if rating has been updated successfully, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean provideMoviesWithRating(List<Movie> movies) throws ServiceException;

}
