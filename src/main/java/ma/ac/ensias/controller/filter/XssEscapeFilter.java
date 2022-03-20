package ma.ac.ensias.controller.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;

public class XssEscapeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Map<String, String[]> parameterMap = servletRequest.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()){
            String[] paramValues = entry.getValue();
            for(int i = 0; i < paramValues.length; i++){
                paramValues[i] = paramValues[i].replace(">", "&gt;")
                        .replace(" ", "&nbsp;")
                        .replace(" ", "&nbsp;")
                        .replace("\"", "&quot;")
                        .replace("\n", "<br/> ")
                        .replace("<", "&lt;");
            }
            servletRequest.setAttribute(entry.getKey(), paramValues[0]);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
