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
    <link rel="stylesheet" href="/Test_Framework/assets/css/Form.css">
  </head>
  <body>
    <form action="hello.do" method="get" id="form-register">
      <div class="title-form"> Enregistrer un employé</div>
      
      <div class="list-input">
        <div class="row-form">
          <div class="label-form"></div>
          <select name="" id="">
            <%
            Vector<String> list = (Vector<String>) request.getAttribute("hellos");
            for (int i = 0; i < list.size(); i++)
            {
             %>
             <option> <% out.print(list.get(i)+"</br>"); %>  </option>
            <%
              }
            %>
          </select>
          <input type="number" placeholder="Age" name="age">
          <input type="number" placeholder="Salary" name="salary">
          <button>Validate</button> 
        </div>

      </div>
    </form>
  
  </body>
</html>
