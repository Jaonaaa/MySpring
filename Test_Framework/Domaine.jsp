<%@ page language="java" contentType="text/html" import="java.util.*,models.*"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Domaine</title>
    <link rel="stylesheet" href="/Test_Framework/assets/css/Form.css" />
    <link rel="stylesheet" href="/Test_Framework/assets/css/font.css" />
    <link rel="stylesheet" href="/Test_Framework/assets/css/index.css" />
  </head>
  <body>
    <div id="root">
      <div class="middle-section">
        <div class="title-section">Domaine Page</div>

        <% if(request.getAttribute("counter") != null) { %>
        <div class="row-form">
          <div class="counter">
            Counter Instance appelé :
            <div class="value-counter">
              <% out.println(request.getAttribute("counter")); %>
            </div>
          </div>
        </div>
        <% } %>
      </div>
    </div>
  </body>
</html>
