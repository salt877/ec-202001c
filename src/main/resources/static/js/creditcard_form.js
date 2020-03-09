$(function(){
	$(".pay-method").on("change", function(){
		var num = $("input[name='paymentMethod']:checked").val();
		if(num == '2'){
			$(".creditForm").hide();
		}else{
			$(".creditForm").show();
		}
	});
	
	$("#order-btn").on("click",function(){
		$.ajax({
			url:"http://153.126.174.131:8080/sample-credit-card-web-api/credit-card/payment", //送信先のWebAPIのURL
			dataType:"jsonp", //レスポンスデータ形式今回は最後にpをつける
			data:{  //リクエストパラメータ情報
				zipcode:$('#zipcode').val()
			},
			async:true //非同期で処理を行う
		}).done(function(data){
			//検索成功時にはページに結果を反映
			//コンソールに取得したJSONデータを表示
			console.dir(JSON.stringify(data));
			$("#address").val(data.items[0].address); //住所欄に住所をセット
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			//検索失敗時には、その旨をダイアログ表示しエラー情報をコンソールに記載
			alert('正しい結果を得られませんでした。');
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown:" + errorThrown.message);
		});
	});
	
});