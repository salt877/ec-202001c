$(function() {
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
	
	$("#error2").hide().css("color","red").css("font-weight","bold");
	
	$("#order-btn").on("click",function(){
		$.ajax({
			url:"http://192.168.2.108:8080/sample-credit-card-web-api/credit-card/payment", //送信先のWebAPIのURL
			dataType:"json", //レスポンスデータ形式
			data:{  //リクエストパラメータ情報
				user_id: $("#user_id").val(),
				order_number: $("#order_number").val(),
				amount: $("#amount").val(),
				card_number: $("#card_number").val(),
				card_exp_year: $("#card_exp_year").val(),
				card_exp_month: $("#card_exp_month").val(),
				card_name: $("#card_name").val(),
				card_cvv: $("#card_cvv").val()
			},
			async:false //同期処理を行う
		}).done(function(data){
			if($("#card_status").val(data.items[0].status) != "success"){
				$("#error2").show();
			}
			; 
		});
	});

});