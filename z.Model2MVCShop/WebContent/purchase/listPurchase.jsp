<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page pageEncoding="EUC-KR"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- listPurchase.jsp (회원이 보는 구매목록 조회) --%>
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
   
   
   <!-- jQuery UI toolTip 사용 CSS-->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <!-- jQuery UI toolTip 사용 JS-->
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
	  body {
            padding-top : 50px;
        }
    </style>
    
     <!--  ///////////////////////// JavaScript ////////////////////////// -->
	<script type="text/javascript">
	function fncGetUserList(currentPage) {
		$("#currentPage").val(currentPage);
		$("form").attr("method", "POST").attr("action", "/purchase/listPurchase?buyerId=${param.buyerId}").submit();

	}
	
	$(function() {
		 	
		$( ".ct_list_pop td:nth-child(3)" ).css("color" , "red");
		$("h7").css("color" , "red");
		
			
		$(".ct_list_pop:nth-child(4n+6)" ).css("background-color" , "whitesmoke");

	});
</script>
</head>

<body>

<jsp:include page="/layout/toolbar.jsp" />
										
<form name="detailForm">

<div class="container">
	
		<div class="page-header text-info">
	       	 <h3>구매목록조회</h3>
	    </div>

	<div class="row">
	    
	    <div class="col-md-6 text-left">
	    	<p class="text-primary">
	    		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
	    	</p>
	    </div>
	</div>	
		
	<table class="table table-hover table-striped" >	
		<thead>
	          <tr>
	            <th align="center">No</th>
	            <th align="left" >회원ID</th>
	            <th align="left">주문자</th>
	            <th align="left">전화번호</th>
	            <th align="left">배송현황</th>
	            <th align="left">정보수정</th>
	          </tr>
	    </thead>
       
		<tbody>

	
		<c:set var="i" value="0" />
		<c:forEach var="purchase" items="${list}">
			<c:set var="i" value="${ i+1 }" />
			<tr class="ct_list_pop">
				<td align="center">		
				<a href="/purchase/getPurchase?tranNo=${purchase.tranNo}">${ i }</a></td>
				<td align="left">
				<a href="/user/getUser?userId=${purchase.buyer.userId}">${purchase.buyer.userId}</a></td>
				<td align="left">${purchase.receiverName}</td>
				<td align="left">${purchase.receiverPhone}</td>
				<td align="left">
					<c:if test="${ fn:contains(purchase.tranCode, '1') }"> 현재 구매완료 상태입니다.</c:if>
					<c:if test="${ fn:contains(purchase.tranCode, '2') }"> 현재 배송중 상태입니다.</c:if>
					<c:if test="${ fn:contains(purchase.tranCode, '3') }"> 현재 배송완료 상태입니다. </c:if>
				</td>	
				<td align="left">
					
					<c:if test="${ fn:contains(purchase.tranCode, '2') }">
					<a href="/purchase/updateTranCodeByTran?tranNo=${purchase.tranNo}&tranCode=3&buyerId=${user.userId}">
					물건도착</a>
					</c:if>
				</td>
				</tr>
          </c:forEach>
        
       	  </tbody>
      
      </table>	

<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
		<jsp:include page="../common/pageNavigator.jsp"/>				
    	</td>
	</tr>
</table>
<!-- PageNavigation End... -->

</form>
</div>

</body>
</html>
