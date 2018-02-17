<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
		  <ol class="breadcrumb">
            <li><a href="<c:url value="/admin/catalog" />">Каталог</a></li>
            <li><a href="<c:url value="/admin/category/${categorySlug}" />">${categoryName}</a></li>
            <li>Добавление новой подкатегории</li>
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
         <form class="account-form" action="<c:url value="/admin/addsubcategory/${categorySlug}" />" method="post" enctype="multipart/form-data">
            <div class="form-section">
              <input type="text" value="" class="fieldBorderError" name="name" placeholder="Название">
           </div>
            <div class="form-section">
              <input type="text" value="" class="fieldBorderError" name="slug" placeholder="Алиас">
           </div>
            <div class="form-section">
              <div class="form-group">
                <label for="exampleInputFile">Укажите файл для иконки подкатегории</label>
                <input type="file" id="exampleInputFile" name="icon">
              </div>
           </div>
            <input type="submit" value="Добавить">
          </form>