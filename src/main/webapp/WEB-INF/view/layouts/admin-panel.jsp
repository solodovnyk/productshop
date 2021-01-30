<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
  <head>
    <title>${title}</title>
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
   <body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <span class="navbar-brand admin">Панель администратора</span>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="<c:url value="/" />">Перейти в магазин</a></li>
            <li><a href="<c:url value="/admin/out" />">Выход</a></li>
          </ul>
          <form class="navbar-form navbar-right">
          </form>
        </div>
      </div>
    </nav>
    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <ul id="admin-menu" class="nav nav-sidebar" data-active="${menuActiveItem}">
            <li><a href="<c:url value="/admin/catalog" />">Каталог</a></li>
            <li><a href="<c:url value="/admin/users" />">Пользователи</a></li>
            <li><a href="<c:url value="/admin/orders" />">Заказы</a></li>
            <li><a href="<c:url value="/admin/messages" />">Сообщения</a></li>
          </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        	<div class="table-responsive">
        		<jsp:include page="${appInfo.viewPath}"/>
        	</div>
        </div>
      </div>
    </div>
  </body>
</html>