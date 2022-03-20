package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.attribute.SessionAttributeName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.Rating;
import ma.ac.ensias.model.entity.User;
import ma.ac.ensias.model.service.RatingService;
import ma.ac.ensias.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RatingCommand implements ActionCommand {

    private final RatingService ratingService = ServiceFactory.getInstance().getRatingService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String rating_string = (String)request.getAttribute(RequestParameterName.RATING);
        if(rating_string == null || rating_string.isEmpty()){
            return new CommandResult(UrlPath.HOME_DO, CommandResult.Type.RETURN_URL);
        }
        int rating_value = Integer.parseInt(rating_string);
        int movieId = Integer.parseInt(request.getParameter(RequestParameterName.MOVIE_ID));
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(SessionAttributeName.USER);
        int userId = user.getId();
        Rating rating = new Rating(rating_value, userId, movieId);
        try {
            ratingService.update(rating);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new CommandResult(UrlPath.HOME_DO, CommandResult.Type.RETURN_URL);
    }
}
