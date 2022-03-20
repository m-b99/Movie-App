package ma.ac.ensias.controller.listener;

import ma.ac.ensias.controller.attribute.LocaleValue;
import ma.ac.ensias.controller.attribute.SessionAttributeName;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.util.ImagePathProvider;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(SessionAttributeName.CURRENT_LOCALE, LocaleValue.EN.getLocale()); // remove it !!
        session.setAttribute(SessionAttributeName.DEFAULT_AVATAR_PATH, ImagePathProvider.getDefaultAvatar());
        session.setAttribute(SessionAttributeName.RETURN_URL, UrlPath.HOME_DO);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
