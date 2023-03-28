package view;

import Annotation.Choosen;
import Annotation.Method;

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
}