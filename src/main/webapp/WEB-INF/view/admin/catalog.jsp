<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		  <ul id="account-sections" class="nav nav-tabs" role="tablist">
            <li role="categories" class="active">
              <a href="#categories" aria-controls="categories" role="tab" data-toggle="tab">Категории</a>
            </li>
            <li role="presentation">
              <a href="#goods" aria-controls="goods" role="tab" data-toggle="tab">Товары</a>
            </li>
          </ul>
          <div class="tab-content">
            <div role="tabpanel" class="tab-pane fade in active" id="categories">
              <div class="new-category"><a class="btn btn-success new-category" href="<c:url value="/admin/addcategory" />">Новая категория</a></div>
              <div class="table-responsive">
                <table class="table table-striped catalog-table">
                  <thead>
                    <tr>
                      <th>Название</th>
                      <th>Товары</th>
                      <th></th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="category" items="${categories}">
				      <tr>
                        <td><a href="<c:url value="/admin/category/${category.slug}" />">${category.name}</a></td>
                        <td><span class="badge">${category.itemQuantity}</span></td>
                        <td><a href="<c:url value="/admin/editcategory/${category.slug}" />">изменить</a></td>
                        <td><a href="<c:url value="/admin/deletecategory/${category.slug}" />">удалить</a></td>
                      </tr>
				  	</c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
            <div role="tabpanel" class="tab-pane fade" id="goods">
              <div class="category-choice">
                <div class="new-category"><a class="btn btn-success new-category" href="<c:url value="/admin/additem" />" role="button">Добавить товар</a></div>
                <div class="clearfix"></div>
              </div>
              <div class="table-responsive">
                <table class="table table catalog-table">
                  <thead>
                    <tr>
                      <th>Название</th>
                      <th>Код товара</th>
                      <th>Фото</th>
                      <th>Цена</th>
                      <th>Скидка %</th>
                      <th></th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                  	<c:forEach var="item" items="${items}">
				      <tr>
                        <td>${item.name}</td>
                        <td>${item.code}</td>
                        <td><img class="item-logo" src="<c:url value="/images/content/${item.photo}" />" alt=""></td>
                        <td>${item.price}</td>
                        <td>${item.sale.intValue()}</td>
                        <td><a href="<c:url value="/admin/edititem/${item.code}" />">изменить</a></td>
                        <td><a href="<c:url value="/admin/deleteitem/${item.code}" />">удалить</a></td>
                      </tr>
				  	</c:forEach>
                  </tbody>
                </table>
                <c:if test="${category.subcategories.size() > itemQuantity}">
	              <nav aria-label="Page navigation">
	                <ul class="pagination">
	                  <li>
	                    <a href="#" aria-label="Previous">
	                      <span aria-hidden="true">&laquo;</span>
	                    </a>
	                  </li>
	                  <c:forEach var="i" begin="1" end="${category.subcategories.size()/itemQuantity+1}">
					    <c:if test="${i == pageNumber}">
						  <c:set var="className" value="class=\"active\"" />
						</c:if>
					    <li ${className}><a href="<c:url value="/admin/category/${category.slug}/${i}" />">${i}</a></li>
					  	<c:set var="className" value="" />
					  </c:forEach>
	                  <li>
	                    <a href="#" aria-label="Next">
	                      <span aria-hidden="true">&raquo;</span>
	                    </a>
	                  </li>
	                </ul>
	              </nav>
			    </c:if>
              </div>
            </div>
          </div>