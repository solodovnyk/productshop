<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
		  <ol class="breadcrumb">
            <li><a href="<c:url value="/admin/catalog" />">Каталог</a></li>
            <li>Добавление нового товара</li>
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
          <form class="account-form" action="<c:url value="/admin/additem" />" method="post" enctype="multipart/form-data">
            <div class="form-section">
              <input type="text" value="" name="name" placeholder="Название">
            </div>
            <div class="form-section">
              <select class="form-control" name="category">
                <option disabled selected="selected">ВЫБЕРИТЕ КАТЕГОРИЮ:</option>
                <c:forEach var="category" items="${categories}">
				  <option disabled>- ${category.name.toUpperCase()}</option>
				  <c:forEach var="subcategory" items="${category.subcategories}">
				    <option value="${subcategory.slug}">-- ${subcategory.name}</option>
			  	  </c:forEach>
			    </c:forEach>
             </select>
             </div>
            <div class="form-section">
              <div class="form-group">
                <label for="exampleInputFile">Укажите файл для фото товара</label>
                <input type="file" name="photo" id="exampleInputFile">
              </div>
           	</div>
            <div class="form-section">
              <input type="text" value="" name="price" placeholder="Цена">
            </div>
            <div class="form-section">
              <input type="text" value="" name="sale" placeholder="Скидка">
            </div>
            <div class="form-section">
              <textarea class="form-control dev" rows="3" name="description" placeholder="Описание"></textarea>
            </div>
            <input type="submit" value="Добавить">
          </form>