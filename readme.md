# Prérequies

- Version JDK 21.0.2

# Utilisation

- Mettre le jar dans le lib de votre projet

- Veuiller configurer votre web.xml comme ceci :

  - Faite en sorte que tout les urls se terminant par '.do' passe par la class
    FrontServlet
  - Mettre le path "/WEB-INF/classes" dans la balise `param-value` du `param-name` de `pathClass` le chemin vers vos fichier sources

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

  - Utiliser l'annotation `Method` et ajouter un attribut `urlTo` qui aura comme valeur l'url que vous
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

  - Lors de la compilation de vos fichier.java veuiller utiliser l'option `-parameters`

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

## Upload

- Pour uploader un fichier , veuillez mettre comme attribut de votre classe l'objet `Upload` pour qu'il vérifie l'envoye d'un fichier si c'est présent durant l'opération.

- Exemple : <br>

```java
@Choosen
public class Emp {

    private Upload pictureEmp;

}
```

## Singleton

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
  ```

## Manipulation Session

### Ajout/Modifier session

- Pour ajouter ou modifier une/des session(s) vous devrez utiliser la fonction `addSession(Object item)` dans l'objet de type
  `ModelView` dans une fonction qui retoune une `ModelView`

Exemple : <br>

```java
@Method(urlTo = "login.do")
  public ModelView login() {
      ModelView view = new ModelView();
      view.addSession("Profil", "admin");
      view.addSession("Name_user", "Peter");
      view.setUrl("index.jsp");
      return view;
  }
```

### Suppression session

- Pour supprimer une session utiliser la fonction `removeSession(String session_name)` l'objet de type
  `ModelView` dans une fonction qui retourne une `ModelView` ou bien supprimer tout les sessions
  actives en utilisant `removeAllSession()`

Exemple :

- Suppression d'une session <br>

```java
 @Method(urlTo = "remove_Session.do")
  public ModelView removeSession() {
      ModelView view = new ModelView();
      view.removeSession("Name_user");
      view.setUrl("index.jsp");
      return view;
  }
```

- Suppression de tous les sessions actives <br>

```java
 @Method(urlTo = "remove_all_session.do")
 public ModelView removeAllSession() {
     ModelView view = new ModelView();
     view.removeAllSession();
     view.setUrl("index.jsp");
     return view;
 }
```

### Utiliser les sessions

- Pour pouvoir utiliser la valeur des session dans une méthode, annoter vos méthode par
  l'annotation `UseSession`.

Exemple : <br>

```java
 @UseSession
 @Method(urlTo = "checkSession.do")
 public ModelView sessions() throws Exception {
     ModelView view = new ModelView();
     String profil = (String) this.getSessions().get("Profil");
     String name = (String) this.getSessions().get("Name_user");
     view.addSession("SessionCheck", "Correct > " + profil + " > " + name);
     view.setUrl("Session.jsp");
     return view;
 }
```

## Autorisation

- Pour donner à une méthode une autorisation requise pour pouvoir l'invoker utiliser l'annotation `Auth`
  qui s'appliquer qu'au méthode et qui prendra en argument dans l'attribut `profil` qui prendra en argument
  la valeur du profil accepté pour invoker la méthode.

Exemple :

```java

@Auth(profil = { "admin" })
  @Method(urlTo = "get-emp-by-id.do")
  public ModelView getEmpById(int id, String test) {
      ModelView view = new ModelView();
      Emp emp1 = new Emp("Peter " + test, id, 3440.0);
      Emp emp2 = new Emp("Paul " + test, id, 4440.0);
      Emp emp3 = new Emp("Patrick " + test, id, 6440.0);
      Emp emp4 = new Emp("Poo " + test, id, 3340.0);
      Emp emp5 = new Emp("Pitri " + test, id, 1140.0);
      ...
```

- Ici le profil requis pour invoker la méthode `getEmpById` est un profil de "admin" .

- Vous devez aussi configurer votre `web.xml` pour pouvoir ajouter une méthode d'autorisation à une méthode .
  Exemple :

```xml
      <init-param>
          <param-name>session_name</param-name>
          <param-value>Profil</param-value>
          <description>Name of the session for authentification</description>
      </init-param>
```

- Vous pourer modifier la valeur `param-value` pour arranger votre gestion du nom de la session à regarder
  pour l'autorisation requise pour accéder à une méthode annoter .
- La valeur du `param-value` est le nom de la session qui va être verifier pour regarder si le client
  qui appel la méthode à l'autorisation nécessaire.

  ## Données en Json

  ### Données du ModelView en Json

  - Pour transformer les données d'une méthode qui retourne un `ModelView` en format Json utiliser la fonction
    `setIsJson(boolean state)` et entrer la valeur `true`.

  Exemple : <br>

  ```java
  @Method(urlTo = "view-to-json.do")
    public ModelView viewJson() {
        ModelView view = new ModelView();
        view.addItem("test", "hello World");
        view.addItem("name", "Person");
        view.setIsJson(true);
        return view;
    }
  ```

  ### Retourner un objet en format Json

  - Pour retourner l'objet que retourne une méthode utiliser l'annotation `JsonData` .

  Exemple : <br>

  ```java
    @JsonData
    @Method(urlTo = "return-to-json.do")
    public Object toJson() {
        ModelView view = new ModelView();
        view.addItem("test", "mety le testtt");
        view.addItem("name", "Person");
        return view;
    }
  ```

# Installation

- Utiliser le fichier go.bat après l'avoir configurer :

  - Changer la valeur de `rootPath` par le path dossier où vous travaillez

  - Changer la valeur de `warFileName` par le nom du fichier.war que vous voulez pour votre projet

  - Changer la valeur de `jspFilePath` par le path où se trouver vos fichier.jsp et les fichiers qui vont avec (css,js,...etc)

  - Changer la valeur de `sourceProjectPath` par le path où se trouve vos fichiers sources

  - Changer la valeur de `tempFilePath` par le path où vous voulez que le fichier temporaire se situe

  - Changer la valeur de `xmlConfigFile` par le path vers votre web.xml

# Dépendances

- Veuiller ajouter le libraire `Gson` dans le `lib` de votre projet ou bien dans celle de votre serveur
  d'application
