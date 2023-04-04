package etu1915.framework.servlet;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

import javax.swing.text.Utilities;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import utilities.ModelView;
import utilities.Url;

import etu1915.framework.Mapping;

public class FrontServlet extends HttpServlet {

    HashMap<String, Mapping> mappingUrls;

    public void init() throws ServletException {
        super.init();
        String pathToClasses = this.getInitParameter("pathClass");
        this.mappingUrls = this.getMap(pathToClasses);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        try {
            String urlTo = "index.jsp";

            String urlPage = processRequest(res, req);
            if (!urlPage.equals("")) {
                urlTo = urlPage;
            }

            RequestDispatcher dispat = req.getRequestDispatcher("/" + urlTo);
            dispat.forward(req, res);

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
        String urlView = "";
        Mapping mapping = mappingUrls.get(urlSetted);
        if (mapping != null) {
            urlSetted = mapping.getClassName();
            Class<?> classMapping = Class.forName(mapping.getClassName());
            Method methodMapping = classMapping.getMethod(mapping.getMethod());
            Class<?> returnType = methodMapping.getReturnType();

            if (returnType == Class.forName("utilities.ModelView")) {
                Object switchClass = classMapping.getConstructor().newInstance();
                ModelView view = (ModelView) methodMapping.invoke(switchClass);
                if (view.getUrl() != null) {
                    urlView = view.getUrl();
                }
                addDataToRequest(req, view);
            }
        }
        return urlView;
    }

    public void addDataToRequest(HttpServletRequest req, ModelView modelView) {
        HashMap<String, Object> datas = modelView.getDatas();
        for (String key : datas.keySet()) {
            req.setAttribute(key, datas.get(key));
        }
    }

    public HashMap<String, Mapping> getMap(String pathToClasses) {
        HashMap<String, Mapping> map = null;
        try {
            String classesPath = this.getServletContext().getRealPath(pathToClasses);
            map = new Url().getGuide(classesPath);
        } catch (Exception e) {
        }
        return map;
    }

}
