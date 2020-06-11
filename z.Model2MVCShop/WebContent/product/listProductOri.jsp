<%@ page contentType="text/html; charset=EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<head>
<title>��ǰ �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	
	function fncGetUserList(currentPage) {
		$("#currentPage").val(currentPage);
		$("form").attr("method", "POST").attr("action", "/product/listProduct?menu=${param.menu}").submit();	
	}
	
	 $(function() {
		 

		$( "td.ct_btn01:contains('�˻�')" ).on("click" , function() {
			fncGetUserList(1);
		});
		
		
		$( ".ct_list_pop td:nth-child(3)" ).on("click" , function() {
			var array = $(this).text().split(",");
			prodName = array[0].trim();
			prodNo = array[1].trim();
			proTranCode = array[2].trim();
			console.log("�߾�" +  array[0].trim() + "�߾�" + array[1].trim() + "�߾�" +array[2].trim() + "��"+ proTranCode );
			var menu = "${param.menu}";
			//console.log("��ȣ" + menu );
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
						
						var displayValue = "<h3>" 
								+ "��ǰ�� : " +JSONData.prodName+ "<br/>"
								+ "��ǰ������ : " + JSONData.prodDetail+ "<br/>"
								+ "�������� : " +JSONData.manuDate+ "<br/>"
								+ "���� : " + JSONData.price+ "<br/>"
								+"</h3>";
								
							
						if(menu == "manage"){				
							displayValue += "<h3>" + '<a href="/product/updateProduct?prodNo=' + JSONData.prodNo + '&menu='+menu+'">'
							+ "�����ϱ�</a>" + "</h3>";
						} else if (menu == "search" && proTranCode == ''){ 
							displayValue += "<h3>" + '<a href="/product/getProduct?prodNo=' + JSONData.prodNo + '&menu='+menu+'">'
							+ "���Ÿ�ũ</a>" +"</h3>";
						} else if (menu == "search" && proTranCode != null){ 
							displayValue += "<h3>�ǸźҰ�</h3>"; 
						} 
						
						
						$("h3").remove();
						$("#"+prodName+"").html(displayValue);
					}
					
				});
			
		});
		
		
		
		$( ".ct_list_pop td:nth-child(3)" ).css("color" , "red");
		$("h7").css("color" , "red");
		
			
		$(".ct_list_pop:nth-child(4n+6)" ).css("background-color" , "whitesmoke");

	});	
	
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>

				<c:if test="${param.menu=='manage'}">
					<td width="93%" class="ct_ttl01">��ǰ ����	</td>
				</c:if>
				
				<c:if test="${param.menu=='search'}">
					<td width="93%" class="ct_ttl01">��ǰ �����ȸ</td>
				</c:if>		
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
		

				<option value="0" ${! empty search.searchCondition && search.searchCondition==0 ? "selected" : ""}>��ǰ��ȣ</option>
				<option value="1" ${! empty search.searchCondition && search.searchCondition==1 ? "selected" : ""}>��ǰ��</option>
				<option value="2" ${! empty search.searchCondition && search.searchCondition==2 ? "selected" : ""}>��ǰ����</option>
					
			</select>
			<input type="text" name="searchKeyword"  value="${! empty search.searchKeyword ? search.searchKeyword : ""}" 
			class="ct_input_g" style="width:200px; height:19px" />
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						�˻�
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >${resultPage.totalCount} �Ǽ�, ���� ${resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td>
			<td></td>
			<td align="left">
				${product.prodName}
				<div style= "display: none;">,${product.prodNo}</div>
				<div style= "display: none;">,${product.proTranCode}</div>
			</td>	
			<td></td>
			<td align="left">${product.price}</td>
			<td></td>
			<td align="left">${product.regDate}</td>
			<td></td>
			<td align="left" id = "proTran">
		<c:choose>
			<%-- null �̸� --%>
			<c:when test="${ empty product.proTranCode }">�Ǹ���</c:when>
			
			<c:when test="${param.menu=='manage'}">
				<c:if test="${ fn:contains(product.proTranCode, '1') }">
					���ſϷ� <a href="/purchase/updateTranCodeByProd?prodNo=${product.prodNo}&tranCode=2">����ϱ�</a>
				</c:if>
				<c:if test="${ fn:contains(product.proTranCode, '2') }"> ����� </c:if>
				<c:if test="${ fn:contains(product.proTranCode, '3') }"> ��ۿϷ� </c:if>		
			</c:when>
			
			<c:when test="${param.menu=='search' && user.role=='admin'}">
				<c:if test="${ fn:contains(product.proTranCode, '1') }"> ���ſϷ� </c:if>
				<c:if test="${ fn:contains(product.proTranCode, '2') }"> ����� </c:if>
				<c:if test="${ fn:contains(product.proTranCode, '3') }"> ��ۿϷ� </c:if>			
			</c:when>
			
			<c:otherwise>
				�������
			</c:otherwise>	
		</c:choose>
	
			</td>	
		</tr>
		<tr><!-- ����� -->
			<td id="${product.prodName}" colspan="11" bgcolor="D6D7D6" height="1"></td>
			<!-- 
			<c:if test="${param.menu=='manage'}"> <%-- ������ ������  �̸��� ���� ��ũ�ɾ� �����ϱ�--%>
			<a href="/product/updateProduct?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName}</a>
			</c:if>
			<c:if test="${param.menu=='search' && !empty product.proTranCode }"> <%-- �����ϱ� & �Ǹ��߾ƴϸ� ��ũx--%>
						${product.prodName}
			</c:if>
			<c:if test="${param.menu=='search' && empty product.proTranCode }"><%-- �����ϱ� & �Ǹ��߿��� ��ũ�ɱ� --%>
			<a href="/product/getProduct?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName}</a>
			</c:if>  -->
	</tr>			
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
		
			<jsp:include page="../common/pageNavigator.jsp"/>	
			
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->

</form>

</div>
</body>
</html>