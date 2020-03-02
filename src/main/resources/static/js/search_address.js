$(function() {
	  // ［検索］ボタンクリックで検索開始
	  $('#search').on("click",function() {
		  
	    $.ajax({
	        url:"http://zipcoda.net/api",
	        dataType:"jsonp",
	        data: { 
	          zipcode:$('#inputZipcode').val() 
	        },
			async:true
	    }).done(function(data) {
	    // 検索成功時にはページに結果を反映
	      // コンソールに取得データを表示
	      console.log(data);
	      $("#inputAddress").val(data.items[0].address);
	      
	    }).fail(function(XMLHttpRequest, textStatus, errorThrown){
			//検索失敗時には、その旨をダイアログ表示しエラー情報をコンソールに記載
	    	alert("正しい結果を得られませんでした。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown:" + errorThrown.message);
		});
	    
	  });
	});