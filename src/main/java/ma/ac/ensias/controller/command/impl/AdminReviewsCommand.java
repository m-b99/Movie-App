package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.PagePath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.Review;
import ma.ac.ensias.model.service.ReviewService;
import ma.ac.ensias.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminReviewsCommand implements ActionCommand {

    private final ReviewService reviewService = ServiceFactory.getInstance().getReviewService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        List<Review> reviews;
        try {
            reviews = reviewService.findReviewsForAdmin();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute(RequestParameterName.REVIEWS, reviews);
        return new CommandResult(PagePath.ADMIN_REVIEWS, CommandResult.Type.FORWARD);
    }
}
