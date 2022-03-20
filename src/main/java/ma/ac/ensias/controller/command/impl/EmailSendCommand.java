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
import ma.ac.ensias.util.MailSender;
import ma.ac.ensias.util.impl.GmailSender;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EmailSendCommand implements ActionCommand {

    private final MailSender mailSender = GmailSender.getInstance();
    private final UserService userService = ServiceFactory.getInstance().getUserService();
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String email = (String)request.getAttribute(RequestParameterName.EMAIL);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(SessionAttributeName.USER);
        try {
            mailSender.sendVerificationEmail(email, user);
            user.setEmail(email);
            user.setEmailConfirmed(false);
            userService.updateUser(user);
        } catch (MessagingException | ServiceException e) {
            throw new CommandException(e);
        }
        return new CommandResult(UrlPath.PROFILE_DO, CommandResult.Type.REDIRECT);
    }
}
