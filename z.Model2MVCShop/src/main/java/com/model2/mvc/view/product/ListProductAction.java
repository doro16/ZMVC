package com.model2.mvc.view.product;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Search search=new Search();
		
		int currentPage=1;
		
		if(request.getParameter("currentPage") !=null) {
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("ListProductAction의 현재페이지 : "+currentPage);
		}
		
		search.setCurrentPage(currentPage);
	
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		//web.xml에  <context-param>으로 저장된 거 가져옴
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		ProductService service=new ProductServiceImpl();
		HashMap<String,Object> map=service.getProductList(search);
		
		Page resultPage=new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction resultPage::"+resultPage);
		request.setAttribute("list", map.get("list"));	//Product들
		request.setAttribute("resultPage", resultPage);	//현재페이지, 총게시물수, 5, 3
		request.setAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}

}
