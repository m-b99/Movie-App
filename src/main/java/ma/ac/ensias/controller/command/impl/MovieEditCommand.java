package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestMethod;
import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.PagePath;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.Movie;
import ma.ac.ensias.model.entity.enums.Genre;
import ma.ac.ensias.model.entity.enums.MovieType;
import ma.ac.ensias.model.service.MovieService;
import ma.ac.ensias.model.service.factory.ServiceFactory;
import ma.ac.ensias.model.validator.MovieValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class MovieEditCommand implements ActionCommand {

    private final MovieService movieService = ServiceFactory.getInstance().getMovieService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int movieId = Integer.parseInt(request.getParameter(RequestParameterName.MOVIE_ID));
        Optional<Movie> optionalMovie;
        try {
            optionalMovie = movieService.findMovie(movieId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if(optionalMovie.isPresent()){
            Movie movie = optionalMovie.get();
            if(request.getMethod().equals(RequestMethod.GET)){
                request.setAttribute(RequestParameterName.MOVIE, movie);
                return new CommandResult(PagePath.ADMIN_MOVIE_EDIT, CommandResult.Type.FORWARD);
            }
            else if(request.getMethod().equals(RequestMethod.POST)){
                movie.setTitle((String)request.getAttribute(RequestParameterName.TITLE));
                movie.setCountry((String)request.getAttribute(RequestParameterName.COUNTRY));
                movie.setYear(Integer.parseInt(request.getParameter(RequestParameterName.YEAR)));
                movie.setGenre(Genre.valueOf(request.getParameter(RequestParameterName.GENRE)));
                movie.setMovieType(MovieType.valueOf(request.getParameter(RequestParameterName.MOVIE_TYPE)));
                movie.setAgeCategory(Integer.parseInt(request.getParameter(RequestParameterName.AGE_CATEGORY)));
                movie.setDescription((String)request.getAttribute(RequestParameterName.DESCRIPTION));
                movie.setYoutubeTrailer((String)request.getAttribute(RequestParameterName.YOUTUBE_TRAILER));
                movie.setImagePath((String)request.getAttribute(RequestParameterName.IMAGE_PATH));
                boolean isValid = MovieValidator.validateMovie(movie);
                if(isValid){
                    try {
                        movieService.updateMovie(movie);
                    } catch (ServiceException e) {
                        throw new CommandException(e);
                    }
                }
                return new CommandResult(UrlPath.ADMIN_MOVIES_DO, CommandResult.Type.REDIRECT);
            }
        }
        return new CommandResult(UrlPath.ADMIN_DO, CommandResult.Type.REDIRECT);
    }
}
