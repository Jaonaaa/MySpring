package etu1915.framework.servlet;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Annotation.Auth;
import Annotation.Choosen;
import Annotation.JsonData;
import Annotation.UseSession;
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
        Object urlPage = null;
        try {
            this.init_Sessions(req);
            String urlTo = "index.jsp";
            urlPage = processRequest(res, req);
            if (!urlPage.equals("")) {
                urlTo = (String) urlPage;
            }
            if (!urlPage.equals("")) {
                RequestDispatcher dispat = req.getRequestDispatcher("/" + urlTo + "?" + urlPage);
                dispat.forward(req, res);
            } else {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Object target = save(res, req, null);
                // out.println(target);
                // out.println(gson.toJson(target));
                // out.println("No redirection :( ");
            }
        } catch (Exception e) {
            out.println(" Exception : " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        Object urlPage = null;
        try {
            this.init_Sessions(req);
            String urlTo = "index.jsp";
            urlPage = processRequest(res, req);
            if (!urlPage.equals("")) {
                urlTo = (String) urlPage;
            }
            if (!urlPage.equals("")) {
                RequestDispatcher dispat = req.getRequestDispatcher("/" + urlTo + "?" + urlPage);
                dispat.forward(req, res);
            } else {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Object target = save(res, req, null);
                // out.println(target);
                out.println(gson.toJson(target));
                // out.println("No redirection :( ");
            }
        } catch (Exception e) {
            out.println(" Exception : " + e.getMessage());
        }
    }

    public void init_Sessions(HttpServletRequest req) throws Exception {
        String session_profile_name = this.getInitParameter("session_name");
        // Vérifier si une session existe
        HttpSession session = req.getSession();
        if (session.getAttribute(session_profile_name) == null) {
            session.setAttribute(session_profile_name, null);
        }
    }

    public Object processRequest(HttpServletResponse res, HttpServletRequest req) throws Exception {
        PrintWriter out = res.getWriter();
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
            int counterAcces = 0;

            for (Method method : methods) {
                boolean hasAccess = this.checkAccess(req, method);
                if (hasAccess) {
                    if (checkIfJsonReturnValue(method, classMapping, res, req)) {
                        return urlView;
                    } else {
                        view = this.getViewRequested(classMapping, method, req, res);
                        if (view != null) {
                            Object dataJson = checkIfJsonRequested(req, view);

                            // Check if ther is a data transformed into json
                            if (dataJson != null) {
                                out.println(dataJson);
                                return urlView;
                            }
                            if (view.getUrl() != null)
                                urlView = view.getUrl();
                            break;
                        }
                    }

                } else
                    counterAcces++;
            }
            if (counterAcces == methods.size())
                throw new Exception("Vous n'avez pas d'accès pour voir ce contenue");
        }
        return urlView;
    }

    public void checkRemoveSession(ModelView view, HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        Vector<String> session_to_remove = view.getRemove_session();
        try {
            for (String key : session_to_remove) {
                session.removeAttribute(key);
            }
            if (view.getInvalidate_session()) {
                session.invalidate();
            }
        } catch (Exception e) {
        }

    }

    public Boolean checkIfJsonReturnValue(Method method, Class<?> classReference, HttpServletResponse res,
            HttpServletRequest req) throws Exception {
        PrintWriter out = res.getWriter();
        if (method.isAnnotationPresent(JsonData.class)) {
            Object value = this.getReturnValue(classReference, method, req);
            if (value != null) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                out.println(gson.toJson(value));
            }
            return true;
        }
        return false;
    }

    public ModelView getViewRequested(Class<?> classReference, Method method, HttpServletRequest req,
            HttpServletResponse res) throws Exception {
        ModelView view = null;
        Class<?> returnType = method.getReturnType();
        if (returnType == Class.forName("utilities.ModelView")) {
            // Object switchClass = this.getInstance(classReference, method, req);
            Object switchClass = save(res, req, method);
            // check if the method invoked has argument or not

            if (method.getParameterCount() == 0) {
                view = (ModelView) method.invoke(switchClass);
            } else {
                view = (ModelView) method.invoke(switchClass, getMethodParamValues(method, req));
            }
        }
        if (view != null) {
            checkRemoveSession(view, req);
            addSessionFromView(req, view);
            addDataToRequest(req, view);
        }
        return view;
    }

    public Object getReturnValue(Class<?> classReference, Method method, HttpServletRequest req) throws Exception {
        Object returnValue = null;
        Class<?> returnType = method.getReturnType();

        Object switchClass = this.getInstance(classReference, method, req);
        // check if the method invoked has argument or not
        if (method.getParameterCount() == 0) {
            returnValue = method.invoke(switchClass);
        } else {
            returnValue = method.invoke(switchClass, getMethodParamValues(method, req));
        }
        return returnValue;
    }

    public void addSessionFromView(HttpServletRequest req, ModelView view) {
        HttpSession session = req.getSession();
        HashMap<String, Object> sessionHashMap = view.getSessions();
        for (Map.Entry<String, Object> entry : sessionHashMap.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    public boolean checkAccess(HttpServletRequest req, Method method) throws Exception {
        boolean authorized = false;
        if (method.isAnnotationPresent(Auth.class)) {
            Auth auth = (Auth) method.getAnnotation(Auth.class);
            String[] profilType = auth.profil();
            if (profilType.length == 0)
                return true;
            String profile_user = getCurrentProfil(req);
            if (profile_user == null)
                return false;
            for (String profile : profilType) {
                if (profile_user.equals(profile))
                    authorized = true;
            }
        } else {
            return true;
        }
        return authorized;
    }

    public String getCurrentProfil(HttpServletRequest req) {
        String session_profile_name = this.getInitParameter("session_name");
        HttpSession session = req.getSession();
        String profile = (String) session.getAttribute(session_profile_name);
        return profile;
    }

    public boolean isSingleton(Class classTarget) {
        Boolean present = false;
        if (classTarget.isAnnotationPresent(Choosen.class)) {
            Choosen choosen = (Choosen) classTarget.getAnnotation(Choosen.class);
            if (choosen.scope().equals(("singleton"))) {
                present = true;
            }
        }
        return present;
    }

    public Object getInstance(Class classTarget, Method method, HttpServletRequest req) throws Exception {
        Boolean singleton = isSingleton(classTarget);
        if (singleton) {
            String key = classTarget.getName();
            Object instance = this.singletonsClass.get(key);
            if (instance == null) {
                Object newInstance = classTarget.getConstructor().newInstance();
                this.checkIsSessionRequested(method, newInstance, req);

                this.singletonsClass.put(newInstance.getClass().getName(), newInstance);
                return newInstance;
            } else {
                this.checkIsSessionRequested(method, instance, req);
                return instance;
            }
        } else {
            Object newInstance = classTarget.getConstructor().newInstance();
            this.checkIsSessionRequested(method, newInstance, req);
            return newInstance;
        }
    }

    public void checkIsSessionRequested(Method method, Object instance, HttpServletRequest req) throws Exception {
        if (method != null && instance != null) {
            if (method.isAnnotationPresent(UseSession.class)) {
                // Vérifier si une session existe
                HttpSession session = req.getSession();
                Enumeration<String> sessionKey = session.getAttributeNames();
                HashMap<String, Object> newSession = new HashMap<String, Object>();
                //
                while (sessionKey.hasMoreElements()) {
                    String key = sessionKey.nextElement();
                    newSession.put(key, session.getAttribute(key));
                }
                this.addSessionToClass(instance, newSession);
            }
        }
    }

    public void addSessionToClass(Object instance, HashMap<String, Object> sessions) throws Exception {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals("sessions")) {
                fields[i].setAccessible(true);
                fields[i].set(instance, sessions);
            }
        }
    }

    public Object[] getMethodParamValues(Method method, HttpServletRequest req)
            throws Exception {
        Vector<Object> paramsValues = new Vector<Object>();
        Parameter[] params = method.getParameters();
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

    public Object save(HttpServletResponse res, HttpServletRequest req, Method method) throws Exception {
        PrintWriter out = res.getWriter();
        // get the object target
        String urlSetted = Url.getUrlSetted(res, req);
        // verify if the url setted is in the hashmap
        Mapping mapping = mappingUrls.get(urlSetted);
        // if the url.do setted is not correspoding to any method
        if (mapping == null)
            return null;
        Class<?> classMapping = Class.forName(mapping.getClassName());
        Object repere = this.getInstance(classMapping, method, req);
        //
        Field[] fields = repere.getClass().getDeclaredFields();
        // Get all field a try to match the filed and the parameter from the client
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            // for sessions
            if (fields[i].getName().equals("sessions") || fields[i].getName().equals("counter")) {
                continue;
            }
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

    public Object checkIfJsonRequested(HttpServletRequest req, ModelView view) {
        Boolean isRequested = view.getIsJson();
        Object dataJson = null;
        if (isRequested) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            dataJson = gson.toJson(view.getDatas());
        }
        return dataJson;
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
