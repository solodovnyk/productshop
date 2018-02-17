<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
		<ol class="breadcrumb">
          <li><a href="<c:url value="/catalog/${item.category.parent.slug}" />">${item.category.parent.name}</a></li>
          <li><a href="<c:url value="/catalog/${item.category.parent.slug}/${item.category.slug}/1/1" />">${item.category.name}</a></li>
          <li>${item.name}</li>
        </ol>
        <div class="item-info">
          <div class="item-general">
            <div class="photo"><img src="<c:url value="/images/content/${item.photo}" />" alt=""></div>
            <div class="action-panel">
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
            </div>
          </div>
          <div class="item-description">
            <h2>${item.name}</h2>
            <div class="product-code">Код товара: ${item.code}</div>
            <p>${item.description}</p>
          </div>
          <div class="clearfix"></div>
        </div>