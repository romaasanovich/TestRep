package com.senla.autoservice.servlet;

import com.google.gson.Gson;
import com.senla.autoservice.bean.Master;
import com.senla.autoservice.bean.Order;
import com.senla.autoservice.facade.Autoservice;
import com.senla.autoservice.utills.Convert;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
@WebServlet("/order")

public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp){
        ArrayList<Order> orders = Autoservice.getInstance().getOrdersByDateOfStart();
        try {
            if (orders != null && !orders.isEmpty()) {
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                String s = new Gson().toJson(orders, ArrayList.class);
                JSONArray jsonObject = new JSONArray(s);
                out.print(jsonObject);
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } else {
                resp.getWriter().println("Empty base");
            }
        }
        catch (IOException ex){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            String id = req.getParameter("id");
            if (id != null && !id.isEmpty()) {
                try {
                    resp.setContentType("application/json");
                    PrintWriter out = resp.getWriter();
                    Order order = Autoservice.getInstance().getOrderById(Integer.parseInt(id));
                    String s = new Gson().toJson(order, Order.class);
                    JSONObject jsonObject = new JSONObject(s);
                    out.print(jsonObject);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    return;
                } catch (Exception ignored) {
                }
            }
            resp.getWriter().println("Wrong request");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        catch (IOException ex){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    @Override
    protected void doPut(final HttpServletRequest req, final HttpServletResponse resp){
        try {
            String idService = req.getParameter("idWork");
            String idMaster = req.getParameter("idMaster");
            String idPlace = req.getParameter("idPlace");
            String status = req.getParameter("status");
            String orderDate = req.getParameter("ordDate");
            String plannedStartDate = req.getParameter("startDate");
            String completionDate = req.getParameter("complDate");

            if (idService != null && !idService.isEmpty() &&
                    idMaster != null && !idMaster.isEmpty() &&
                    idPlace != null && !idPlace.isEmpty() &&
                    status != null && !status.isEmpty() &&
                    orderDate != null && !orderDate.isEmpty() &&
                    plannedStartDate != null && !plannedStartDate.isEmpty() &&
                    completionDate != null && !completionDate.isEmpty()) {
                try {
                    resp.setContentType("application/json");
                    PrintWriter out = resp.getWriter();
                    Autoservice.getInstance().addOrderToMaster(Integer.valueOf(idService), Integer.valueOf(idMaster), Integer.valueOf(idPlace), Convert.fromStrToStatus(status), orderDate, plannedStartDate, completionDate);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    return;
                } catch (Exception ignored) {
                }
            }
            resp.getWriter().println("Wrong request");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        catch (IOException ex){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
