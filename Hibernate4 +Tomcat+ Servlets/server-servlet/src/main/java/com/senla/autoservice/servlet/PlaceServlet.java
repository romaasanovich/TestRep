package com.senla.autoservice.servlet;

import com.google.gson.Gson;
import com.senla.autoservice.bean.Master;
import com.senla.autoservice.bean.Place;
import com.senla.autoservice.facade.Autoservice;
import com.senla.autoservice.utills.constants.Constants;
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


@WebServlet("/place")
public class PlaceServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            ArrayList<Place> places = Autoservice.getInstance().getAllFreePlaces();
            if (places != null && !places.isEmpty()) {
                resp.setContentType(Constants.APP_JSON);
                PrintWriter out = resp.getWriter();
                String s = new Gson().toJson(places, ArrayList.class);
                JSONArray jsonObject = new JSONArray(s);
                out.print(jsonObject);
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } else {
                resp.getWriter().println(Constants.EMPTY_BASE);
            }
        } catch (IOException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            String id = req.getParameter(Constants.ID);
            if (id != null && !id.isEmpty()) {
                try {
                    resp.setContentType(Constants.APP_JSON);
                    PrintWriter out = resp.getWriter();
                    Place place = Autoservice.getInstance().getPlaceById(Integer.parseInt(id));
                    String s = new Gson().toJson(place, Place.class);
                    JSONObject jsonObject = new JSONObject(s);
                    out.print(jsonObject);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    return;
                } catch (Exception ignored) {
                }
            }
            resp.getWriter().println(Constants.WRONG_REQUEST);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    @Override
    protected void doPut(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            String name = req.getParameter(Constants.NAME);
            if (name != null && !name.isEmpty()) {
                try {
                    resp.setContentType(Constants.APP_JSON);
                    PrintWriter out = resp.getWriter();
                    Autoservice.getInstance().addPlace(name);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    return;
                } catch (Exception ignored) {
                }
            }
            resp.getWriter().println(Constants.WRONG_REQUEST);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
