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
		<h2>Регистрация</h2>
        <form class="account-form" action="<c:url value="/account/registration" />" method="post">
        	<c:if test="${user != null}">
            	<c:set var="name" value="${user.name}"/>
            	<c:set var="surname" value="${user.surname}"/>
            	<c:set var="email" value="${user.email}"/>
            	<c:set var="phone" value="${user.phone}"/>
			</c:if>
        <div class="form-section">
            <input type="text" value="${name}" name="name" placeholder="Имя">
          </div>
          <div class="form-section">
            <input type="text" value="${surname}" name="surname" placeholder="Фамилия">
          </div>
          <div class="form-section">
            <input type="text" value="${email}" name="email" placeholder="Email">
          </div>
          <div class="form-section">
            <input type="text" value="${phone}" name="phone" placeholder="Телефон">
          </div>
          <div class="form-section">
            <input type="password" value="" name="password" placeholder="Пароль">
          </div>
          <div class="form-section">
            <input type="password" value="" name="confirm-password" placeholder="Повторите пароль">
          </div>
          <input type="submit"  value="Зарегистрироваться">
        </form>