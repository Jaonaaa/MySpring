package utilities;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;

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

    public HashMap<String, Mapping> getGuide(String packageName) {
        Vector<Mapping> mappings = new Vector<Mapping>();
        Vector<Class<?>> classes = Url.getAllClasses(packageName);
        //
        //
        for (Class<?> class1 : classes) {
            Method[] methods = class1.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Mapping mapping = new Mapping();
                mapping.setClassName(class1.getSimpleName());
                mapping.setMethod(methods[i].getName());
                mapping.setFullName(class1.getCanonicalName());
                mappings.add(mapping);
            }
        }
        //
        //
        HashMap<String, Mapping> maps = new HashMap<String, Mapping>();
        //
        for (Mapping mapping : mappings) {
            String key = mapping.getClassName() + "/" + mapping.getMethod();
            maps.put(key, mapping);
        }
        // for (String key : maps.keySet()) {
        // System.out.println(key + " => " + maps.get(key).getClassName());
        // }
        return maps;
    }

    public static Vector<Class<?>> getAllClasses(String path) {
        Vector<Class<?>> classes = new Vector<Class<?>>();
        ClassesInPackage classeInPack = new ClassesInPackage();
        Package pkg = new Package();
        boolean innerClass = false;
        if (path.equals("./")) {
            pkg.getAllPackageIn(path, null);
        } else {
            //
            path = path.replace(".", "/");
            //
            pkg.getAllPackageIn(path, path);
            innerClass = true;
        }
        for (String pack : pkg.getAllPackage()) {
            Vector<Class<?>> resultClasses = classeInPack.getAllClassIn(pack);
            for (Class<?> resultClasse : resultClasses) {
                classes.add(resultClasse);
            }
        }
        if (innerClass) {
            Vector<Class<?>> resultClasses = classeInPack.getAllClassIn(path);
            for (Class<?> resultClasse : resultClasses) {
                classes.add(resultClasse);
            }
        }
        return classes;
    }
}
