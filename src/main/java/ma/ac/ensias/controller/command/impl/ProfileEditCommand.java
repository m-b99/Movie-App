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
import ma.ac.ensias.model.entity.User;
import ma.ac.ensias.model.service.UserService;
import ma.ac.ensias.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileEditCommand implements ActionCommand {

    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if(request.getMethod().equals(RequestMethod.GET)){

            return new CommandResult(PagePath.EDIT_PROFILE, CommandResult.Type.FORWARD);
        }
        String first_name = (String) request.getAttribute(RequestParameterName.FIRST_NAME);
        String second_name = (String) request.getAttribute(RequestParameterName.SECOND_NAME);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(SessionAttributeName.USER);
        user.setFirstName(first_name);
        user.setSecondName(second_name);
        boolean isSuccessful;
        try {
            isSuccessful = userService.updateUser(user);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if(isSuccessful){
            return new CommandResult(UrlPath.PROFILE_DO, CommandResult.Type.REDIRECT);
        }
        session.setAttribute(SessionAttributeName.ERROR_MESSAGE, "User changes were not saved");
        return new CommandResult(PagePath.EDIT_PROFILE, CommandResult.Type.FORWARD);
    }
}
