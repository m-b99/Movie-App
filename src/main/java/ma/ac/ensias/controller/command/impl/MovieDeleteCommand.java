package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.service.MovieService;
import ma.ac.ensias.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MovieDeleteCommand implements ActionCommand {

    private final MovieService movieService = ServiceFactory.getInstance().getMovieService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int movieId = Integer.parseInt(request.getParameter(RequestParameterName.MOVIE_ID));
        try {
            movieService.deleteMovie(movieId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new CommandResult(UrlPath.ADMIN_MOVIES_DO, CommandResult.Type.REDIRECT);
    }
}
