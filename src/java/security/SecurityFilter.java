package security;

import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        boolean loggedIn = false;

        String loginURL = request.getContextPath() + "/login.xhtml";
        String expiredURL = request.getContextPath() + "/expired.xhtml";

        if (session.getAttribute("currentActiveUser") != null && session != null) {
            loggedIn = true;
        } else {
            loggedIn = false;
        }
        
        boolean loginRequest = request.getRequestURI().equals(loginURL);
        boolean expiredRequest = request.getRequestURI().equals(expiredURL);
        boolean resourceRequest = request.getRequestURI().startsWith("/THRUST/javax.faces.resource/");

        if (loggedIn || loginRequest || resourceRequest || expiredRequest) {

            if (!resourceRequest) {
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                response.setDateHeader("Expires", 0); // Proxies.
            }

            chain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURL);
        }

        /*
         if (session.getAttribute("currentActiveUser") != null || req.getRequestURI().endsWith("login.xhtml") || req.getRequestURI().endsWith("expired.xhtml")) {
         chain.doFilter(request, response);
         } else  {
         HttpServletResponse res = (HttpServletResponse) response;
         res.sendRedirect("login.xhtml");
         return;
         }
         */
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {
    }
}
