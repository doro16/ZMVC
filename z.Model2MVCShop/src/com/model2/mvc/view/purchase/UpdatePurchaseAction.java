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
		//���ϰ��� ���⶧���� 
		//request.setAttribute("purchase", purchase); �̰� �ȵ�
		//return "forward:/purchase/getPurchase.jsp"; �� �ȵ� update�� �ƴµ� ����ȸ�� ��������
		//getPurchase.jsp�� �θ����� ���� GetPurchaseAction�� �����ؾ� ��. find�ؼ� ������  purchase���� ����
		return "redirect:/getPurchase.do?tranNo="+tranNo;
	}
	
}
