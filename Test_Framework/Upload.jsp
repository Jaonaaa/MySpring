<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Upload</title>
    <link rel="stylesheet" href="./assets/css/font.css" />
    <link rel="stylesheet" href="./assets/css/index.css" />
    <link rel="stylesheet" href="./assets/css/upload.css" />
  </head>
  <body>
    <div id="root">
      <div class="middle-section">
        <div class="title-section">Uploader un fichier</div>
        <form
          method="post"
          action="upload.do"
          class="form-upload"
          enctype="multipart/form-data"
        >
          <label for="empPicture"> + </label>
          <input type="file" name="empPicture" id="empPicture" />
          <input type="submit" value="Upload" />
        </form>
      </div>
    </div>
  </body>
</html>
