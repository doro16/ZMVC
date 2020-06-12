<%@ page contentType="text/html; charset=EUC-KR" %>
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
	    <!--  ///////////////////////// JavaScript ////////////////////////// -->
		<script type="text/javascript">
			//============= "구매내역"  Event 연결 =============
			$(function() {
				$( "button:contains('확인')" ).on("click" , function() {
					self.location = "/product/listProduct?menu=search"
				})

			});	
	
				
		</script>
	    
	</head>

	<body>

		<!-- ToolBar Start /////////////////////////////////////-->
		<jsp:include page="/layout/toolbar.jsp" />
	   	<!-- ToolBar End /////////////////////////////////////-->
	   	
		<!--  화면구성 div Start /////////////////////////////////////-->
		<div class="container">
		
			<div class="page-header">
		       <h3 class=" text-info">구 매 완 료</h3>
		       <h5 class="text-muted">구매 정보를 확인하세요.</h5>
		    </div>
			
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
			<div class="row">
		  		<div class="col-xs-4 col-md-2 my"><strong>구매자 아이디</strong></div>
				<div class="col-xs-8 col-md-4">&nbsp;${user.userId}</div>
			</div>
			<hr/>
			<div class="row">
		  		<div class="col-xs-4 col-md-2 my" ><strong>구매방법</strong></div>
				<div class="col-xs-8 col-md-4">
					<c:if test="${purchase.paymentOption == 1}">
						현금구매
					</c:if>
					<c:if test="${purchase.paymentOption == 2}">
						신용구매
					</c:if>
				</div>
			</div>
			
			<hr/>


			<div class="row">
		  		<div class="col-xs-4 col-md-2 my"><strong>구매자이름</strong></div>
				<div class="col-xs-8 col-md-4">${purchase.receiverName}</div>
			</div>
			
			<hr/>

			<div class="row">
		  		<div class="col-xs-4 col-md-2 my"><strong>구매자연락처</strong></div>
				<div class="col-xs-8 col-md-4">${purchase.receiverPhone}</div>
			</div>
			
			<hr/>

			<div class="row">
		  		<div class="col-xs-4 col-md-2 my"><strong>구매자주소</strong></div>
				<div class="col-xs-8 col-md-4">${purchase.divyAddr}</div>
			</div>
			
			<hr/>

			<div class="row">
		  		<div class="col-xs-4 col-md-2 my"><strong>구매요청사항</strong></div>
				<div class="col-xs-8 col-md-4">${purchase.divyRequest}</div>
			</div>
			
			<hr/>

			<div class="row">
		  		<div class="col-xs-4 col-md-2 my"><strong>배송희망일자</strong></div>
				<div class="col-xs-8 col-md-4">${purchase.divyDate}</div>
			</div>
			
			<hr/>

			<div class="row">
		  		<div class="col-md-12 text-right ">
		  			<button type="button" class="btn btn-primary">확인</button>

		  		</div>
			</div>
		
			<br/>
			
		</div>
	</body>
</html>