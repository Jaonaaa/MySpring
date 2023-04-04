package models;

import java.util.Vector;

import javax.crypto.Cipher;

import Annotation.Choosen;
import Annotation.Method;
import utilities.ModelView;

@Choosen
public class Emp {

    @Method
    public String getHello() {
        return "<h1>Welcome</h1>";
    }

    @Method
    public ModelView getPage() {
        ModelView view = new ModelView();
        Vector<String> params = new Vector<String>();
        params.add("Hello Guys");
        params.add("Hello Eleonara");
        params.add("Hello Paolo");
        params.add("Hello Defefeeff");
        view.addItem("hellos", params);

        view.setUrl("Emp.jsp");
        return view;
    }

}