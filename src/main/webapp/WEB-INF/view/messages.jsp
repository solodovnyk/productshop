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
		  	<c:if test="${message != null}">
	           <c:set var="name" value="${message.senderName}"/>
	           <c:set var="email" value="${message.senderEmail}"/>
			   <c:set var="text" value="${message.text}"/>
		    </c:if>
			<form class="account-form" action="<c:url value="/messages" />" method="post">
	          <div class="form-section">
	            <input type="text" value="${name}" name="name" placeholder="Имя">
	          </div>
	          <div class="form-section">
	            <input type="text" value="${email}" name="email" placeholder="Email">
	          </div>
	          <textarea class="form-control dev" rows="3" name="question" placeholder="Задайте свой вопрос">${text}</textarea>
	          <input type="submit"  value="Отправить">
	        </form>
		  
		  