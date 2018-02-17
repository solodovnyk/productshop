<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<ol class="breadcrumb">
              <li><a href="<c:url value="/admin/users" />">Пользователи</a></li>
              <li>Информация о пользователе</li>
            </ol>
            <table class="table table-striped">
              <tr>
                <td>Имя:</td>
                <td>${user.name}</td>
              </tr>
              <tr>
                <td>Фамилия:</td>
                <td>${user.surname}</td>
              </tr>
              <tr>
                <td>Email:</td>
                <td>${user.email}</td>
              </tr>
              <tr>
                <td>Телефон:</td>
                <td>${user.phone}</td>
              </tr>
              <tr>
                <td>Дата регистрации:</td>
                <td>${user.registrationDate}</td>
              </tr>
            </table>