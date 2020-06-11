<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="ko">
	
<head>
	<meta charset="EUC-KR">
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--   jQuery , Bootstrap CDN  -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
   
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
   <script type="text/javascript">

	</script>
	<!--  CSS �߰� : ���ٿ� ȭ�� ������ ���� �ذ� :  �ּ�ó�� ��, �� Ȯ��-->
	<style>
        body {
            padding-top : 70px;
        }
   	</style>
   	
     <!--  ///////////////////////// JavaScript ////////////////////////// -->
	 <script type="text/javascript">
	 $(function() { 
		 testUserList();
		 testWeather();
				
	 });	
	 function testWeather(){
		 
	        $.ajax({
	        	url : "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=6fb5170def605fc02b087103f4016766" ,
	            dataType: "json",
	            type: "GET",
	            async: "false",
	            success: function(JSONData) {
	                console.log(JSONData);
	                console.log("����µ� : "+ (JSONData.main.temp- 273.15) );
	                console.log("������� : "+ JSONData.main.humidity);
	                console.log("���� : "+ JSONData.weather[0].main );
	                console.log("�󼼳������� : "+ JSONData.weather[0].description );
	                console.log("���� �̹��� : "+ JSONData.weather[0].icon );
	                console.log("�ٶ�   : "+ JSONData.wind.speed );
	                console.log("����   : "+ JSONData.sys.country );
	                console.log("�����̸�  : "+ JSONData.name );
	                console.log("����  : "+ (JSONData.clouds.all) +"%" );     
	                
	                var dis = '<img src= "http://openweathermap.org/img/w/' + JSONData.weather[0].icon + '.png" alt="�����̹���">';

	                var nalssi = JSONData.weather[0].description;
	                var dis2 =  '<button type="button" class="btn btn-warning">�����ڵ� �ޱ�</button>';
	                
	                $("#rain").html(dis);
	                
	                if( nalssi.indexOf("clouds") != 1 ){
	                	$("#rain2").html(dis2);
	                }
	            }
	        });
	   
	   }
		 
		 

	 function testUserList(){
			var userId = $(this).next().val();
		
			$.ajax( 
					{
						url : "/product/json/listProduct" ,
						method : "GET" ,
						dataType : "json" ,
						headers : {
							"Accept" : "application/json",
							"Content-Type" : "application/json"
						},
						success : function(JSONData , status) {
							//alert("Server���� ���� ���� : \n"+"���̵� : "+JSON.stringify(JSONData.list[0])+"<br/>");
							//console.log("Server���� ���� ���� : \n"+"���̵� : "+JSON.stringify(JSONData.list[0])+"<br/>");
							
							
							 
							var members =  JSON.stringify(JSONData.list[0]);
							//List<Product> list =  objectMapper.readValue(jsonManyValue, new TypeReference<List<Product>>() {});
							
							//for (int i=0; i<list.size(); i++) {
							//	Product str = list.get(i);
							//	System.out.println(str);
							//}
							var displayValue = ""
							for (i=0; i < JSONData.list.length; i++){
								displayValue += '<div class="col-sm-6 col-md-4">'
						       +' <div class="thumbnail">'
						       +'  <img src="/images/uploadFiles/' + JSONData.list[i].fileName + '" width="242" height="220" alt="�����">'
						       +'   <div class="caption">'
						       +'     <h3>'+ JSONData.list[i].prodName +'</h3>'
						       +'     <p>'+ JSONData.list[i].prodDetail +'</p>'
						       +'     <p>'+ JSONData.list[i].price +'��</p>'
						       +'     <p><a href="/product/getProduct?prodNo='+  JSONData.list[i].prodNo  + '&menu=search" class="btn btn-warning" role="button">��ǰ����</a> </p>'
						       +'   </div>'
						       +' </div>'
						       +'</div>'
							;
							
							}

														
							//$("h6").remove();
							$( "#hh" ).html(displayValue);
						}
				});
				///////////////////////////////////////////////////////////////////////////////////////////
	 
	 }

	 
	 </script>
	
</head>
	
<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />

   	<!-- ToolBar End /////////////////////////////////////-->

	<!--  �Ʒ��� ������ http://getbootstrap.com/getting-started/  ���� -->	
   	<div class="container">
      <!-- Main jumbotron for a primary marketing message or call to action -->
        <span id = "rain"></span>&nbsp;&nbsp;
    	<span id = "rain2"></span>
    	
    		
    <br><br>	
    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
		  <!-- Indicators -->
		  <ol class="carousel-indicators">
		    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
		    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
		    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
		  </ol>
		
		  <!-- Wrapper for slides -->
		  <div class="carousel-inner" role="listbox">
		    <div class="item active">
		      <img src="/images/uploadFiles/bongbong.jpg" alt="���1">
		    </div>
		    <div class="item">
		      <img src="/images/uploadFiles/coffee.png" alt="���2">
		    </div>
		    <div class="item">
		      <img src="/images/uploadFiles/bingsu.png" alt="���3">
		    </div>
		  </div>
		
		  <!-- Controls -->
		  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
		    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>

		  </a>
		  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
		    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
		  </a>
	</div>
	<br><br>
	<!-- ���� : http://getbootstrap.com/css/   : container part..... -->
	<div class="container" >
        <div class="bs-example" data-example-id="thumbnails-with-custom-content">
    <div class="row" id="hh">
     
    </div>
    
  	</div>
  	 


</body>

</html>