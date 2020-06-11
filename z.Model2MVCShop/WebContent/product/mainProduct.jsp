<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
	<script type="text/javascript">
	//윈도우켜지자마자
	$(function() {
		fncGetUserList(1);
		console.log("켜졌냐");
		
	});

	
	function fncGetUserList(currentPage) {
		$("#currentPage").val(currentPage);
		$("form").attr("method", "POST").attr("action", "/product/listProduct?menu=${param.menu}").submit();	
	}
	
	 $(function() {
		 

		$( "td:nth-child(6) > i" ).on("click" , function() {
			var array = $(this).text().split(",");
			console.log("냐냐"+array);
			prodName = array[0].trim();
			prodNo = array[1].trim();
			proTranCode = array[2].trim();
			console.log("야야" +  array[0].trim() + "야야" + array[1].trim() + "야야" +array[2].trim() + "학"+ proTranCode );
			var menu = "${param.menu}";
			//console.log("야호" + menu );
			$.ajax(
				{
					url : "/product/json/getProduct/"+prodNo,
					method : "GET",
					dataType : "json",
					headers : {
						"Accept" : "application/json",
						"Content-Type" : "application/json",
					},
					success : function(JSONData, status) {
						
						var displayValue = "<h6>" 
								+ "상품명 : " +JSONData.prodName+ "<br/>"
								+ "상품상세정보 : " + JSONData.prodDetail+ "<br/>"
								+ "제조일자 : " +JSONData.manuDate+ "<br/>"
								+ "가격 : " + JSONData.price+ "<br/>"
								+"상품 이미지 : <img src='/images/uploadFiles/"+JSONData.fileName+"'><br/>"
								+"</h6>";
								
							
						if(menu == "manage"){				
							displayValue += "<h6>" + '<a href="/product/updateProduct?prodNo=' + JSONData.prodNo + '&menu='+menu+'">'
							+ "수정하기</a>" + "</h6>";
						} else if (menu == "search" && proTranCode == ''){ 
							displayValue += "<h6>" + '<a href="/product/getProduct?prodNo=' + JSONData.prodNo + '&menu='+menu+'">'
							+ "구매링크</a>" +"</h6>";
						} else if (menu == "search" && proTranCode != null){ 
							displayValue += "<h6>판매불가</h6>"; 
						} 
						
						
						$("h6").remove();
						$("#"+prodName+"").html(displayValue);
					}
					
				});
			
		});
		
		$( "td:nth-child(2)" ).on("click" , function() {
			var array = $(this).text().split(",");
			prodName = array[0].trim();
			prodNo = array[1].trim();
			proTranCode = array[2].trim();
			console.log("야야" +  array[0].trim() + "야야" + array[1].trim() + "야야" +array[2].trim() + "학"+ proTranCode );
			var menu = "${param.menu}"; 
			
			if(menu == "manage"){				
				self.location = "/product/updateProduct?prodNo=" + prodNo + "&menu=" +menu;
			} else if (menu == "search" && proTranCode == ''){ 
				self.location = "/product/getProduct?prodNo=" + prodNo + "&menu=" +menu;
			} else if (menu == "search" && proTranCode != null){ 
				
			} 
			
		});
		
		$( "td:nth-child(2)" ).css("color" , "red");
		
			
		

	});	
	
</script>
	
	
	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->
	
	<!--  화면구성 div Start /////////////////////////////////////-->
	<div class="container">
	
		<div class="page-header text-info">
	       <c:if test="${param.menu=='manage'}">
	       <h3>상품 관리</h3>
	       </c:if>
	       <c:if test="${param.menu=='search'}">
	       <h3>상품목록조회</h3>
	       </c:if>
	    </div>
	    
	    <!-- table 위쪽 검색 Start /////////////////////////////////////-->
	    <div class="row">
	    
		    <div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
		    	</p>
		    </div>
		    
		    <div class="col-md-6 text-right">
			    <form class="form-inline" name="detailForm">
			    
				  <div class="form-group">
				    <select class="form-control" name="searchCondition" >
						<option value="0" ${! empty search.searchCondition && search.searchCondition==0 ? "selected" : ""}>상품번호</option>
						<option value="1" ${! empty search.searchCondition && search.searchCondition==1 ? "selected" : ""}>상품명</option>
						<option value="2" ${! empty search.searchCondition && search.searchCondition==2 ? "selected" : ""}>상품가격</option>
					</select>
				  </div>
				  
				  <div class="form-group">
				    <label class="sr-only" for="searchKeyword">검색어</label>
				    <input type="text" class="form-control" id="searchKeyword" name="searchKeyword"  placeholder="검색어"
				    			 value="${! empty search.searchKeyword ? search.searchKeyword : '' }"  >
				  </div>
				  
				  <button type="button" class="btn btn-default">검색</button>
				  
				  <!-- PageNavigation 선택 페이지 값을 보내는 부분 -->
				  <input type="hidden" id="currentPage" name="currentPage" value=""/>
				  
				</form>
	    	</div>
	    	
		</div>
		<!-- table 위쪽 검색 Start /////////////////////////////////////-->
		
		
      <!--  table Start /////////////////////////////////////-->
      <table class="table table-hover table-striped" >
      
        <thead>
          <tr>
            <th align="center">No</th>
            <th align="left" >상품명</th>
            <th align="left">가격</th>
            <th align="left">등록일</th>
            <th align="left">현재상태</th>
            <th align="left">간략정보</th>
          </tr>
        </thead>
       
		<tbody>
		
		  <c:set var="i" value="0" />
		  <c:forEach var="product" items="${list}">
			<c:set var="i" value="${ i+1 }" />
			<tr class="ct_list_pop">
			  <td align="center">${ i }</td>
			  <td align="left"  title="Click : 상품정보 확인">
			  	${product.prodName}
				<div style= "display: none;">,${product.prodNo}</div>
				<div style= "display: none;">,${product.proTranCode}</div>
			  </td>	
			  <td align="left">${product.price}</td>
			  <td align="left">${product.regDate}</td>
			  <td align="left" id = "proTran">
				<c:choose>
					<%-- null 이면 --%>
					<c:when test="${ empty product.proTranCode }">판매중</c:when>
					
					<c:when test="${param.menu=='manage'}">
						<c:if test="${ fn:contains(product.proTranCode, '1') }">
							구매완료 <a href="/purchase/updateTranCodeByProd?prodNo=${product.prodNo}&tranCode=2">배송하기</a>
						</c:if>
						<c:if test="${ fn:contains(product.proTranCode, '2') }"> 배송중 </c:if>
						<c:if test="${ fn:contains(product.proTranCode, '3') }"> 배송완료 </c:if>		
					</c:when>
					
					<c:when test="${param.menu=='search' && user.role=='admin'}">
						<c:if test="${ fn:contains(product.proTranCode, '1') }"> 구매완료 </c:if>
						<c:if test="${ fn:contains(product.proTranCode, '2') }"> 배송중 </c:if>
						<c:if test="${ fn:contains(product.proTranCode, '3') }"> 배송완료 </c:if>			
					</c:when>
					
					<c:otherwise>
						재고없음
					</c:otherwise>	
				</c:choose>
			  </td>	
			  <td align="left"><!-- 여기 -->
				  	<i class="glyphicon glyphicon-ok" id= "${product.prodName}">
				  	<div style= "display: none;">${product.prodName}</div>
				  	<div style= "display: none;">,${product.prodNo}</div>
					<div style= "display: none;">,${product.proTranCode}</div>
					</i>
				  	<input type="hidden" value="${product.prodName}">
			  </td>
			</tr>
          </c:forEach>
        
        </tbody>
      
      </table>
	  <!--  table End /////////////////////////////////////-->
	  
 	</div>
 	<!--  화면구성 div End /////////////////////////////////////-->
 	
 	
 	<!-- PageNavigation Start... -->
	<jsp:include page="../common/pageNavigator_new.jsp"/>
	<!-- PageNavigation End... -->
	
