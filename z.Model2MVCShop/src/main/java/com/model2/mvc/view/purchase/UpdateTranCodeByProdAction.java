package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

//listProduct.jsp에서  (관리자가 보는 상품목록 조회)
//<a href="/updateTranCodeByProd.do?prodNo=<%=vo.getProdNo() %>
//								   &tranCode=2">배송하기</a>
public class UpdateTranCodeByProdAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		String tranCode=request.getParameter("tranCode");

		Product product = new Product();
		product.setProdNo(prodNo);		  
		product.setProTranCode(tranCode); 
		
		Purchase purchase = new Purchase(); 
		purchase.setPurchaseProd(product);
		//purchase에 저장한걸(TranCode) 테이블에서 바꿔줌
		PurchaseService purchaseService=new PurchaseServiceImpl();
		purchaseService.updateTranCode(purchase);
		
		return "redirect:/listProduct.do?menu=manage";
	}

}
