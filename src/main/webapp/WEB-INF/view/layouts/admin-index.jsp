<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
  <head>
    <title>Панель администратора</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta charset="utf-8">
    <link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/css/general.css" />" rel="stylesheet">
 	<link href="<c:url value="/css/admin.css" />" rel="stylesheet">
   	<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'>
    <script src="<c:url value="/js/jquery-3.2.1.min.js" />"></script>
  	<script src="<c:url value="/js/bootstrap.min.js" />"></script>
  	<script src="<c:url value="/js/main.js" />"></script>
   </head>
   <body class="admin">
    <div class="container">
      <jsp:include page="${appInfo.viewPath}"/>
    </div>
  </body>
</html>