package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.Review;
import ma.ac.ensias.model.entity.enums.Status;
import ma.ac.ensias.model.service.ReviewService;
import ma.ac.ensias.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ReviewUnblockCommand implements ActionCommand {

    private final ReviewService reviewService = ServiceFactory.getInstance().getReviewService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int reviewId = Integer.parseInt(request.getParameter(RequestParameterName.REVIEW_ID));
        Optional<Review> optionalReview;
        try{
            optionalReview = reviewService.findReview(reviewId);
        }catch (ServiceException e) {
            throw new CommandException("could n`t find review with such id", e);
        }
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            review.setStatus(Status.ACTIVE);
            try{
                reviewService.updateReview(review);
            }catch (ServiceException e) {
                throw new CommandException("could n`t update review", e);
            }
        }
        return new CommandResult(UrlPath.ADMIN_REVIEWS_DO, CommandResult.Type.REDIRECT);
    }
}
