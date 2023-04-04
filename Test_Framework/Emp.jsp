<%@ page language="java" contentType="text/html"
 import="java.util.*"
pageEncoding="UTF-8" %>



<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Emp</title>
  </head>
  <body>
    Hello , Emp Page :))
    <%
      Vector<String> list = (Vector<String>) request.getAttribute("hellos");
      for (int i = 0; i < list.size(); i++)
      {
        out.print(list.get(i)+"</br>");
      }
    %>
  </body>
</html>
