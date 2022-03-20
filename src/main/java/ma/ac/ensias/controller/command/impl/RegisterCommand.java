package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestMethod;
import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.attribute.SessionAttributeName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.PagePath;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.service.factory.ServiceFactory;
import ma.ac.ensias.model.validator.UserValidator;
import ma.ac.ensias.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterCommand implements ActionCommand {

    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final String LOGIN_IS_TAKEN = "User with this login is already registered";
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if(request.getMethod().equals(RequestMethod.GET)){
            return new CommandResult(PagePath.REGISTER, CommandResult.Type.FORWARD);
        }
        String login = (String) request.getAttribute(RequestParameterName.LOGIN);
        String password = (String) request.getAttribute(RequestParameterName.PASSWORD);
        String passwordConfirm = (String) request.getAttribute(RequestParameterName.PASSWORD_CONFIRM);
        HttpSession session = request.getSession();

        String errorMessage = UserValidator.validateUserForRegister(login,password, passwordConfirm);
        if(errorMessage != null && !errorMessage.isEmpty()){
            session.setAttribute(SessionAttributeName.ERROR_MESSAGE, errorMessage);
            return new CommandResult(UrlPath.REGISTER_DO, CommandResult.Type.REDIRECT);
        }

        boolean isRegisterComplete;
        try {
            isRegisterComplete = userService.register(login, password, passwordConfirm);
        }catch (ServiceException e){
            throw new CommandException(e);
        }
        if(isRegisterComplete){
            session.setAttribute(SessionAttributeName.REGISTER_COMPLETED, "User registered successfully!");
            return new CommandResult(UrlPath.LOGIN_DO, CommandResult.Type.REDIRECT);
        }else{
            session.setAttribute(SessionAttributeName.ERROR_MESSAGE, LOGIN_IS_TAKEN);
            return new CommandResult(UrlPath.REGISTER_DO, CommandResult.Type.REDIRECT);
        }
    }
}
