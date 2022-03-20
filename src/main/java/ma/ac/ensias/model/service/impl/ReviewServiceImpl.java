package ma.ac.ensias.model.service.impl;

import ma.ac.ensias.exception.DaoException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.dao.ReviewDao;
import ma.ac.ensias.model.dao.factory.DaoFactory;
import ma.ac.ensias.model.entity.Review;
import ma.ac.ensias.model.entity.enums.Status;
import ma.ac.ensias.model.service.ReviewService;
import ma.ac.ensias.util.comparator.ReviewComparator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao = DaoFactory.getInstance().getReviewDao();

    @Override
    public List<Review> findReviewsForAdmin() throws ServiceException {
        List<Review> reviews;
        try {
            reviews = reviewDao.findALl();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return reviews;
    }

    @Override
    public List<Review> findAllReviewsByMovieId(int movieId) throws ServiceException {
        List<Review> reviews;
        try {
            reviews = reviewDao.findAllByMovieId(movieId);
            reviews = reviews.stream()
                    .filter(review -> review.getStatus() == Status.ACTIVE)
                    .sorted(new ReviewComparator())
                    .collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return reviews;
    }

    @Override
    public Optional<Review> findReview(int reviewId) throws ServiceException {
        Optional<Review> optionalReview;
        try {
            optionalReview = reviewDao.findById(reviewId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalReview;
    }

    @Override
    public boolean updateReview(Review review) throws ServiceException {
        boolean result;
        try{
            result = reviewDao.update(review);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean addReview(Review review) throws ServiceException {
        boolean result;
        try{
            result = reviewDao.save(review);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean deleteReview(int reviewId) throws ServiceException {
        boolean result;
        try{
            result = reviewDao.delete(reviewId);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return result;
    }
}
