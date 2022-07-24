<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<div>
          <ul id="account-sections" class="nav nav-tabs" role="tablist">
            <li role="info" class="active">
              <a href="#info" aria-controls="info" role="tab" data-toggle="tab">Общая информация</a>
            </li>
            <li role="presentation">
              <a href="#personal-data" aria-controls="personal-data" role="tab" data-toggle="tab">Личные данные</a>
            </li>
            <li role="presentation">
              <a href="#orders" aria-controls="orders" role="tab" data-toggle="tab">Мои заказы</a>
            </li>
          </ul>
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
          <div class="tab-content">
            <div role="tabpanel" class="tab-pane fade in active" id="info">
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
            </div>
            <div role="tabpanel" class="tab-pane fade" id="personal-data">
              <form class="account-form" action="<c:url value="/account/edituser" />" method="post">
                <h3>Изменение персональной информации</h3>
                <div class="form-section">
                  <input type="text" value="${user.name}" name="name" placeholder="Имя">
                </div>
                <div class="form-section">
                  <input type="text" value="${user.surname}" name="surname" placeholder="Фамилия">
                </div>
                <div class="form-section">
                  <input type="text" value="${user.phone}" name="phone" placeholder="Телефон">
                </div>
                <h3>Изменение пароля</h3>
                <div class="form-section">
                  <input type="text" value="" name="new-password" placeholder="Новый пароль">
                </div>
                <div class="form-section">
                  <input type="text" value="" name="confirm-new-password" placeholder="Повторите новый пароль">
                </div>
                <input type="submit"  value="Изменить">
              </form>
            </div>
            <div role="tabpanel" class="tab-pane fade" id="orders">
              <table class="table orders">
                <tr class="active">
                  <th>Номер заказа</th>
                  <th>Дата оформления</th>
                  <th>Статус</th>
                  <th>Сумма</th>
                </tr>
                <c:forEach var="order" items="${orders}">
                <tr data-toggle="modal" data-target="#order${order.id}">
                  <td>${order.id}</td>
                  <td>${order.orderDate}</td>
                  <c:if test="${order.orderStatusID == 1}">
			        <td data-status="1">оформлен</td>
			      </c:if>
			      <c:if test="${order.orderStatusID == 2}">
			        <td data-status="2">в обработке</td>
			      </c:if>
                  <c:if test="${order.orderStatusID == 3}">
			        <td data-status="3">выполнен</td>
			      </c:if>
                  <td>${order.totalPrice}</td>
                </tr>
                </c:forEach>
              </table>
            </div>
          </div>
        </div>
        
        <c:forEach var="order" items="${orders}">
        <!-- Modal -->
		  <div class="modal fade" id="order${order.id}" tabindex="-1" role="dialog" aria-labelledby="order">
		    <div class="modal-dialog #order${order.id}" role="document">
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		          <h4 class="modal-title" id="order">Заказ №${order.id} от ${order.orderDate}</h4>
		        </div>
		        <div class="modal-body">
		          <table class="table table-striped my-orders">
		            <tr class="active">
		              <th>Наименование</th>
		              <th>Количество</th>
		              <th>Стоимость</th>
		            </tr>
		            <c:forEach var="position" items="${order.productPositions}">
		            <tr>
		              <td><a href="#">${position.item.name}</a></td>
		              <td>${position.quantity}</td>
		              <td>${position.price.doubleValue()}</td>
		            </tr>
		            </c:forEach>
		          </table>
		          <div class="total">Сумма: ${order.totalPrice} грн</div>
		        </div>
		        <div class="modal-footer">
		          <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
		        </div>
		      </div>
		    </div>
		  </div>
		  </c:forEach>