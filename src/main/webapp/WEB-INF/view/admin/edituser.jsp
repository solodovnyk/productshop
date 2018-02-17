<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
		  <ol class="breadcrumb">
              <li><a href="<c:url value="/admin/users" />">Пользователи</a></li>
              <li>Изменение персональной информации пользователя</li>
            </ol>
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
            <form class="account-form" action="<c:url value="/admin/edituser/${user.id}" />" method="post">
              <div class="form-section">
                <input type="text" value="${user.name}" name="name" placeholder="Имя">
              </div>
              <div class="form-section">
                <input type="text" value="${user.surname}" name="surname" placeholder="Фамилия">
              </div>
              <div class="form-section">
                <input type="text" value="${user.phone}" name="phone" placeholder="Телефон">
              </div>
              <input type="submit" value="Изменить">
            </form>