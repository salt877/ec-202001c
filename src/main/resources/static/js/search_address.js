$(function() {
	$("#inputZipcode").on("keyup",function(){
		AjaxZip3.zip2addr('zipcode','','address','address');
	});
});