package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.exception.CommandException;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.User;
import ma.ac.ensias.model.entity.enums.Status;
import ma.ac.ensias.model.service.UserService;
import ma.ac.ensias.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class UserBlockCommand implements ActionCommand {
    private final UserService userService = ServiceFactory.getInstance().getUserService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int userId = Integer.parseInt(request.getParameter(RequestParameterName.USER_ID));
        Optional<User> optionalUser;
        try {
            optionalUser = userService.findUser(userId);
        } catch (ServiceException e) {
            throw new CommandException("could n`t find user with such userId",e);
        }
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setStatus(Status.BLOCKED);
            try {
                userService.updateUser(user);
            } catch (ServiceException e) {
                throw new CommandException("could n`t update user",e);
            }
        }
        return new CommandResult(UrlPath.ADMIN_USERS_DO, CommandResult.Type.REDIRECT);
    }
}
