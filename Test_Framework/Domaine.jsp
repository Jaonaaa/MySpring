<%@ page language="java" contentType="text/html" import="java.util.*,models.*"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Emp</title>
    <link rel="stylesheet" href="/Test_Framework/assets/css/Form.css" />
  </head>
  <body>
    Test Page <% if(request.getAttribute("counter") != null) { %>
    <div class="row-form">
      <div class="label-form">Counter : <% out.println(request.getAttribute("counter")); %></div>
    </di v>
    <% } %>
  </body>
</html>
