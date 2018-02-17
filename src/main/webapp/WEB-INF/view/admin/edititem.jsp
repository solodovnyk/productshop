<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
		  <ol class="breadcrumb">
            <li><a href="<c:url value="/admin/catalog" />">Каталог</a></li>
            <li>Изменение товара</li>
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
          <form class="account-form" action="<c:url value="/admin/edititem/${item.code}" />" method="post" enctype="multipart/form-data">
            <div class="form-section">
              <input type="text" value="${item.name}" name="name" placeholder="Название">
            </div>
            <div class="form-section">
              <img class="item-logo" src="<c:url value="/images/content/${item.photo}" />" alt="">
              <div class="form-group">
                <label for="exampleInputFile">Укажите файл для фото товара, если необходимо его заменить</label>
                <input type="file" name="photo" id="exampleInputFile">
              </div>
            </div>
            <div class="form-section">
              <input type="text" value="${item.price.doubleValue()}" name="price" placeholder="Цена">
            </div>
            <div class="form-section">
              <input type="text" value="${item.sale.intValue()}" name="sale" placeholder="Скидка">
            </div>
            <div class="form-section">
              <textarea class="form-control dev" rows="3" name="description" placeholder="Описание">${item.description}</textarea>
            </div>
            <input type="submit" value="Сохранить">
          </form>