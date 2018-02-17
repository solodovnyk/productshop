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
		<c:if test="${productPositions.size() <= 0}">
	      <p class="bg-info server_msg cart_msg">Ваша корзина пуста</p>
		</c:if>
		<c:if test="${productPositions.size() > 0}">
		  <form class="my-cart" method="post" action="<c:url value="/cart/addorder" />">
          <table class="table table-striped">
            <tr class="active">
              <th>Наименование</th>
              <th>Количество</th>
              <th>Стоимость</th>
              <th></th>
            </tr>
            <c:forEach var="position" items="${productPositions}">
       	    <tr>
               <td><a href="<c:url value="/catalog/${position.item.code}" />">${position.item.name}</a></td>
               <td>
                 <div class="buy">
                    <div class="set-quantity">
	                  <button class="subtract">-</button>
	                  <input type="text" class="itemQuantity" name="quantity" value="${position.quantity}">
	                  <button class="add">+</button>
	                </div>
                 </div>
               </td>
               <td class="cart-price">${position.item.finalPrice}</td>
               <td><a href="<c:url value="/cart/delete/${position.item.id}" />">удалить</a></td>
          	</tr>
	        </c:forEach>
          </table>
          <footer>
            <div class="total">Сумма: <span>0.0</span> грн</div>
            <input type="submit" class="btn to-order" value="Заказать" />
          </footer>
        </form>
       </c:if>