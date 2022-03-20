package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.attribute.SessionAttributeName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.UrlPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeLocaleCommand implements ActionCommand {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String locale = (String)request.getAttribute(RequestParameterName.CURRENT_LOCALE);
        HttpSession session = request.getSession();
        if(locale != null && !locale.isEmpty()){
            session.setAttribute(SessionAttributeName.CURRENT_LOCALE, locale);
        }
        return new CommandResult(UrlPath.HOME_DO, CommandResult.Type.RETURN_URL);
    }
}
