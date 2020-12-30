$(function() {
	  //全問題数をセッションストレージに保持
	  var totalquizNum = document.getElementById('totalquizNumber').textContent;
	  if(totalquizNum == null || totalquizNum == ''){
		  totalquizNum = sessionStorage.getItem('totalquizLocalNum');
	  }
	  sessionStorage.setItem('totalquizLocalNum',totalquizNum);
	  document.getElementById('totalquizNumView').innerHTML = totalquizNum ;

	  //現在の問題番号をセッションストレージに保持
	  var nowquizNum = document.getElementById('nowquizNumber').textContent;
	  if(nowquizNum == null || nowquizNum == ''){
		  nowquizNum = sessionStorage.getItem('nowquizLocalNum');
	  }
	  sessionStorage.setItem('nowquizLocalNum',nowquizNum);
	  document.getElementById('nowquizNumView').innerHTML = nowquizNum ;

	  //解答表示
	  $('.judge').addClass('show');
	  $('#seikai').addClass('show');
	  $('#huseikai').addClass('show');
});