package ma.ac.ensias.controller.filter;

import ma.ac.ensias.controller.attribute.CookieName;
import ma.ac.ensias.controller.attribute.SessionAttributeName;
import ma.ac.ensias.controller.path.UrlPath;
import ma.ac.ensias.exception.ServiceException;
import ma.ac.ensias.model.entity.User;
import ma.ac.ensias.model.service.UserService;
import ma.ac.ensias.model.service.factory.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AuthenticationFilter implements Filter {

    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();

        if(request.getCookies() == null || session.getAttribute(SessionAttributeName.USER) != null){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        List<Cookie> cookies = List.of(request.getCookies());
        String userHash = null;
        String userLogin = null;
        for (var cookie : cookies) {
            if (cookie.getName().equals(CookieName.USER_HASH)) {
                userHash = cookie.getValue();
            }
            if (cookie.getName().equals(CookieName.USER_LOGIN)) {
                userLogin = cookie.getValue();
            }
        }
        if(userHash != null && userLogin != null){
            try{
                Optional<User> optionalUser = userService.findUserWithCookies(userLogin, userHash);
                if(optionalUser.isPresent()){
                    User user = optionalUser.get();
                    session.setAttribute(SessionAttributeName.USER, user);
                }
            }catch (ServiceException e){
                String page = request.getContextPath() + UrlPath.LOGIN_DO;
                response.sendRedirect(page);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
