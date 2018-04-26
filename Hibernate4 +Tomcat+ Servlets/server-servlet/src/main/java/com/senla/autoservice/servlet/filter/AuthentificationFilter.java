package com.senla.autoservice.servlet.filter;

import com.senla.autoservice.bean.User;
import com.senla.autoservice.facade.Autoservice;
import com.senla.autoservice.utills.constants.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/master", "/work", "/order", "/place"}, filterName = "AuthentificationFilter")
public class AuthentificationFilter implements Filter {

    private FilterConfig config;

    public void init(final FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain filterChain) {
        try {
            final HttpServletRequest request = (HttpServletRequest) req;
            final Object user = request.getSession().getAttribute(Constants.USER);

            if (user instanceof User) {
                try {
                    final String token = ((User) user).getToken();

                    if (Autoservice.getInstance().isValidToken(token)) {
                        filterChain.doFilter(req, resp);
                    }
                } catch (final Exception e) {
                    resp.getWriter().println("Invalid token. Please login.");
                }
            }
        }catch (IOException ex){
        }
    }

    @Override
    public void destroy() {

    }
}
