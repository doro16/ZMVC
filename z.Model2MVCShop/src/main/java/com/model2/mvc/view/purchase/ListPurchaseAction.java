package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
//left.jsp
//<a href="/listPurchase.do?buyerId=<%=vo.getUserId()%>"  target="rightFrame">구매이력조회</a>

//UpdateTranCodeAction.java
//return "redirect:/listPurchase.do?buyerId="+buyerId;
public class ListPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		String buyerId=request.getParameter("buyerId");		
		
		Search search=new Search();
		
		int currentPage=1;
		
		if(request.getParameter("currentPage") !=null) {
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		//web.xml에  <context-param>으로 저장된 거 가져옴
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));		//3
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));	//5
		search.setPageSize(pageSize);
		
		PurchaseService service=new PurchaseServiceImpl();
		HashMap<String,Object> map=service.getPurchaseList(search, buyerId);
																			//.intValue() Object -> int
		Page resultPage=new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
											
		request.setAttribute("list", map.get("list"));	//Purchase들
		request.setAttribute("resultPage", resultPage); //현재페이지, 총게시물수, 5, 3
		request.setAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
