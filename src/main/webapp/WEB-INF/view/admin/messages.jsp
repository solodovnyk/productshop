<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<table class="table table-striped">
              <tr>
                <th>#ID</th>
                <th>Имя</th>
                <th>Email</th>
                <th>Дата</th>
                <th></th>
                <th></th>
              </tr>
              <c:forEach var="message" items="${messages}">
              <tr>
                <td>#${message.id}</td>
                <td>${message.senderName}</td>
                <td>${message.senderEmail}</td>
                <td>${message.messageDate}</td>
                <td><a href="<c:url value="/admin/message/${message.id}" />">читать</a></td>
                <td><a href="<c:url value="/admin/deletemessage/${message.id}" />">удалить</a></td>
              </tr>
              </c:forEach>
            </table>