package ma.ac.ensias.model.service.impl;

import ma.ac.ensias.exception.DaoException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.dao.RatingDao;
import ma.ac.ensias.model.dao.factory.DaoFactory;
import ma.ac.ensias.model.entity.Movie;
import ma.ac.ensias.model.entity.Rating;
import ma.ac.ensias.model.service.RatingService;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class RatingServiceImpl implements RatingService {
    private final RatingDao ratingDao = DaoFactory.getInstance().getRatingDao();

    @Override
    public boolean update(Rating rating) throws ServiceException {
        try {
            if(ratingDao.isExists(rating)){
                ratingDao.update(rating);
            }else{
                ratingDao.save(rating);
            }
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public double calcRating(int movieId) throws ServiceException {
        double result;
        try {
            result = ratingDao.calcAverageRating(movieId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean provideMoviesWithRating(List<Movie> movies) throws ServiceException {
        Map<Integer, Double> movieRatings;
        try {
            movieRatings = ratingDao.provideAllRatings();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        for(Movie movie : movies){
            double rating = movieRatings.get(movie.getId());
            String formattedRating = new DecimalFormat("#0.00").format(rating);
            movie.setRating(formattedRating);
        }
        return true;
    }
}
