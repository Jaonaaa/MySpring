package src;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;
import etu1915.framework.Mapping;
import utilities.ClassesInPackage;
import utilities.Package;
import utilities.Url;

public class Main {

    public static void main(String[] args) {

        HashMap<String, Mapping> maps = new Url().getGuide("utilities");
        for (String key : maps.keySet()) {
            System.out.println(key + " => " + maps.get(key).getClassName());
        }

    }
}
