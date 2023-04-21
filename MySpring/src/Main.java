package src;

import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws Exception {

        // HashMap<String, Mapping> maps = new Url().getGuide(
        // "C:\\Program Files\\Apache Software Foundation\\Tomcat
        // 10.0\\webapps\\MySpring\\WEB-INF\\classes");
        // for (String key : maps.keySet()) {
        // System.out.println(key);
        // }
        System.out.println("Hello World !! XD");
        Test test = new Test();
        Field[] fields = test.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getType().getSimpleName());
        }
    }

}
