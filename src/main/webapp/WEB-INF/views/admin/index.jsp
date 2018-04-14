<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
	  <div class="login">
	  <c:if test="${messages.successMessages.size() > 0}">
           <c:forEach var="message" items="${messages.successMessages}">
           	<div class="admin-msg"><p class="bg-success server_msg">${message}</p></div>
		</c:forEach>
	  </c:if>
	  <c:if test="${messages.errorMessages.size() > 0}">
           <c:forEach var="message" items="${messages.errorMessages}">
			<div class="admin-msg"><p class="bg-danger server_msg">${message}</p></div>
		</c:forEach>
	  </c:if>
        <form class="form-signin" action="<c:url value="/admin" />" method="post">
          <h2 class="form-signin-heading">Панель управления</h2>
          <div class="form-section">
            <input type="text" name="email" value="${email}" placeholder="Email" autofocus>
          </div>
          <div class="form-section">
            <input type="password" name="password" placeholder="Пароль">
          </div>
          <input type="submit" value="Войти">
        </form>
      </div>