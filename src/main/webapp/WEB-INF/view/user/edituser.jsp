<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<div>
		<ol class="breadcrumb">
            <li><a href="<c:url value="/account" />">Личный кабинет</a></li>
            <li>Изменение персональной информации</li>
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
        </div>