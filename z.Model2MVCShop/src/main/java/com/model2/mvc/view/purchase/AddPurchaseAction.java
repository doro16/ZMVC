package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
//addPurchaseView.jspø°º≠
//document.addPurchase.submit();
public class AddPurchaseAction extends Action{
			
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		Product product=new Product();
		ProductService productService=new ProductServiceImpl();
		product=productService.getProduct(prodNo);
		request.setAttribute("product", product);
		
		String userId=request.getParameter("buyerId");
		
		User user=new User();
		UserService userUservice=new UserServiceImpl();
		user=userUservice.getUser(userId);
		request.setAttribute("user", user);
				
		Purchase purchase=new Purchase();
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(user.getUserName());
		purchase.setReceiverPhone(user.getPhone());
		purchase.setDivyAddr(user.getAddr());		
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setTranCode("1");	// ±∏∏≈øœ∑·∑Œ πŸ≤„¡‹!!!!!!!!!!!
		purchase.setDivyDate(request.getParameter("receiverDate"));

		PurchaseService purchaseService=new PurchaseServiceImpl();
		purchaseService.addPurchase(purchase);
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
