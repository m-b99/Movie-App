package ma.ac.ensias.controller.command.impl;

import ma.ac.ensias.controller.attribute.CookieName;
import ma.ac.ensias.controller.attribute.SessionAttributeName;
import ma.ac.ensias.controller.command.ActionCommand;
import ma.ac.ensias.controller.command.CommandResult;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.exception.CommandException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements ActionCommand {


    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        String currentLocale = (String) session.getAttribute(SessionAttributeName.CURRENT_LOCALE);
        session.invalidate();
        session = request.getSession(true);
        session.setAttribute(SessionAttributeName.CURRENT_LOCALE, currentLocale);
        Cookie cookie = new Cookie(CookieName.USER_HASH, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        cookie = new Cookie(CookieName.USER_LOGIN, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new CommandResult(UrlPath.LOGIN_DO, CommandResult.Type.REDIRECT);
    }
}
