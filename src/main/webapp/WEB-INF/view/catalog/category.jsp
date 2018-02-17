<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
		<ol class="breadcrumb">
          <li>${category.name}</li>
        </ol>
        <ul class="subcategory-wrapper">
        <c:forEach var="subcategory" items="${category.subcategories}">
			<li>
        		<a href="<c:url value="/catalog/${category.slug}/${subcategory.slug}/1/1" />" class="subcategory">
            		<div class="subcategory-logo"><img src="<c:url value="/images/content/${subcategory.icon}" />" alt="${subcategory.name}"></div>
            		<span>${subcategory.name}</span>
          		</a>
        	</li>
		</c:forEach>
        </ul>