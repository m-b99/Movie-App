package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.CookieName;
import ma.ac.ensias.controller.attribute.RequestMethod;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.attribute.SessionAttributeName;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.PagePath;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.User;
import ma.ac.ensias.model.entity.enums.Status;
import ma.ac.ensias.model.service.factory.ServiceFactory;
import ma.ac.ensias.model.validator.UserValidator;
import ma.ac.ensias.model.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginCommand implements ActionCommand {

    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if(request.getMethod().equals(RequestMethod.GET)){
            return new CommandResult(PagePath.LOGIN, CommandResult.Type.FORWARD);
        }
        String login = (String)request.getAttribute(RequestParameterName.LOGIN);
        String password = (String)request.getAttribute(RequestParameterName.PASSWORD);
        String[] checkBoxValues = request.getParameterValues(RequestParameterName.REMEMBER_ME);
        List<String> checkboxes = checkBoxValues != null ? List.of(checkBoxValues) : new ArrayList<>();
        boolean rememberMe = checkboxes.contains(RequestParameterName.ON);
        HttpSession session = request.getSession();
        String errorMessage = UserValidator.validateUserForLogin(login,password);
        if(errorMessage != null && !errorMessage.isEmpty()){
            session.setAttribute(SessionAttributeName.ERROR_MESSAGE, errorMessage);
            return new CommandResult(UrlPath.REGISTER_DO, CommandResult.Type.REDIRECT);
        }
        Optional<User> optionalUser;
        try{
            optionalUser = userService.login(login,password);
        }catch(ServiceException e){
            throw new CommandException(e);
        }

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.getStatus() == Status.BLOCKED){
                session.setAttribute(SessionAttributeName.ERROR_MESSAGE, "user is blocked");
                return new CommandResult(UrlPath.LOGIN_DO, CommandResult.Type.REDIRECT);
            }
            session.setAttribute(SessionAttributeName.USER, user);
            if(rememberMe){
                Cookie hashCookie = new Cookie(CookieName.USER_HASH, user.getUserHash());
                Cookie loginCookie = new Cookie(CookieName.USER_LOGIN, user.getLogin());
                hashCookie.setMaxAge(24*60*60);
                loginCookie.setMaxAge(24*60*60);
                response.addCookie(hashCookie);
                response.addCookie(loginCookie);
            }
            return new CommandResult(UrlPath.HOME_DO, CommandResult.Type.REDIRECT);
        }else {
            session.setAttribute(SessionAttributeName.ERROR_MESSAGE, "Incorrect login or password");
            return new CommandResult(PagePath.LOGIN, CommandResult.Type.FORWARD);
        }
    }
}
