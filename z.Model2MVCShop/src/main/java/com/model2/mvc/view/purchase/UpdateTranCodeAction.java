package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

//listPurchase.jsp에서 (회원이 보는 구매목록 조회)
//<a href="/updateTranCode.do?tranNo=<%=purchaseVO.getTranNo()%>
//							 &tranCode=3
//							 &buyerId=<%=user.getUserId()%>">물건도착</a>

public class UpdateTranCodeAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		String tranCode=request.getParameter("tranCode");	
		String buyerId=request.getParameter("buyerId");
	
		Product product=new Product();
		product.setProTranCode(tranCode);
		
		Purchase purchase=new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setPurchaseProd(product);	
		//purchase에 저장한걸(TranCode) 테이블에서 바꿔줌
		PurchaseService purchaseService=new PurchaseServiceImpl();
		purchaseService.updateTranCode(purchase);
			
		return "redirect:/listPurchase.do?buyerId="+buyerId;
	}

}
