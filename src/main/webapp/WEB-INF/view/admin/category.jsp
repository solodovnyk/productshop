<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
		  <ol class="breadcrumb">
            <li><a href="<c:url value="/admin/catalog" />">Каталог</a></li>
            <li>${category.name}</li>
          </ol>
          <div class="new-subcategory"><a class="btn btn-success new-category" href="<c:url value="/admin/addsubcategory/${category.slug}" />" role="button">Новая подкатегория</a></div>
            <div class="table-responsive">
              <table class="table table catalog-table">
                <thead>
                  <tr>
                    <th>Название</th>
                    <th>Иконка</th>
                    <th>Товары</th>
                    <th></th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                	<c:forEach var="subcategory" items="${category.subcategories}">
				      <tr>
                      	<td>${subcategory.name}</td>
	                    <td><img class="subcategory-logo" src="<c:url value="/images/content/${subcategory.icon}" />" alt=""></td>
	                    <td><span class="badge">${subcategory.itemQuantity}</span></td>
	                    <td><a href="<c:url value="/admin/editsubcategory/${subcategory.slug}" />">изменить</a></td>
	                    <td><a href="<c:url value="/admin/deletecategory/${subcategory.slug}" />">удалить</a></td>
                  	  </tr>
				  	</c:forEach>
                </tbody>
              </table>
            </div>