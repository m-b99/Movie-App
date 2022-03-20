package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.PagePath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.Movie;
import ma.ac.ensias.model.service.MovieService;
import ma.ac.ensias.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminMoviesCommand implements ActionCommand {

    private final MovieService movieService = ServiceFactory.getInstance().getMovieService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        List<Movie> movies;
        try {
            movies = movieService.findALlMoviesForAdmin();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute(RequestParameterName.MOVIES, movies);
        return new CommandResult(PagePath.ADMIN_MOVIES, CommandResult.Type.FORWARD);
    }
}
