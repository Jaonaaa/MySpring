<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="/Test_Framework/assets/css/font.css" />
    <link rel="stylesheet" href="/Test_Framework/assets/css/index.css" />
    <title>Acceuil</title>
  </head>
  <body>
    <div id="root">
      <div class="middle-section">
        <div class="title-section">Vous voulez faire quoi ?</div>
        <div class="list-response">
          <div class="response-box">
            <div class="text-response">Voir détails d'un employé</div>
            <div class="detail-response" data-url="get-emp-by-id.do"></div>
          </div>
          <div class="response-box">
            <div class="text-response">Ajouter un employé</div>
            <div class="detail-response" data-url="add-emp.do"></div>
          </div>
          <div class="response-box">
            <div class="text-response">Voir liste des sessions</div>
            <div class="detail-response" data-url="checkSession.do"></div>
          </div>
          <div class="response-box">
            <div class="text-response">Se connecter ( admin )</div>
            <div class="detail-response" data-url="login.do"></div>
          </div>
          <div class="response-box">
            <div class="text-response">Se déconnecter</div>
            <div class="detail-response" data-url="logout.do"></div>
          </div>
          <div class="response-box">
            <div class="text-response">Voir page upload</div>
            <div class="detail-response" data-url="UploadPage.do"></div>
          </div>

          <div class="response-box">
            <div class="text-response">Voir Domaine page</div>
            <div class="detail-response" data-url="get-Domaine-page.do"></div>
          </div>

          <div class="response-box">
            <div class="text-response">View datas en Json</div>
            <div class="detail-response" data-url="view-to-json.do"></div>
          </div>

          <div class="response-box">
            <div class="text-response">Valeur de retour en Json</div>
            <div class="detail-response" data-url="return-to-json.do"></div>
          </div>

          <div class="response-box">
            <div class="text-response">Supprime tous les Sessions</div>
            <div class="detail-response" data-url="remove_all_session.do"></div>
          </div>

          <div class="response-box">
            <div class="text-response">Supprime une Session</div>
            <div class="detail-response" data-url="remove_Session.do"></div>
          </div>
        </div>
      </div>
    </div>
    <!-- <form method="post" action="upload.do" enctype="multipart/form-data">
      Choose a file: <input type="file" name="empPicture" />
      <input type="submit" value="Upload" />
    </form> -->
    <script type="module" src="/Test_Framework/assets/js/index.js"></script>
  </body>
</html>
