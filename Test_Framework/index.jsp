<%@ page language="java" contentType="text/html"
pageEncoding="UTF-8" %>
<% 

if(request.getParameter("content")!=null)
{
    out.print("{ "+request.getParameter("content")+"}");

}
out.println("Index Page");
%>