<%@ page language="java" contentType="text/html"
 import="java.util.*,models.*"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Emp</title>
    <link rel="stylesheet" href="/Test_Framework/assets/css/font.css" />
    <link rel="stylesheet" href="/Test_Framework/assets/css/Form.css">
  </head>
  <body>

<% 
if(request.getAttribute("hellos") != null)
{
    Vector<String> list = (Vector<String>) request.getAttribute("hellos");
%>

    <div class="title-form"> Enregistrer un employé </div>
    <form action="get-emp-by-id.do" method="get" id="form-register">
      <div class="list-input">
        <div class="row-form">
          <div class="label-form">Nom :</div>

          <select name="id" id="h">
            <%
            for (int i = 0; i < list.size(); i++)
            {
             %>
             <option value="<%out.print(i);%>"> <% out.print(list.get(i)+"</br>"); %>  </option>
            <%
              } 
            %>
          </select>

        </div>
        <div class="row-form">
          <div class="label-form">Age :</div>
          <input type="number" placeholder="00" name="age">
        </div>
        <div class="row-form">
          <div class="label-form">Salaire :</div>
          <input type="number" placeholder="0000" name="salary">
        </div>
        <div class="row-form">
          <button class="btn_validate">Valider</button> 
        </div>

       <% if(request.getAttribute("counter") != null)
          { %>
        <div class="row-form">
          <div class="counter">Counter Instance appelé : <div class="value-counter"> <% out.println(request.getAttribute("counter"));
            out.println(request.getAttribute("emp"));
            %></div></div>
        </div>
        <% } %>

   
      </div>
    </form>
  <%  } 

  if(request.getAttribute("emp_target") != null)
  { 
   Emp emp = (Emp)request.getAttribute("emp_target");
    %>
    <div class="title-form"> L'Employé <% out.print(emp.getName()+" "+emp.getAge()); %></div>
  <% 
  }
  
  %>
  </body>
</html>
