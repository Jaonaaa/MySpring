import Annotation.Choosen;
import models.Emp;

public class Were {

    public static void main(String[] args) {

        Emp e = new Emp();
        if (e.getClass().isAnnotationPresent(Choosen.class)) {
            Choosen annoted = e.getClass().getAnnotation(Choosen.class);
            System.out.println(annoted.scope());
        }
    }
}
