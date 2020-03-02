/*<![CDATA[*/
$(function() {
	calc_price();
	$(".size").on("change", function(){
		calc_price();
	});
	
	$(".checkbox").on("change", function(){
		calc_price();
	});
	
	$("#coffeenum").on("change", function(){
		calc_price();
	});
	
	
	function calc_price() {
		var size = $(".size:checked").val()
		var topping_count = $("#topping input:checkbox:checked").length;
		var coffee_num = $("#coffeenum option:selected").val();
		if(size == "M"){
			var size_price = 530; //リクエストスコープから値を取りたい
			var topping_price = 200 * topping_count;
		}else{
			var size_price = 650; //リクエストスコープから値を取りたい
			var topping_price = 300 * topping_count;
		}
		var price = (size_price + topping_price) * coffee_num;
		$("#totalprice").text(price.toLocaleString());
	};
});
/*]]>*/