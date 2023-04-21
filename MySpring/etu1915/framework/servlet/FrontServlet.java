package etu1915.framework.servlet;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

            if (!urlPage.equals("")) {
                RequestDispatcher dispat = req.getRequestDispatcher("/" + urlTo + "?" + urlPage);
                dispat.forward(req, res);
            } else {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Object target = save(res, req);
                out.println(gson.toJson(target));

            }

        } catch (Exception e) {
            out.println(" Exception : " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        try {
            String urlTo = "index.jsp";

            String urlPage = processRequest(res, req);
            if (!urlPage.equals("")) {
                urlTo = urlPage;
            }

            if (!urlPage.equals("")) {
                RequestDispatcher dispat = req.getRequestDispatcher("/" + urlTo + "?" + urlPage);
                dispat.forward(req, res);
            } else {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Object target = save(res, req);
                out.println(gson.toJson(target));

            }

        } catch (Exception e) {
            out.println(" Exception : " + e.getMessage());
        }
    }

    public String processRequest(HttpServletResponse res, HttpServletRequest req) throws Exception {
        String urlSetted = Url.getUrlSetted(res, req);
        String urlView = "";
        // verify if the url setted is in the hashmap
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

    public Object save(HttpServletResponse res, HttpServletRequest req) throws Exception {
        PrintWriter out = res.getWriter();

        // get the object target
        String urlSetted = Url.getUrlSetted(res, req);
        // verify if the url setted is in the hashmap
        Mapping mapping = mappingUrls.get(urlSetted);
        Class<?> classMapping = Class.forName(mapping.getClassName());
        Object repere = classMapping.getConstructor().newInstance();

        //
        Field[] fields = repere.getClass().getDeclaredFields();
        // Get all field a try to match the filed and the parameter from the client
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            // if(fields[i].getType().getName().equals(fields))
            validAffectationField(repere, fields[i], null);
            String fieldName = fields[i].getName();
            // verify if the parameter filed is matching then continue or not
            String valueParameter = req.getParameter(fieldName);
            if (valueParameter == null) {
                continue;
            }
            validAffectationField(repere, fields[i], valueParameter);
        }
        return repere;
    }

    public void validAffectationField(Object obj, Field field, String value) throws Exception {
        String fieldType = field.getType().getSimpleName();
        if (fieldType.equals("int") || fieldType.equals("Integer")) {
            field.set(obj, value == null ? 0 : Integer.valueOf(value));
        } else if (fieldType.equals("Double")
                || fieldType.equals("double")) {
            field.set(obj, value == null ? 0.0 : Double.valueOf(value));
        } else if (fieldType.equals("boolean")
                || fieldType.equals("Boolean")) {
            field.set(obj, value == null ? false : Boolean.valueOf(value));
        } else {
            field.set(obj, value == null ? null : value);
        }

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
