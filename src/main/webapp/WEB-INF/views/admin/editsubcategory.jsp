<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
		  <ol class="breadcrumb">
            <li><a href="<c:url value="/admin/catalog" />">Каталог</a></li>
            <li><a href="<c:url value="/admin/category/${subcategory.parent.slug}" />">${subcategory.parent.name}</a></li>
            <li>Изменение подкатегории</li>
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
          <form class="account-form" action="<c:url value="/admin/editsubcategory/${subcategory.slug}" />" method="post" enctype="multipart/form-data">
            <div class="form-section">
              <input type="text" value="${subcategory.name}" name="name" placeholder="Название">
            </div>
            <div class="form-section">
              <input type="text" value="${subcategory.slug}" name="slug" placeholder="Алиас">
            </div>
            <div class="form-section">
              <img class="subcategory-logo" src="<c:url value="/images/content/${subcategory.icon}" />" alt="">
              <div class="form-group">
                <label for="exampleInputFile">Укажите файл для новой иконки, если необходимо ее заменить</label>
                <input type="file" id="exampleInputFile" name="icon">
              </div>
            </div>
            <input type="submit" value="Сохранить">
          </form>