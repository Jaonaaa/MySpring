<%@ page language="java" contentType="text/html" import="java.util.*,models.*"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Sessions</title>
    <link rel="stylesheet" href="/Test_Framework/assets/css/Form.css" />
    <link rel="stylesheet" href="./assets/css/font.css" />
    <link rel="stylesheet" href="./assets/css/index.css" />
  </head>
  <body>
    <div id="root">
      <div class="middle-section">
        <div class="title-section">Liste des Sessions</div>
      </div>
      <table>

     <tr>
      <th>Nom</th>
      <th>Valeur</th>
     </tr>
      <% 
      Enumeration<String> sessionKey = session.getAttributeNames();
        while (sessionKey.hasMoreElements()) {
          String key = sessionKey.nextElement();
      %>  
      <tr >
        <td > <%  out.println(key); %> </td>
        <td ><% out.println(session.getAttribute(key)); %></td>
      </tr>
      <% } %>
      </table>
    </div>
   
  </body>
</html>
