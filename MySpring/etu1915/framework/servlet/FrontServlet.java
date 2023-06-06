package etu1915.framework.servlet;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Annotation.Choosen;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.MultipartConfig;
import utilities.ModelView;
import utilities.Url;

import etu1915.framework.Mapping;

@MultipartConfig(fileSizeThreshold = 2240 * 2240, maxFileSize = 2240 * 2240 * 5, maxRequestSize = 2240 * 2240 * 5 * 5)
public class FrontServlet extends HttpServlet {

    HashMap<String, Mapping> mappingUrls;
    HashMap<String, Object> singletonsClass;

    public void init() throws ServletException {
        super.init();
        String pathToClasses = this.getInitParameter("pathClass");
        this.mappingUrls = this.getMap(pathToClasses);
        this.singletonsClass = new HashMap<String, Object>();
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
                out.println(target);
                out.println(gson.toJson(target));
                out.println("No redirection :( ");
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
            // rehefa mi return model view de ato
            if (!urlPage.equals("")) {
                RequestDispatcher dispat = req.getRequestDispatcher("/" + urlTo + "?" +
                        urlPage);
                dispat.forward(req, res);
            } else {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Object target = save(res, req);
                out.println(target);
                out.println(gson.toJson(target));
                out.println("No redirection :( ");
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
            ModelView view = null;
            urlSetted = mapping.getClassName();
            Class<?> classMapping = Class.forName(mapping.getClassName());
            // get the corresponding methods by the name in Mapping
            Vector<Method> methods = methodsMatchingName(mapping.getMethod(), classMapping);
            for (Method method : methods) {
                Class<?> returnType = method.getReturnType();
                if (returnType == Class.forName("utilities.ModelView")) {
                    Object switchClass = this.getInstance(classMapping);
                    // check if the method invoked has argument or not
                    if (method.getParameterCount() == 0) {
                        view = (ModelView) method.invoke(switchClass);
                    } else {
                        view = (ModelView) method.invoke(switchClass, getMethodParamValues(method, req, res));
                    }
                    if (view.getUrl() != null)
                        urlView = view.getUrl();
                }
                if (view != null) {
                    addDataToRequest(req, view);
                    break;
                }
            }

        }
        return urlView;
    }

    public boolean isSingletion(Class classTarget) {
        Boolean present = false;
        if (classTarget.isAnnotationPresent(Choosen.class)) {
            Choosen choosen = (Choosen) classTarget.getAnnotation(Choosen.class);
            if (choosen.scope().equals(("singleton"))) {
                present = true;
            }
        }
        return present;
    }

    public Object getInstance(Class classTarget) throws Exception {
        Boolean singleton = isSingletion(classTarget);
        if (singleton) {
            String key = classTarget.getName();
            Object instance = this.singletonsClass.get(key);
            if (instance == null) {
                Object newInstance = classTarget.getConstructor().newInstance();
                this.singletonsClass.put(newInstance.getClass().getName(), newInstance);
                return newInstance;
            } else {
                return instance;
            }
        } else {
            return classTarget.getConstructor().newInstance();
        }
    }

    public Object[] getMethodParamValues(Method method, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        Vector<Object> paramsValues = new Vector<Object>();
        Parameter[] params = method.getParameters();
        PrintWriter out = res.getWriter();

        for (Parameter parameter : params) {
            String valueParam = req.getParameter(parameter.getName());
            paramsValues.add(transformeValue(valueParam, parameter.getType().getSimpleName()));
        }
        return paramsValues.toArray();
    }

    public Vector<Method> methodsMatchingName(String methodName, Class objectTarget) {
        Vector<Method> methodsList = new Vector<Method>();
        Method[] methods = objectTarget.getMethods();
        for (Method meth : methods) {
            if (meth.getName().equals(methodName)) {
                methodsList.add(meth);
            }
        }
        return methodsList;
    }

    public Object save(HttpServletResponse res, HttpServletRequest req) throws Exception {
        PrintWriter out = res.getWriter();
        // get the object target
        String urlSetted = Url.getUrlSetted(res, req);
        // verify if the url setted is in the hashmap
        Mapping mapping = mappingUrls.get(urlSetted);
        // if the url.do setted is not correspoding to any method
        if (mapping == null)
            return null;
        Class<?> classMapping = Class.forName(mapping.getClassName());
        Object repere = this.getInstance(classMapping);
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
            // for the upload part
            uploadFileIn(repere, req, fields[i], out);
            //
            if (valueParameter == null) {
                continue;
            }
            validAffectationField(repere, fields[i], valueParameter);
        }
        return repere;
    }

    public void uploadFileIn(Object obj, HttpServletRequest req, Field field, PrintWriter out) throws Exception {
        String fieldType = field.getType().getSimpleName();
        out.println(fieldType);

        if (fieldType.equals("Upload")) {
            field.set(obj, Upload.uploadFile(req, this));
        }
    }

    public void validAffectationField(Object obj, Field field, String value) throws Exception {
        String fieldType = field.getType().getSimpleName();

        if (fieldType.equals("int") || fieldType.equals("Integer")) {
            field.set(obj, value == null || value.equals("") ? 0 : Integer.valueOf(value));
        } else if (fieldType.equals("Double")
                || fieldType.equals("double")) {
            field.set(obj, value == null || value.equals("") ? 0.0 : Double.valueOf(value));
        } else if (fieldType.equals("boolean")
                || fieldType.equals("Boolean")) {
            field.set(obj, value == null || value.equals("") ? false : Boolean.valueOf(value));
        } else {
            field.set(obj, value == null ? null : value);
        }
    }

    public Object transformeValue(String value, String paramType) {
        if (paramType.equals("int") || paramType.equals("Integer")) {
            return value == null || value.equals("") ? 0 : Integer.valueOf(value);
        } else if (paramType.equals("Double")
                || paramType.equals("double")) {
            return value == null || value.equals("") ? 0.0 : Double.valueOf(value);
        } else if (paramType.equals("boolean")
                || paramType.equals("Boolean")) {
            return value == null || value.equals("") ? false : Boolean.valueOf(value);
        } else {
            return value == null ? null : value;
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
