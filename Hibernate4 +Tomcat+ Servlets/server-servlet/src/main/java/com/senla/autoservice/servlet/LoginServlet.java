package com.senla.autoservice.servlet;

import com.senla.autoservice.bean.User;
import com.senla.autoservice.manager.UserManager;
import com.senla.autoservice.utills.constants.Constants;
import com.senla.autoservice.utills.hashgenerator.TokenGenerator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {


    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            final String login = req.getParameter(Constants.USERNAME);
            final String password = req.getParameter(Constants.PASSWORD);
            if (login != null && password != null && !login.isEmpty() && !password.isEmpty()) {
                final User user = new UserManager().getUser(login, password);
                if (user != null) {
                    final PrintWriter writer = resp.getWriter();
                    req.getSession().setAttribute(Constants.USER, user);
                    writer.println("Login OK");
                }
            }
        } catch (IOException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

