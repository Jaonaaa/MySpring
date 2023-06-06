package models;

import Annotation.Choosen;
import Annotation.Method;
import utilities.ModelView;

@Choosen
public class Domaine {

    private String name;
    private int counter = 0;

    @Method(urlTo = "get-Domaine-page.do")
    public ModelView getEmpById(int id, String test) {
        ModelView view = new ModelView();
        view.setUrl("Domaine.jsp");
        this.counter = this.counter + 1;

        view.addItem("counter", this.counter);
        return view;
    }
}
