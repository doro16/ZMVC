package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class UpdatePurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
	
		Purchase purchase=new Purchase();
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));	
		purchase.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		purchaseService.updatePurchase(purchase);
		//리턴값이 없기때문에 
		//request.setAttribute("purchase", purchase); 이게 안됨
		//return "forward:/purchase/getPurchase.jsp"; 은 안됨 update는 됐는데 상세조회를 못가져옴
		//getPurchase.jsp를 부르려면 먼저 GetPurchaseAction를 수행해야 함. find해서 리턴한  purchase값을 세팅
		return "redirect:/getPurchase.do?tranNo="+tranNo;
	}
	
}
