$(function() {
	$("#error_card").hide().css("color","red").css("font-weight","bold");
	$("#error_delivery_date").hide().css("color","red").css("font-weight","bold");
	$("#error_name").hide().css("color","red").css("font-weight","bold");
	$("#error_email").hide().css("color","red").css("font-weight","bold");
	$("#error_zip").hide().css("color","red").css("font-weight","bold");
	$("#error_address").hide().css("color","red").css("font-weight","bold");
	$("#error_tel").hide().css("color","red").css("font-weight","bold");
	
	$(".pay-method").on("change", function() {
		var num = $("input[name='paymentMethod']:checked").val();
		if (num == '2') {
			$(".creditForm").hide();
		} else {
			$(".creditForm").show();
		}
	});
	
	$("#order-btn").on("submit",function(){
		var errorCount = 0;
		var now = new Date();
		var threeDaysLater = now.setDate(now.getDate() + 3);
		var inputDate = $("#input_date").val();
		var inputTime = $("input[name='deliveryTime']:checked").val();
		var notBlank = new Array("#destination_name", "#destination_email", "#destination_address");
		var notBlankError = new Array("#error_name", "#error_email", "#error_address");
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
				errorCount++;
			}
			if(threeDaysLater.getDate() < inputDate.getDate() || inputDate.getDate() < now.getDate()){
				$("#error_delivery_date").show();
				errorCount++;
			}
			if(inputDate.getDate() == now.getDate()){
				if((now.getMinutes() == 0 && inputTime - 1 < now.getHours()) 
						|| (now.getMinutes() != 0 && inputTime - 1 <= now.getHours())){
					$("#error_delivery_date").show();
					errorCount++;
				}
			}
			for(var i = 0; i < notBlank.length; i++){
				if($(notBlank[i]).val() == ""){
					$(notBlankError[i]).show();
					errorCount++;
				}
			}
			if(!($("#destination_zipcode").val().match(/^\d{7}$/))){
				$("#error_zip").show();
				errorCount++;
			}
			if(!($("#destination_tel").val().match(/^(0[5-9]0\d{8}|0[1-9][1-9]\d{7})$/))){
				$("#error_tel").show();
				errorCount++;
			}
			if(errorCount == 0){
				return true;
			}
			return false;
		});
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
});