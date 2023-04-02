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
            String clientKey = processRequest(res, req);

            for (String key : mappingUrls.keySet()) {
                out.println(key + " => " + mappingUrls.get(key).getClassName() + "  -- "
                        + mappingUrls.get(key).getMethod());
            }

            Mapping mapping = mappingUrls.get(clientKey);
            String urlTo = "index.jsp";
            if (mapping != null) {
                urlTo = mapping.getClassName();
                Class<?> classMapping = Class.forName(mapping.getClassName());
                Method methodMapping = classMapping.getMethod(mapping.getMethod());
                Class<?> returnType = methodMapping.getReturnType();
                out.println(returnType);
                if (returnType == Class.forName("utilities.ModelView")) {
                    Object switchClass = classMapping.getConstructor().newInstance();
                    ModelView view = (ModelView) methodMapping.invoke(switchClass);
                    if (view.getUrl() != null) {
                        urlTo = view.getUrl();
                    }
                }
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

        return urlSetted;
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