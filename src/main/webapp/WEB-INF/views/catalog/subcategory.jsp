<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
		<ol class="breadcrumb">
          <li><a href="<c:url value="/catalog/${subcategory.parent.slug}" />">${subcategory.parent.name}</a></li>
          <li>${subcategory.name}</li>
        </ol>
        <div class="sort">Сортировать по цене: 
          <span><a href="<c:url value="/catalog/${subcategory.parent.slug}/${subcategory.slug}/2/1" />">низкая</a></span> | 
          <span><a href="<c:url value="/catalog/${subcategory.parent.slug}/${subcategory.slug}/3/1" />">высокая</a></span>
        </div>
        <ul class="item-wrapper">
          <c:forEach var="item" items="${items}">
	       	  <li>
	            <a href="<c:url value="/catalog/${item.code}" />" class="item">
	              <header>${item.name}</header>
	              <div class="item-logo"><img src="<c:url value="/images/content/${item.photo}" />" alt=""></div>
	              <div class="price-container">
	              	<c:if test="${item.sale == null || item.sale.intValue() <= 0}">
			        	<div class="price">
		                	<b>${item.price.intValue()}</b><span class="cents">${item.getPriceFraction()}</span> <span class="currency">грн</span>
		              	</div>
				    </c:if> <c:if test="${item.sale != null && item.sale.intValue() > 0}">
			        	<div class="price">
		                	<div class="price old-price">
			                  <b>${item.price.intValue()}</b><span class="cents">${item.getPriceFraction()}</span> <span class="currency">грн</span>
			                </div>
			                <div class="price new-price">
			                  <b>${item.finalPrice.intValue()}</b><span class="cents">${item.getFinalPriceFraction()}</span> <span class="currency">грн</span>
			                </div>
			                <div class="сanceled"></div>
		              	</div>
				    </c:if>
	              </div>
	              <form class="buy" action="<c:url value="/cart/add" />" method="post">
	                <div class="set-quantity">
	                  <button class="subtract">-</button>
	                  <input type="text" class="itemQuantity" name="quantity" value="1">
	                  <button class="add">+</button>
	                </div>
	                <input type="hidden" name="item-code" value="${item.code}" />
	                <input type="hidden" name="price" value="${item.finalPrice}" />
	                <input type="submit" class="put" value="В корзину" />
	              </form>
	              <div class="product-code">Код товара: ${item.code}</div>
	            </a>
	          </li>
	      </c:forEach>
        </ul>
        <c:if test="${itemsQuantityInCategory > itemQuantity}">
          <nav aria-label="Page navigation">
            <ul class="pagination">
              <c:forEach var="i" begin="1" end="${itemsQuantityInCategory/itemQuantity+1}">
			    <c:if test="${i == pageNumber}">
				  <c:set var="className" value="class=\"active\"" />
				</c:if>
			    <li ${className}><a href="<c:url value="/catalog/${subcategory.parent.slug}/${subcategory.slug}/1/${i}" />">${i}</a></li>
			  	<c:set var="className" value="" />
			  </c:forEach>
            </ul>
          </nav>
	    </c:if>