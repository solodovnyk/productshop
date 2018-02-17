<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<c:if test="${itemsQuantityInSearchResults > 1}">
        	<h3>По запросу "${keyword}" найдено ${itemsQuantityInSearchResults} товаров:</h3> 
	    </c:if><c:if test="${itemsQuantityInSearchResults == 1}">
	    	<h3>По запросу "${keyword}" найден всего один товар:</h3>
	    </c:if><c:if test="${itemsQuantityInSearchResults == 0}">
	    	<h3>По запросу "${keyword}" ничего не найдено.</h3>
	    </c:if><c:if test="${itemsQuantityInSearchResults == null}">
	    	<h3>Пожалуйста, введите запрос для поиска.</h3>
	    </c:if>
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
	              <div class="buy">
	                <div class="set-quantity">
	                  <button>-</button>
	                  <input type="text" value="1">
	                  <button>+</button>
	                </div>
	                <button class="put">В корзину</button>
	              </div>
	              <div class="product-code">Код товара: ${item.code}</div>
	            </a>
	          </li>
	      </c:forEach>
        </ul>
        <c:if test="${itemsQuantityInSearchResults > itemQuantity}">
        <c:if test="${itemsQuantityInSearchResults % itemQuantity == 0}">
        	<c:set var="pages" value="${itemsQuantityInSearchResults/itemQuantity}" />
        </c:if>
        <c:if test="${itemsQuantityInSearchResults % itemQuantity != 0}">
        	<c:set var="pages" value="${itemsQuantityInSearchResults/itemQuantity+1}" />
        </c:if>
          <nav aria-label="Page navigation">
            <ul class="pagination">
              <c:forEach var="i" begin="1" end="${pages}">
			    <c:if test="${i == pageNumber}">
				  <c:set var="className" value="class=\"active\"" />
				</c:if>
			    <li ${className}><a href="<c:url value="/search/find/${keyword}/1/${i}" />">${i}</a></li>
			  	<c:set var="className" value="" />
			  </c:forEach>
            </ul>
          </nav>
	    </c:if>