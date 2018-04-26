package com.senla.autoservice.servlet;

import com.senla.autoservice.bean.User;
import com.senla.autoservice.utills.constants.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            User user = (User) req.getSession().getAttribute(Constants.USER);
            if (user != null) {
                user.setToken("");
                final PrintWriter writer = resp.getWriter();
                writer.println("Disconected....");
            }
        } catch (IOException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
