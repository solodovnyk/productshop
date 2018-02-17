<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<img src="<c:url value="/resources/images/big-img.png" />" class="big-img" alt="">
        <h2>Новые товары</h2>
        <ul class="item-wrapper">
          <c:forEach var="item" items="${lastItems}">
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
            </a>
          </li>
          </c:forEach>
        </ul>
        <h2>Акционные товары</h2>
        <ul class="item-wrapper">
          <c:forEach var="item" items="${discountItems}">
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
            </a>
          </li>
          </c:forEach>
        </ul>