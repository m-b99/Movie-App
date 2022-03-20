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

public class ReviewAddCommand implements ActionCommand {
    private final ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String text = (String)request.getAttribute(RequestParameterName.TEXT);
        if(text == null || text.isEmpty()){
            return new CommandResult(UrlPath.HOME_DO, CommandResult.Type.RETURN_URL);
        }
        int userId = Integer.parseInt(request.getParameter(RequestParameterName.USER_ID));
        int movieId = Integer.parseInt(request.getParameter(RequestParameterName.MOVIE_ID));
        Review review = new Review("", "", text, 0, Status.ACTIVE, userId, movieId);
        try {
            reviewService.addReview(review);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new CommandResult(UrlPath.HOME_DO, CommandResult.Type.RETURN_URL);
    }
}
