$(function() {
	$("#sample3select").on("change", function() { // 選択肢変更時
		if ($("#sample3select").val() == "1") {
			$("#triathlon").show();
			$("#other").hide();
	});
});