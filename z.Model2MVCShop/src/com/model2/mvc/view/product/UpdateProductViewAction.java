package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
//listProduct.jsp에서 
//if(menu.equals("manage")){ 수정하기페이지로
//<a href="/updateProductView.do?prodNo=<%=vo.getProdNo() %>&menu=<%=menu%>"><%=vo.getProdName() %></a> 
public class UpdateProductViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		ProductService service=new ProductServiceImpl();
		Product product=service.getProduct(prodNo);
		
		request.setAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}

}
