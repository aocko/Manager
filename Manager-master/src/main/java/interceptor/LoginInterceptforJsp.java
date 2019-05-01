package interceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginInterceptforJsp implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (request.getServletPath().equals("/login.jsp")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getServletPath().equals("/image.jsp")) {
            filterChain.doFilter(request, response);
            return;
        }
        if(session.getAttribute("currentUser") != null){
            filterChain.doFilter(request, response);
            return;
        }
      else if (session.getAttribute("currentUser") == null) {
            response.sendRedirect("/login.jsp");
            return;
        }


    }
}

