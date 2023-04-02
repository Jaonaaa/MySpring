package models;

import javax.crypto.Cipher;

import Annotation.Choosen;
import Annotation.Method;
import utilities.ModelView;

@Choosen
public class Landing {
    @Method
    public String getForm() {
        return "<form>Form Lading page </form>";
    }

    @Method
    public String getHello() {
        return "<h1>Welcome</h1>";
    }

    @Method
    public ModelView getEmpPage() {
        ModelView view = new ModelView();
        view.setUrl("Emp.jsp");
        return view;
    }

}