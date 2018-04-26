package com.senla.autoservice.servlet;

import com.senla.autoservice.facade.Autoservice;
import com.senla.autoservice.utills.constants.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/signup")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            final String login = req.getParameter(Constants.USERNAME);
            final String password = req.getParameter(Constants.PASSWORD);

            if (login != null && password != null && !login.isEmpty() && !password.isEmpty()) {
                final PrintWriter writer = resp.getWriter();
                Autoservice.getInstance().addUser(login, password);
                writer.print("New user is added!!");
            }
        } catch (IOException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
