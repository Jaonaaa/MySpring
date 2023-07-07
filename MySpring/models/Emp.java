package models;

import Annotation.Choosen;
import Annotation.Method;
import Annotation.Auth;
import utilities.ModelView;
import java.util.Vector;
import java.util.HashMap;

@Choosen(scope = "singleton")
public class Emp {

    private String name;
    private int age;
    private double salary;
    public HashMap<String, Object> sessions;

    public Emp() {
    }

    public Emp(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    @Auth
    @Method(urlTo = "get-emp-by-id.do")
    public ModelView getEmpById(int id) {
        ModelView view = new ModelView();
        Emp emp1 = new Emp("Peter", id, 3440.0);
        Emp emp2 = new Emp("Paul", id, 4440.0);
        Emp emp3 = new Emp("Patrick", id, 6440.0);
        Emp emp4 = new Emp("Poo", id, 3340.0);
        Emp emp5 = new Emp("Pitri", id, 1140.0);

        if (id == 1)
            view.addItem("emp_target", emp1);
        else if (id == 2)
            view.addItem("emp_target", emp2);
        else if (id == 3)
            view.addItem("emp_target", emp3);
        else if (id == 4)
            view.addItem("emp_target", emp4);
        else if (id == 5)
            view.addItem("emp_target", emp5);
        else
            view.addItem("emp_target", emp3);

        view.setUrl("Emp.jsp");
        return view;
    }

    @Method(urlTo = "emp-page.do")
    public ModelView getPage() {
        ModelView view = new ModelView();
        Vector<String> params = new Vector<String>();
        params.add("Peter ");
        params.add("Hello Eleonara");
        params.add("Hello Paolo");
        params.add("Hello Defefeeff");
        view.addItem("hellos", params);
        view.setUrl("Emp.jsp");
        return view;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
