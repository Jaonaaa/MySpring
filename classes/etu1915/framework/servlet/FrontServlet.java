package etu1915.framework.servlet;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import utilities.Url;

public class FrontServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        try {
            String clientString = processRequest(res, req);
            out.println(clientString);
        } catch (Exception e) {
            out.println("Exception: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        try {
            String clientString = processRequest(res, req);
            out.println(clientString);
        } catch (Exception e) {
            out.println("Exception: " + e.getMessage());
        }
    }

    public String processRequest(HttpServletResponse res, HttpServletRequest req) throws Exception {

        String urlSetted = Url.getUrlSetted(res, req);
        return urlSetted;
    }

}
