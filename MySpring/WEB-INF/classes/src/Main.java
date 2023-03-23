package src;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;
import etu1915.framework.Mapping;
import utilities.ClassesInPackage;
import utilities.Package;
import utilities.Url;

public class Main {

    public static void main(String[] args) throws Exception {

        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(
                        "C:/Program Files/Apache Software Foundation/Tomcat 10.0/webapps/MySpring/WEB-INF/classes");
        System.out.println(stream);
        Class<?> c = Class.forName(
                "etu1915.framework.Mapping");
        System.out.println(c);
        // HashMap<String, Mapping> maps = new Url().getGuide("etu1915/");
        // System.out.println(maps);
        // for (String key : maps.keySet()) {
        // System.out.println(key + " => " + maps.get(key).getClassName());
        // }
        // Package pkg = new Package();
        // pkg.getAllPackageInZ("etu1915", null, 0);
        // System.out.println();
        // for (String string : pkg.getAllPackage()) {
        // System.out.println(string);
        // }

    }
}
