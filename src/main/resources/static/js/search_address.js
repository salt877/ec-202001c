$(function(){
	$("#search").on("click", function(){
		AjaxZip3.zip2addr('zipcode', '', 'address', 'address');
	});
});