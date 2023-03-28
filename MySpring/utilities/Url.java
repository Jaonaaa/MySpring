package utilities;

import java.io.File;
import java.text.Annotation;
import java.util.HashMap;
import java.util.Vector;

import Annotation.Choosen;
import Annotation.Method;
import etu1915.framework.Mapping;
import jakarta.servlet.http.*;

public class Url {

    public static String getUrlSetted(HttpServletResponse res, HttpServletRequest req) throws Exception {
        StringBuffer url = req.getRequestURL();
        String context = req.getContextPath();
        int index = url.indexOf(req.getContextPath());
        String otherArgs = "";
        // +1 for not taking the "/"
        for (int i = index + (context.length()) + 1; i < url.length(); i++) {
            otherArgs += url.toString().charAt(i);
        }
        return otherArgs;
    }

    public Vector<Class<?>> getAllClasses(String pathRacine, String pathTarget, Vector<Class<?>> classes)
            throws Exception {
        File file = new File(pathTarget);
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                // out.println(files[i].getAbsolutePath());
                String allPath = files[i].getAbsolutePath();
                File fileChild = new File(allPath);
                if (fileChild.isDirectory()) {
                    getAllClasses(pathRacine, fileChild.getAbsolutePath(), classes);
                } else {
                    if (allPath.endsWith(".class")) {
                        String className = allPath.replace(pathRacine, "");
                        classes.add(Class.forName(className.replace("\\", ".").replace(".class", "")));
                    }
                }
            }
        }
        return classes;
    }

    public HashMap<String, Mapping> getGuide(String packageName) throws Exception {
        Vector<Mapping> mappings = new Vector<Mapping>();
        Vector<Class<?>> classes = null;
        ////
        try {

            classes = this.getAllClasses(packageName + "\\", packageName, new Vector<Class<?>>());
        } catch (Exception e) {
            throw new Exception(e);
        }
        //
        //
        if (classes != null) {
            for (Class<?> class1 : classes) {
                if (class1.isAnnotationPresent(Choosen.class)) {
                    // System.out.println(class1);
                    java.lang.reflect.Method[] methods = class1.getDeclaredMethods();
                    for (int i = 0; i < methods.length; i++) {
                        if (methods[i].isAnnotationPresent(Method.class)) {
                            Mapping mapping = new Mapping();
                            mapping.setClassName(class1.getCanonicalName());
                            mapping.setMethod(methods[i].getName());
                            mappings.add(mapping);
                        }
                    }
                }
            }
        }

        //
        //
        HashMap<String, Mapping> maps = new HashMap<String, Mapping>();
        //
        for (Mapping mapping : mappings) {
            String[] blocks = mapping.getClassName().split("[.]");
            String key = blocks[blocks.length - 1] + "-" + mapping.getMethod();
            maps.put(key, mapping);
        }
        // for (String key : maps.keySet()) {
        // System.out.println(key + " => " + maps.get(key).getClassName());
        // }
        return maps;
    }

}
