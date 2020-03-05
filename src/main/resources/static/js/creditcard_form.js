$(function() {
	$("#payMethod").on("change", function() { // 選択肢変更時
		if ($('input[name=paymentMethod]:checked').val() == "2") {
			$("#creditForm").hide();
		}else{
			$("#creditForm").show();
		}
	});
});