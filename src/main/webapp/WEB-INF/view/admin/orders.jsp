<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<table class="table table-striped">
            <tr class="active">
              <th>Номер заказа</th>
              <th>Дата оформления</th>
              <th>Покупатель</th>
              <th>Статус</th>
              <th>Сумма</th>
              <th></th>
              <th></th>
            </tr>
            <c:forEach var="order" items="${orders}">
		     <tr>
		      <form action="<c:url value="/admin/changestatus" />" method="post">
		      <input type="hidden" name="order-id" value="${order.id}">
              <td>${order.id}</td>
              <td>${order.orderDate}</td>
              <td><a href="customer-info.html">${order.user.name}</a></td>
              <td>
              	<c:if test="${order.orderStatusID == 3}">
			      <span>выполнен</span>
				</c:if>
              	<c:if test="${order.orderStatusID != 3}">
			      <select class="form-control admin-orders order-status-select" data-ststus="${order.orderStatusID}" name="status">
                    <option value="1">новый</option>
                    <option value="2">в обработке</option>
                    <option value="3">выполнен</option>
                  </select>
				</c:if>
              <td>${order.getTotalPrice().doubleValue()}</td>
              <td><input type="submit" class="btn btn-default admin-orders" value="OK" /></td>
              </form>
             </tr>
		  	</c:forEach>
          </table>