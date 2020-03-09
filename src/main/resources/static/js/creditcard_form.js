$(function() {
	$("#error_card").hide().css("color","red").css("font-weight","bold");
	$("#error_delivery_date").hide().css("color","red").css("font-weight","bold");
	
	$(".pay-method").on("change", function() {
		var num = $("input[name='paymentMethod']:checked").val();
		if (num == '2') {
			$(".creditForm").hide();
		} else {
			$(".creditForm").show();
		}
	});
	
	$("#bbb").on("click",function(){
		console.log($("#user_id").val());
		console.log($("#order_number").val());
		console.log($("#amount").val());
		console.log($("#card_number").val());
		console.log($("#card_exp_year").val());
		console.log($("#card_exp_month").val());
		console.log($("#card_name").val());
		console.log($("#card_cvv").val());
	});
	
	
	$("#order-btn").on("click",function(){
		var now = new Date();
		var threeDaysLater = now.setDate(now.getDate() + 3);
		var inputDate = $("#input_date").val();
		var inputTime = $("input[name='deliveryTime']:checked").val();
		$.ajax({
			url:"http://192.168.2.108:8080/sample-credit-card-web-api/credit-card/payment",
			dataType:"json",
			data:{
				user_id: $("#user_id").val(),
				order_number: $("#order_number").val(),
				amount: $("#amount").val(),
				card_number: $("#card_number").val(),
				card_exp_year: $("#card_exp_year").val(),
				card_exp_month: $("#card_exp_month").val(),
				card_name: $("#card_name").val(),
				card_cvv: $("#card_cvv").val()
			},
			async:false
		}).done(function(data){
			if($("#card_status").val(data.items[0].status) != "success"){
				$("#error_card").show();
			}
			if(threeDaysLater.getDate() < inputDate.getDate() || inputDate.getDate() < now.getDate()){
				$("#error_delivery_date").show();
			}
			if(inputDate.getDate() == now.getDate()){
				if((now.getMinutes() == 0 && inputTime - 1 < now.getHours()) 
						|| (now.getMinutes() != 0 && inputTime - 1 <= now.getHours())){
					$("#error_delivery_date").show();
				}
			}
			
		});
	});

});