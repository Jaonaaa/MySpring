<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %> <%
out.println("Index Page tyyyyyy"); %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Test</title>
  </head>
  <body>
    <form method="post" action="upload.do" enctype="multipart/form-data">
      Choose a file: <input type="file" name="empPicture" />
      <input type="submit" value="Upload" />
    </form>
  </body>
</html>
