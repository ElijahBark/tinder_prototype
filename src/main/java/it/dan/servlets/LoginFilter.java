package it.dan.servlets;

import it.dan.AppRunner;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        Cookie[] cookies = request.getCookies();
        String userLogin = "";
        if (cookies != null) {
            for (Cookie ck : cookies) {
                if (ck.getName().equals("userid")) {
                    userLogin = ck.getValue();
                    req.setAttribute("login", userLogin);
                }
            }
        }
        if (userLogin.equals("")) {
            ((HttpServletResponse) resp).sendRedirect("/login");
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
