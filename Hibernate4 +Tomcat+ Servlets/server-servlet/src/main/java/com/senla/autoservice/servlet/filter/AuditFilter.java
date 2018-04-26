package com.senla.autoservice.servlet.filter;


import com.senla.autoservice.bean.User;
import com.senla.autoservice.facade.Autoservice;
import com.senla.autoservice.utills.constants.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "AuditFilter")
public class AuditFilter implements Filter {
    private FilterConfig config;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain filterChain) {
        try {
            final HttpServletRequest request = (HttpServletRequest) req;
            final Object user = request.getSession().getAttribute(Constants.USER);
            final String url = request.getRequestURI();
            String method = ((HttpServletRequest) req).getMethod().toString();
            Autoservice.getInstance().auditRequest((User) user, url, method);
            filterChain.doFilter(req, resp);
        } catch (Exception ex){

        }
    }

    @Override
    public void destroy() {

    }
}
