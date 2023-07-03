package models;

import java.util.HashMap;
import java.util.Vector;

import Annotation.Auth;
import Annotation.Choosen;
import Annotation.JsonData;
import Annotation.Method;
import Annotation.UseSession;
import etu1915.framework.servlet.Upload;
import utilities.ModelView;

@Choosen(scope = "singleton")
public class Emp {

    private String name;
    private int age;
    private double salary;
    private Upload empPicture;
    private int counter = 0;
    private HashMap sessions = new HashMap();

    public Emp() {
    }

    public Emp(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    @Auth(profil = { "admin" })
    @Method(urlTo = "get-emp-by-id.do")
    public ModelView getEmpById(int id, String test) {
        ModelView view = new ModelView();
        Emp emp1 = new Emp("Peter " + test, id, 3440.0);
        Emp emp2 = new Emp("Paul " + test, id, 4440.0);
        Emp emp3 = new Emp("Patrick " + test, id, 6440.0);
        Emp emp4 = new Emp("Poo " + test, id, 3340.0);
        Emp emp5 = new Emp("Pitri " + test, id, 1140.0);

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

    @Method(urlTo = "add-emp.do")
    public ModelView getPage() {
        ModelView view = new ModelView();
        Vector<String> params = new Vector<String>();
        params.add("Peter ");
        params.add("Hello Eleonara");
        params.add("Hello Paolo");
        params.add("Hello Defefeeff");
        view.addItem("hellos", params);
        this.counter = this.counter + 1;
        view.addItem("counter", this.counter);

        view.setUrl("Emp.jsp");
        return view;
    }

    @Method(urlTo = "login.do")
    public ModelView login() {
        ModelView view = new ModelView();
        view.addSession("Profil", "admin");
        view.addSession("Name_user", "Peter");
        view.setUrl("index.jsp");
        return view;
    }

    @Method(urlTo = "view-to-json.do")
    public ModelView viewJson() {
        ModelView view = new ModelView();
        view.addItem("test", "hello World");
        view.addItem("name", "Person");
        view.setIsJson(true);
        return view;
    }

    @JsonData
    @Method(urlTo = "return-to-json.do")
    public Object toJson() {
        ModelView view = new ModelView();
        view.addItem("test", "mety le testtt");
        view.addItem("name", "Person");
        return view;
    }

    @Method(urlTo = "logout.do")
    public ModelView log_out() {
        ModelView view = new ModelView();
        view.addSession("Profil", null);
        view.setUrl("index.jsp");
        return view;
    }

    @Method(urlTo = "remove_Session.do")
    public ModelView removeSession() {
        ModelView view = new ModelView();
        view.removeSession("Name_user");
        view.setUrl("index.jsp");
        return view;
    }

    @Method(urlTo = "remove_all_session.do")
    public ModelView removeAllSession() {
        ModelView view = new ModelView();
        view.removeAllSession();
        view.setUrl("index.jsp");
        return view;
    }

    @UseSession
    @Method(urlTo = "checkSession.do")
    public ModelView sessions() {
        ModelView view = new ModelView();
        String profil = (String) this.getSessions().get("Profil");
        String name = (String) this.getSessions().get("Name_user");
        view.addSession("SessionCheck", "Correct > " + profil + " > " + name);
        view.setUrl("Session.jsp");
        return view;
    }

    @Method(urlTo = "UploadPage.do")
    public ModelView uploadPage() {
        ModelView view = new ModelView();
        view.setUrl("Upload.jsp");
        return view;
    }

    @Method(urlTo = "upload.do")
    public String upload() {
        return "Hello World";
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

    public Upload getEmpPicture() {
        return empPicture;
    }

    public void setEmpPicture(Upload empPicture) {
        this.empPicture = empPicture;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public HashMap getSessions() {
        return sessions;
    }

    public void setSessions(HashMap sessions) {
        this.sessions = sessions;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}