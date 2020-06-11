package com.model2.mvc.web.product;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
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
	
	
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public String addProduct() throws Exception {

		System.out.println("/product/addProduct : GET");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	//https://otamot.com/110 봐라
	public String addProduct(@ModelAttribute("product") Product product, 
							 MultipartHttpServletRequest request) throws Exception {

		System.out.println("/product/addProduct : POST");
		
		
		Map<String, MultipartFile> files = request.getFileMap();

	    CommonsMultipartFile cmf = (CommonsMultipartFile) files.get("file");
	    
	    String path ="C:/Users/User/git/ZMVC/z.Model2MVCShop/WebContent/images/uploadFiles"+cmf.getOriginalFilename();
	    //String path ="C:/Users/LG/git/ZMVC/z.Model2MVCShop/WebContent/images/uploadFiles"+cmf.getOriginalFilename();
	    File f = new File(path);
	    cmf.transferTo(f);
	    System.out.println("//////////"+ cmf.getOriginalFilename());
	    
	    product.setFileName(cmf.getOriginalFilename());
	   
		product.setManuDate(product.getManuDate().replace("-", ""));
		
		productService.addProduct(product);
		
		
		return "forward:/product/addProduct.jsp";
	}
					
	@RequestMapping(value="getProduct", method=RequestMethod.GET)  
	public String getProduct( @RequestParam("prodNo") int prodNo ,
							  @RequestParam(value="menu", required=false) String menu, Model model) throws Exception{
		
		System.out.println("/product/getProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);

		if(menu =="manage") {
			return "/product/updateProductView.jsp";
		} else {
			//System.out.println("파람 넘어가는가"+ menu);
			return "/product/getProduct.jsp";
			
		} 
		
	}
	//<a href="/updateProductView.do?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName}</a
	@RequestMapping(value="updateProduct", method=RequestMethod.GET)
	public String updateProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/product/updateProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct( @ModelAttribute("product") Product product , Model model) throws Exception{

		System.out.println("/product/updateProduct : POST");
		//Business Logic
		product.setManuDate(product.getManuDate().replace("-", ""));
		
		productService.updateProduct(product);
		
		model.addAttribute("menu", "manage");
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo();
	}
	
	@RequestMapping(value="listProduct")
	public String listProduct( @ModelAttribute("search") Search search , 
							   @RequestParam(value="menu", required=false) String menu, Model model) throws Exception{
		
		System.out.println("/product/listProduct : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("menu",menu);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		//rd아님?
		return "forward:/product/listProduct.jsp";
	}
	
}