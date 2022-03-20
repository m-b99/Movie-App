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
import ma.ac.ensias.model.entity.enums.Status;
import ma.ac.ensias.model.service.MovieService;
import ma.ac.ensias.model.service.factory.ServiceFactory;
import ma.ac.ensias.model.validator.MovieValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MovieAddCommand implements ActionCommand {
    private final MovieService movieService = ServiceFactory.getInstance().getMovieService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if(request.getMethod().equals(RequestMethod.GET)){
            return new CommandResult(PagePath.ADMIN_MOVIE_ADD, CommandResult.Type.FORWARD);
        }
        String title = (String) request.getAttribute(RequestParameterName.TITLE);
        String country =  (String)request.getAttribute(RequestParameterName.COUNTRY);
        int year = Integer.parseInt(request.getParameter(RequestParameterName.YEAR));
        Genre genre = Genre.valueOf(request.getParameter(RequestParameterName.GENRE));
        MovieType movieType = MovieType.valueOf(request.getParameter(RequestParameterName.MOVIE_TYPE));
        int ageCategory = Integer.parseInt(request.getParameter(RequestParameterName.AGE_CATEGORY));
        String description =  (String)request.getAttribute(RequestParameterName.DESCRIPTION);
        String youtubeTrailer =  (String)request.getAttribute(RequestParameterName.YOUTUBE_TRAILER);
        String imagePath =  (String)request.getAttribute(RequestParameterName.IMAGE_PATH);
        Movie movie = new Movie(title, country, year, genre, movieType, ageCategory,
                description, youtubeTrailer, Status.ACTIVE, imagePath);

        boolean isValid = MovieValidator.validateMovie(movie);
        if(isValid){
            try {
                movieService.addMovie(movie);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            return new CommandResult(UrlPath.ADMIN_MOVIES_DO, CommandResult.Type.REDIRECT);
        }
        return new CommandResult(UrlPath.ADMIN_MOVIES_DO, CommandResult.Type.REDIRECT);
    }
}
