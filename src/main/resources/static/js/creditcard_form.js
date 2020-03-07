$(function(){
	$(".pay-method").on("change", function(){
		var num = $("input[name='paymentMethod']:checked").val();
		if(num == '2'){
			$(".creditForm").hide();
		}else{
			$(".creditForm").show();
		}
	});
});