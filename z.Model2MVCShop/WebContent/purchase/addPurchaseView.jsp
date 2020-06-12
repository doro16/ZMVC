<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="ko">
	
<head>
	<meta charset="EUC-KR">
	
	<!-- 참조 : http://getbootstrap.com/css/   참조 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
   
    <!-- Bootstrap Dropdown Hover JS -->
    <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
	<script type="text/javascript" src="../javascript/calendar.js"></script>
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
 		body {
            padding-top : 50px;
        }
        .my{
        	text-align : right;
        	margin-left: 190px;
        }
    </style>
     
	<script type="text/javascript">
	
	function fncAddPurchase() {
		$("form").attr("method", "POST").attr("action", "/purchase/addPurchase").submit();
	}
	
	$(function() {
		$( "button:contains('구매')" ).on("click" , function() {
			fncAddPurchase();
		});
		
		$( "button:contains('취소')" ).on("click" , function() {
			history.go(-1);
		});
	});
	
	</script>

</head>

<body>

	<jsp:include page="/layout/toolbar.jsp" />

	<!--  화면구성 div Start /////////////////////////////////////-->
	<div class="container">
	
		<h1 class="bg-primary text-center">상 품 구 매</h1>
		
		<!-- form Start /////////////////////////////////////-->
		<form class="form-horizontal" name="addPurchase" >
		<input type="hidden" name="prodNo" value="${product.prodNo}"/>
		<div class="row" >
	  		<div class="col-xs-4 col-md-2 my"><strong>상품번호</strong></div>
			<div class="col-xs-8 col-md-4">&nbsp;${product.prodNo}</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 my"><strong>상 품 명</strong></div>
			<div class="col-xs-8 col-md-4">&nbsp;${product.prodName}</div>
		</div>
		
		<hr/>
		

		<div class="row">
	  		<div class="col-xs-4 col-md-2 my"><strong>가격</strong></div>
			<div class="col-xs-8 col-md-4">&nbsp;${product.price}</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 my"><strong>등록일자</strong></div>
			<div class="col-xs-8 col-md-4">&nbsp;${product.regDate}</div>
		</div>
		
		<hr/> 
		  <div class="form-group">
		    <label for="buyerId" class="col-sm-offset-1 col-sm-3 control-label">구매자 아이디</label>
		    <div class="col-sm-4">
		      <input type="hidden" class="form-control" id="buyerId" name="buyerId" value="${user.userId}"/>
		      ${user.userId}
		    </div>
		  </div>
		<hr/> 
		  <div class="form-group">
		    <label for="paymentOption" class="col-sm-offset-1 col-sm-3 control-label">구매방법</label>
		    <div class="col-sm-4">
		    <select class="form-control"	name="paymentOption">
				<option value="1" selected="selected">현금구매</option>
				<option value="2">신용구매</option>
			</select>
		    </div>
		  </div>
		 <hr/>  
		  <div class="form-group">
		    <label for="receiverName" class="col-sm-offset-1 col-sm-3 control-label">구매자이름</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="receiverName" name="receiverName" value="${user.userName}" />
		    </div>
		  </div>
		  <hr/> 
		  <div class="form-group">
		    <label for="receiverPhone" class="col-sm-offset-1 col-sm-3 control-label">구매자연락처</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="receiverPhone" name="receiverPhone" >
		    </div>
		  </div>
		  <hr/> 
		  <div class="form-group">
		    <label for="divyAddr" class="col-sm-offset-1 col-sm-3 control-label">구매자주소</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="divyAddr" name="divyAddr">
		    </div>
		  </div>
		  <hr/> 
		  <div class="form-group">
		    <label for="divyRequest" class="col-sm-offset-1 col-sm-3 control-label">구매요청사항</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="divyRequest" name="divyRequest">
		    </div>
		  </div>
		  <hr/> 
		  <div class="form-group">
		    <label for="divyDate" class="col-sm-offset-1 col-sm-3 control-label">배송희망일자</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" readonly="readonly" id="divyDate" name="divyDate">
		      <img 	src="../images/ct_icon_date.gif" width="15" height="15" 
					onclick="show_calendar('document.addPurchase.divyDate', document.addPurchase.divyDate.value)"/>
		    </div>
		  </div>
		  <hr/> 
		 
		  
		  <div class="form-group">
		    <div class="col-sm-offset-4  col-sm-4 text-center">
		      <button type="button" class="btn btn-primary"  > 구매</button>
			  <a class="btn btn-primary btn" href="#" role="button">취소</a>
		    </div>
		  </div>
		</form>
		<!-- form Start /////////////////////////////////////-->
		
 	</div>
	<!--  화면구성 div end /////////////////////////////////////-->
	
</body>

</html>