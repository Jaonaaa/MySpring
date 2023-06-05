package src;

import java.lang.reflect.Method;
import java.util.Vector;

public class Test {
    int age;
    Integer inetgeree;
    double doubledd;
    Double doubleeeD;
    boolean boole;
    Boolean boo;

    /**
     * @param id
     * @param name
     */
    public String getElementById(double id, Boolean name, String popo) {
        return "id =" + id + " name = " + name + " popo = " + popo;
    }

    public Class<?>[] paramsToArray() {
        Vector<Class<?>> clas = new Vector<Class<?>>();
        clas.add(getCorrespondanteClass("double"));
        clas.add(getCorrespondanteClass("Boolean"));
        clas.add(getCorrespondanteClass("String"));
        Class<?>[] classes = new Class<?>[clas.size()];
        for (int i = 0; i < clas.size(); i++) {
            classes[i] = clas.get(i);
        }
        return classes;
    }

    public Vector<Method> methodsMatchingName(String methodName, Object objectTarget) {
        Vector<Method> methodsList = new Vector<Method>();
        Method[] methods = objectTarget.getClass().getMethods();
        for (Method meth : methods) {
            if (meth.getName().equals(methodName)) {
                methodsList.add(meth);
            }
        }
        return methodsList;
    }

    public Class<?> getCorrespondanteClass(String typeName) {
        if (typeName.equals("int"))
            return int.class;
        else if (typeName.equals("double"))
            return double.class;
        else if (typeName.equals("Integer"))
            return Integer.class;
        else if (typeName.equals("Double"))
            return Double.class;
        else if (typeName.equals("Boolean"))
            return Boolean.class;
        else if (typeName.equals("boolean"))
            return boolean.class;
        else if (typeName.equals("long"))
            return long.class;
        else if (typeName.equals("Long"))
            return Long.class;
        else if (typeName.equals("String"))
            return String.class;
        else
            return null;
    }

}
