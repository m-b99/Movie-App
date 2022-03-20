package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.PagePath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.Movie;
import ma.ac.ensias.model.entity.Review;
import ma.ac.ensias.model.service.LikeService;
import ma.ac.ensias.model.service.MovieService;
import ma.ac.ensias.model.service.RatingService;
import ma.ac.ensias.model.service.ReviewService;
import ma.ac.ensias.model.service.factory.ServiceFactory;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public class MovieCommand implements ActionCommand {
    private final MovieService movieService = ServiceFactory.getInstance().getMovieService();
    private final ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
    private final RatingService ratingService = ServiceFactory.getInstance().getRatingService();
    private final LikeService likeService = ServiceFactory.getInstance().getLikeService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int movieId = Integer.parseInt(request.getParameter(RequestParameterName.MOVIE_ID));
        Optional<Movie> optionalMovie;
        List<Review> reviews;
        double ratingValue;
        try {
            optionalMovie = movieService.findMovie(movieId);
            reviews = reviewService.findAllReviewsByMovieId(movieId);
            ratingValue = ratingService.calcRating(movieId);
            for(Review review : reviews){
                MutablePair<Integer, Integer> likePair = likeService.calcLikesAndDislikes(review.getId());
                review.setLikes(likePair.left);
                review.setDislikes(likePair.right);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        String formattedRating = new DecimalFormat("#0.00").format(ratingValue);
        if(optionalMovie.isPresent()){
            Movie movie = optionalMovie.get();
            movie.setReviews(reviews);
            movie.setRating(formattedRating);
            request.setAttribute(RequestParameterName.MOVIE, movie);
        }
        return new CommandResult(PagePath.MOVIE, CommandResult.Type.FORWARD);
    }
}
