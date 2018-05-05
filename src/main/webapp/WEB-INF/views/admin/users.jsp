<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<table class="table table-striped">
              <thead>
                <tr>
                  <th>Имя</th>
                  <th>Фамилия</th>
                  <th>Email</th>
                  <th>Телефон</th>
                  <th></th>
                  <th></th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
              	<c:forEach var="user" items="${users}">
                <tr>
                  <td>${user.name}</td>
                  <td>${user.surname}</td>
                  <td>${user.email}</td>
                  <td>${user.phone}</td>
                  <td><a href="<c:url value="/admin/userinfo/${user.id}" />">информация</a></td>
                  <td><a href="<c:url value="/admin/edituser/${user.id}" />">редактировать</a></td>
                  <td><a href="<c:url value="/admin/deleteuser/${user.id}" />">удалить</a></td>
                </tr>
                </c:forEach>
              </tbody>
            </table>