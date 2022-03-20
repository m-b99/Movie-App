package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.Movie;
import ma.ac.ensias.model.entity.enums.Status;
import ma.ac.ensias.model.service.MovieService;
import ma.ac.ensias.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class MovieBlockCommand implements ActionCommand {

    private final MovieService movieService = ServiceFactory.getInstance().getMovieService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int movieId = Integer.parseInt(request.getParameter(RequestParameterName.MOVIE_ID));
        Optional<Movie> optionalMovie;
        try{
            optionalMovie = movieService.findMovie(movieId);
        }catch (ServiceException e) {
            throw new CommandException("could n`t find movie with such id", e);
        }
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movie.setStatus(Status.BLOCKED);
            try{
                movieService.updateMovie(movie);
            }catch (ServiceException e) {
                throw new CommandException("could n`t update movie", e);
            }
        }
        return new CommandResult(UrlPath.ADMIN_MOVIES_DO, CommandResult.Type.REDIRECT);
    }
}
