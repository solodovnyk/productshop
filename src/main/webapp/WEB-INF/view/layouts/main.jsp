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
  <link href="<c:url value="/css/style.css" />" rel="stylesheet">
  <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'>
  <script src="<c:url value="/js/jquery-3.2.1.min.js" />"></script>
  <script src="<c:url value="/js/bootstrap.min.js" />"></script>
  <script src="<c:url value="/js/main.js" />"></script>
</head>
<body>
  <header>
    <div class="top-header">
      <div class="container">
        <div class="top-header-left">
          <div class="ask">
            <span></span>
            <a href="<c:url value="/first/messages" />">Задать вопрос</a>
          </div>
          <div class="delivery">
            <span></span>
            <a href="<c:url value="/first/delivery" />">Доставка</a>
          </div>
        </div>
        <div class="top-header-right">
        <c:if test="${authManager.isAuthenticated()}">
        	<div class="account">
	          <span></span>
	          <a href="<c:url value="/account" />">Личный кабинет</a>
	        </div>
	        <div class="login">
	          <span></span>
	          <a href="<c:url value="/account/out" />">Выход</a>
	        </div>
	    </c:if>
	    <c:if test="${!authManager.isAuthenticated()}">
        	<div class="login">
	          <span></span>
	          <a href="<c:url value="/account/login" />">Вход</a> |
              <a href="<c:url value="/account/registration" />">Регистрация</a>
	        </div>
	    </c:if>
          <div class="cart">
            <span></span>
            <a href="<c:url value="/cart" />">Корзина</a> (<b>${cookieManager.cookieQuantity}</b>)
          </div>
        </div>
      </div>
      <div class="clearfix"></div>
    </div>
    <div class="bottom-header">
      <div class="container">
        <div class="logo">
          <a href="<c:url value="/" />"></a>
        </div>
        <form class="search" action="<c:url value="/search" />" method="post">
          <input type="text" id="searchField" value="${keyword}" name="keyword" placeholder="Что вы хотите найти?">
          <input type="submit"  value="Найти">
          <small>например, <a id="keyword">кефир</a></small>
        </form>
        <div class="contacts">
          <div class="contact"><span class="first-part">&#x2706; ${adminPhone.substring(0,5)}</span> ${adminPhone.substring(6)}</div>
          <div class="contact"><span class="first-part">&#x2709; ${adminEmail.substring(0,3)}</span>${adminEmail.substring(3)}</div>
        </div>
      </div>
    </div>
  </header>
  <div class="main">
    <div class="container">
      <aside>
        <nav class="menu">
          <header>Каталог</header>
          <ul>
          	<c:forEach var="category" items="${allCategories}">
		      <li><a href="<c:url value="/catalog/${category.slug}" />">${category.name}</a></li>
		  	</c:forEach>
          </ul>
        </nav>
      </aside>
      <div class="content">
      	<jsp:include page="../${page}.jsp"/>
      </div>
      <div class="clearfix"></div>
    </div>
  </div>
  <footer>
    <div class="container">
      <div class="copy">${shopName} © ${year}</div>
      <div class="admin"><a href="<c:url value="/admin" />">Вход для администратора</a></div>
      <div class="clearfix"></div>
    </div>
  </footer>
</body>
</html>