<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		  <c:if test="${messages.successMessages.size() > 0}">
	           <c:forEach var="message" items="${messages.successMessages}">
				<p class="bg-success server_msg">${message}</p>
			</c:forEach>
		  </c:if>
		  <c:if test="${messages.errorMessages.size() > 0}">
	           <c:forEach var="message" items="${messages.errorMessages}">
				<p class="bg-danger server_msg">${message}</p>
			</c:forEach>
		  </c:if>
		  <h2>Вход в личный кабинет</h2>
        <form class="account-form" action="<c:url value="/account/login" />" method="post">
          <div class="form-section">
            <input type="text" value="${email}" name="email" placeholder="Email">
          </div>
          <div class="form-section">
            <input type="password" value="" name="password" placeholder="Пароль">
          </div>
          <input type="submit"  value="Войти">
        </form>