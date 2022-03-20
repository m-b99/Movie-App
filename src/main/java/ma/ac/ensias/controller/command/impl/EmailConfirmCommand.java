package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.attribute.SessionAttributeName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.User;
import ma.ac.ensias.model.service.UserService;
import ma.ac.ensias.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class EmailConfirmCommand implements ActionCommand {

    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int userId = Integer.parseInt(request.getParameter(RequestParameterName.USER_ID));
        String token = (String)request.getAttribute(RequestParameterName.TOKEN);
        Optional<User> optionalUser;
        try{
            optionalUser = userService.confirmEmail(userId, token);
        }catch (ServiceException e){
            throw new CommandException(e);
        }
        if(optionalUser.isPresent()){
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttributeName.USER, optionalUser.get());
        }
        return new CommandResult(UrlPath.PROFILE_DO, CommandResult.Type.REDIRECT);
    }
}
