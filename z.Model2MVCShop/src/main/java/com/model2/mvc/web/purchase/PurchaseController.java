package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;


@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	
	
	//setter Method 구현 않음
		
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	

	@RequestMapping("/addPurchaseView.do")
	public String addPurchaseView( @RequestParam("prodNo") int prodNo,
								  HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("/addPurchasetView.do");
		Product product = productService.getProduct(prodNo);
		
		request.setAttribute("product", product);
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@RequestMapping("/addPurchase.do")
	public String addPurchase(@RequestParam("prodNo") int prodNo,
							  @RequestParam("buyerId") String buyerId,
							  @ModelAttribute("purchase") Purchase purchase, Model model) throws Exception {

		System.out.println("/addPurchase.do");
		
		//purchase.setManuDate(product.getManuDate().replace("-", ""));
		User user = new User();
		user.setUserId(buyerId);
		Product product = new Product();
		//product.setProdNo(prodNo);
		product = productService.getProduct(prodNo);
		System.out.println(product);
		//User user = userService.getUser(user);
		
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		
		purchaseService.addPurchase(purchase);
		
		model.addAttribute("product", product);
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
	@RequestMapping("/getPurchase.do") 
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception{
		
		
		System.out.println("/getPurchase.do");
		//Business Logic
		
		ModelAndView modelAndView = new ModelAndView();
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
		
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updatePurchaseView( @RequestParam("tranNo") int tranNo) throws Exception{

		System.out.println("/updatePurchaseView.do");
		//Business Logic
		ModelAndView modelAndView = new ModelAndView();
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
	
		// Model 과 View 연결
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/purchase/updatePurchaseView.jsp");
		
		return modelAndView;
	}
	//POST잖아 다시해라 
	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase( @ModelAttribute("purchase") Purchase purchase, 
										@RequestParam("buyerId") String buyerId,
										@RequestParam("tranNo") int tranNo ) throws Exception{

		System.out.println("/updatePurchase.do");
		//Business Logic
		ModelAndView modelAndView = new ModelAndView();

		System.out.println(purchase.getTranNo());
		System.out.println("*******"+purchase.getBuyer());
		System.out.println("*******"+buyerId);
		User user = new User();
		user = userService.getUser(buyerId);
		purchase.setBuyer(user);
		purchaseService.updatePurchase(purchase);
		//purchase = purchaseService.getPurchase(purchase.getTranNo());
		purchase = purchaseService.getPurchase(tranNo);
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		
		return modelAndView;
	}

	@RequestMapping("/listPurchase.do")
	public ModelAndView  listPurchase( @ModelAttribute("search") Search search , HttpSession session) throws Exception{
		
		System.out.println("/listPurchase.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		// Business logic 수행		
		ModelAndView modelAndView = new ModelAndView();
		User user = (User)session.getAttribute("user");
		String buyerId = user.getUserId();
		
		Map<String, Object> map = purchaseService.getPurchaseList(search, buyerId);
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search",search);
		modelAndView.setViewName("/purchase/listPurchase.jsp");
		System.out.println("***********"+buyerId);
		
		return modelAndView;
	}
	//listPurchase.jsp에서 
	//<a href="//updateTranCodeByTran.do?tranNo=<%=purchaseVO.getTranNo()%>
	//								 &tranCode=3
	//								 &buyerId=<%=user.getUserId()%>">물건도착</a>

	@RequestMapping("/updateTranCodeByTran.do")
	public ModelAndView updateTranCodeByTran( @ModelAttribute("purchase") Purchase purchase
												) throws Exception{
		System.out.println("/updateTranCodeByTran.do");
		
		ModelAndView modelAndView = new ModelAndView();
		purchaseService.updateTranCodeByTran(purchase);
		
		modelAndView.setViewName("redirect:/listPurchase.do");
	
		
		return modelAndView;
	}
	// 구매완료 <a href="/updateTranCodeByProd.do?prodNo=${product.prodNo}
	//										  &tranCode=2">배송하기</a>
	@RequestMapping("/updateTranCodeByProd.do")
	public ModelAndView updateTranCodeByProd( 	@ModelAttribute("search") Search search ,
												@RequestParam("tranCode") String tranCode,
												@ModelAttribute("product") Product product,
												HttpSession httpSession
												) throws Exception{
		System.out.println("///////////////////updateTranCodeByProd.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		ModelAndView modelAndView = new ModelAndView();
		// Business logic 수행		
		product.setProTranCode(tranCode);
			
		Purchase purchase = new Purchase();
		
		purchase.setPurchaseProd(product);
		//System.out.println("//////////"+product.getProTranCode());
		//System.out.println("//////////////////////"+purchase.getPurchaseProd().getProTranCode());
		purchaseService.updateTranCodeByProd(purchase);
		
		
		Map<String, Object> map = productService.getProductList(search);
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search",search);
		modelAndView.setViewName("redirect:/listProduct.do?menu=manage");
	
		
		return modelAndView;
	}
}