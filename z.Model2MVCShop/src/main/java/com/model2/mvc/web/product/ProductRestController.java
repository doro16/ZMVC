package com.model2.mvc.web.product;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.sun.glass.ui.Menu;


@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductRestController(){
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
	
	
	
	@RequestMapping(value="json/addProduct", method=RequestMethod.POST)
	
	public Product addProduct(@RequestBody Product product) throws Exception {

		System.out.println("/product/json/addProduct : POST");		
		
		product.setManuDate(product.getManuDate().replace("-", ""));	
		productService.addProduct(product);		
		Product product1 = new Product();		
		return product1;
	}
					
	@RequestMapping(value="json/getProduct/{prodNo}", method=RequestMethod.GET)  
	public Product getProduct( @PathVariable("prodNo") int prodNo )throws Exception{
		
		System.out.println("/product/json/getProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		return product;
 
		
	}
	
	
	@RequestMapping(value="json/updateProduct", method=RequestMethod.POST)
	public Product updateProduct( @RequestBody Product product) throws Exception{

		System.out.println("/product/json/updateProduct : POST");
		//Business Logic
		productService.updateProduct(product);
		Product product1 = productService.getProduct(product.getProdNo());
		return product1;
		
	}
	//left
	@RequestMapping(value="json/listProduct", method=RequestMethod.GET)
	public Map listProduct( @RequestBody String menu) throws Exception{
		System.out.println("/product/json/listProduct : GET");
		
		Search search = new Search();
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		map.put("menu",menu);
		map.put("list", map.get("list"));
		map.put("resultPage", resultPage);
		map.put("search", search);
		
		return map;
	}
	
	//search
	@RequestMapping(value="json/listProduct", method=RequestMethod.POST)
	public Map listProduct( @RequestBody String menu, @RequestBody Search search) throws Exception{
		System.out.println("/product/json/listProduct : POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		map.put("menu",menu);
		map.put("list", map.get("list"));
		map.put("resultPage", resultPage);
		map.put("search", search);
		
		return map;
	}
}