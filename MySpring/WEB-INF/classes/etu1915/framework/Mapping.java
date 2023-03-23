package etu1915.framework;

public class Mapping {

    String className;
    String method;
    // by me
    String fullName;

    public Mapping() {
    }

    public Mapping(String className, String method) {
        this.className = className;
        this.method = method;
    }

    public String getClassName() {
        return className;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}