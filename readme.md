# Prérequies

- Version JDK 1.8  

# Utilisation

- Mettre le jar dans le lib de votre projet

- Veuiller configurer votre web.xml comme ceci :

  - Faite en sorte que tout les urls se terminant par '.do' passe par la class
    FrontServlet
  - Mettre le dans la balise `param-value` le chemin vers vos fichier sources

  - Exemple:

  ```xml
  <myxml>
    <servlet>
      <servlet-name>FrontServlet</servlet-name>
      <servlet-class>etu1915.framework.servlet.FrontServlet</servlet-class>
    <init-param>
      <param-name>pathClass</param-name>
      <param-value>/WEB-INF/classes</param-value>
      <description>Path to your classes</description>
    </servlet>
    <servlet-mapping>
      <servlet-name>FrontServlet</servlet-name>
      <url-pattern>*.do</url-pattern>
  </servlet-mapping>
  <myxml>
  ```

## Association url

- Pour associer une methode à un url :

  - Utiliser l'annotation "Method" et ajouter un attribut "urlTo" qui aura comme valeur l'url que vous
    vouler associer à cette methode pour l'appeler
  - Noter que : La classe où se trouve la fonction a appelé doit etre annoté par l'annotation Choosen !!

  - Pour rediriger une méthode vers une `View` veuiller à cela fonction que vous appeler retourne une `ModelView`

    ```java
    @Choosen
    public class Emp {

    private String name;
    private int age;

    @Method(urlTo = "emp-page.do")
    public ModelView getEmpPage() {
      ///Your code here
    }
    }

    ```
## Transfert de données

- Pous envoyer des données depuis le backend vers le frontend avec `ModelView`:

  - La class ModelView possède une fonction `addItem(Object data)`qui permettra de transferer des données depuis
    les Models vers les Views
  - Pour indiquer le chemin de redirection vers la View, veuiller utiliser la fonction `setUrl(String view)`
    de la classe `ModelView` <br>
    Exemple :
    <br/>
    <br/>

  ```java
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
  ```

    <br>

- Pour envoyer des données depuis le frontend vers le backend:

  - Utiliser la fonction `save()` dans le FrontServlet pour récupérer les données transmisent depuis le Client
  - Veuiller à ce que les noms des champs que vous vouler envoyer correspondate aux noms des attributs de la classe cible

- Pour appeler une fonction qui prend en compte un ou des parametres :

  - Lors de la compilation de vos fichier.java veuiller utiliser l'option `-parameters ` 
  
  <br/>

  ```bat
  javac -parameters fichier.java
  ```

  - Veuillez à ce que les noms des champs envoyés correspondent aux nom des parametres de la fonction
  
   </br>
    -Exemple : <br> 
    
    Votre classe :

    ```java
    @Method(urlTo = "get-emp-by-id.do")
    public ModelView getEmpById(int id) {
      /// Your code here
    }
    ```

    <br> Champ correspondante : 

    ```html
    <form action="get-emp-by-id.do" method="get">
    <input name="id" placeholder="ID employé"/>
    <button> Search </emp>
    </form>
    ```

### Upload 

- Pour uploader un fichier , veuillez mettre comme attribut de votre classe l'objet `Upload` pour qu'il vérifie l'envoye d'un fichier si c'est présent durant l'opération.

- Exemple : <br> 

```java 
@Choosen
public class Emp {

    private Upload pictureEmp;

}
```


 ### Singleton   

- Pour assurer qu'une classe utilise la méthode de singleton ( il n'a qu'une seule instance )
  durant son utilisation.
   
   - Veuillez annoter la classe avec l'annotation `Choosen` ayant comme porté un `singleton`

   - Exemple : <br>

   ```java	
  	@Choosen(scope = "singleton")
  public class Emp {
    ...
    }

   ``

# Installation

  -  Utiliser le fichier go.bat ....
<!--
The "go.bat" file compile all .class file in the framework then create a .jar file and move that file in the
lib of the "Test_Framework" , then it create a .war file from the Test_Framework and you should see that
file.war now XD -->

<!-- ![Test imgae.](./white-shirt.jpg) -->
