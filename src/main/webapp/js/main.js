$(document).ready(function() {
	sumPrices();
	
	$("#keyword").on("click", function(e) {
		$("#searchField").attr("value", e.currentTarget.innerHTML);
    });
	
	$(".add, .subtract").on("click", function(e) {
		var target = $(e.currentTarget);
		var quantityElem = target.siblings().filter(".itemQuantity");
		var startQuantity = quantityElem.attr("value")
		var operation = $(e.currentTarget).text();
		
		var finalPrice = (operation === "+") ? ++startQuantity : --startQuantity;
		if(finalPrice < 0) finalPrice = 0;
		
		quantityElem.attr("value", finalPrice);
		
		sumPrices();
		return false;
    });
	
	if($("#admin-menu").length > 0) {
		
		var menu = $("#admin-menu");
		var active = menu.attr("data-active");
		
		menu.children().each(function(index, elem) {
			if(index+1 == active) {
				$(elem).addClass("active");
			}
		});
		
		
	}
	
	if($("table.orders").length > 0) {
		$("table.orders td[data-status]").each(function(index, td) {
			status = $(td).attr("data-status");

			if(status == 2) {
				$(td).parent().addClass("warning");
			} else if(status == 3) {
				$(td).parent().addClass("success");
			}			
		});
	}
	
	if($(".order-status-select").length > 0) {
		$(".order-status-select").each(function(index, elem) {
			var status = $(elem).attr("data-ststus");
			
			$(elem).find("option").each(function(index, elem) {
				if(index+1 == status) {
					$(elem).attr("selected", "selected");
				}
			});
			
		}); 
	}
	
	
	
	
	function sumPrices() {
		if($(".total").length > 0) {
			var sum = 0;
			
			$(".cart-price").each(function(index, elem) {
				var itemQuantity = $(elem).parent().find(".itemQuantity").attr("value");
				sum += parseFloat($(elem).text()) * itemQuantity;
			});
			
			$(".total span").text(sum.toFixed(2));
		}
	}
	
});