package ma.ac.ensias.controller.filter;

import ma.ac.ensias.controller.attribute.RequestParameterName;
import ma.ac.ensias.controller.attribute.SessionAttributeName;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MessageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpSession session = request.getSession();
        String errorMessage = (String)session.getAttribute(SessionAttributeName.ERROR_MESSAGE);
        String successMessage = (String)session.getAttribute(SessionAttributeName.REGISTER_COMPLETED);
        if(errorMessage != null){
            request.setAttribute(RequestParameterName.ERROR_MESSAGE,errorMessage);
        }
        if(successMessage != null){
            request.setAttribute(RequestParameterName.SUCCESS_MESSAGE, successMessage);
        }
        session.removeAttribute(SessionAttributeName.ERROR_MESSAGE);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
