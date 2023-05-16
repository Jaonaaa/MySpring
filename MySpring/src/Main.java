package src;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Vector;

public class Main {

    public static void main(String[] args) throws Exception {

        Test test = new Test();
        Field[] fields = test.getClass().getDeclaredFields();
        //
        //
        Method[] meths = test.getClass().getMethods();
        Class<?>[] classes = test.paramsToArray();
        //
        //
        Object[] pp = new Object[3];
        pp[0] = 454;
        pp[1] = true;
        pp[2] = "P o o o o";
        Method meth = test.getClass().getMethod("getElementById", classes);
        System.out.println(meth.invoke(test, pp));
        Vector<Method> methods = test.methodsMatchingName("wait", test);
        for (Method method : methods) {
            System.out.println(method.getName() + "  " + method.getParameterCount());
        }
    }
}
