package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
//getPurchase.jsp (구매상세조회)에서
//<a href="/updatePurchaseView.do?tranNo=<%=purchase.getTranNo()%>">수정</a>


public class UpdatePurchaseViewAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		Purchase purchase=purchaseService.getPurchase(tranNo);
		
		request.setAttribute("purchase", purchase);
		//구매정보수정 페이지로
		return "forward:/purchase/updatePurchaseView.jsp";
	}

}
