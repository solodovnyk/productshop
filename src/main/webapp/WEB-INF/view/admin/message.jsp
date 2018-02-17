<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<ol class="breadcrumb">
              <li><a href="<c:url value="/admin/messages" />">Сообщения</a></li>
              <li>Сообщение #${message.id}</li>
            </ol>
            <table class="table table-striped table-msg msg-table">
              <tr>
                <td>Имя:</td>
                <td>${message.senderName}</td>
              </tr>
              <tr>
                <td>Email:</td>
                <td>${message.senderEmail}</td>
              </tr>
              <tr>
                <td>Дата:</td>
                <td>${message.messageDate}</td>
              </tr>
              <tr>
                <td>Сообщение:</td>
                <td>
                  <p>${message.text}</p>
                </td>
              </tr>
            </table>