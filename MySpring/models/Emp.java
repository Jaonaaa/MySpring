package models;

import Annotation.Choosen;
import Annotation.Method;
import utilities.ModelView;

@Choosen
public class Emp {

    @Method
    public void findAll() {

    }

    public void updateEmp() {

    }

    public ModelView empPage() {
        ModelView view = new ModelView();
        view.setUrl("Emp.jsp");
        return view;
    }

    @Method
    public void insertEmp() {

    }

    public void deleteEmp() {
    }
}
