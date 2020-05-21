package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	//@Test
	public void testAddPurchase() throws Exception {
		Purchase purchase = new Purchase();
		
		User user = new User();
		user.setUserId("user03");
		purchase.setBuyer(user);
		
		Product product = new Product();
		product.setProdNo(10043);
		purchase.setPurchaseProd(product);
		
		purchase.setDivyRequest("������� �������ּ���");
		purchase.setReceiverPhone("01058264544");
		
		purchaseService.addPurchase(purchase);
		purchase = purchaseService.getPurchase(10055);

		//==> console Ȯ��
		//System.out.println(user);
		
		//==> API Ȯ�� 
		Assert.assertEquals("user03", purchase.getBuyer().getUserId());
		Assert.assertEquals(10043, purchase.getPurchaseProd().getProdNo());

	}
	
	@Test
	public void testGetPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		
		purchase = purchaseService.getPurchase(10054);

		
		//==> API Ȯ��
		Assert.assertEquals("user03", purchase.getBuyer().getUserId());
		Assert.assertEquals(10042, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("������� �������ּ���", purchase.getDivyRequest());
		Assert.assertEquals("01058264544", purchase.getReceiverPhone());
		
		Assert.assertNotNull(purchaseService.getPurchase(purchase.getTranNo()));
		//Assert.assertNotNull(purchaseService.getPurchase(10002));
	}
	
	@Test
	 public void testUpdatePurchase() throws Exception{
		 
		Product product = purchaseService.getPurchase(10012);
		Assert.assertNotNull(product);
		
		Assert.assertEquals("�������ƺ�ī��", product.getProdName());
		Assert.assertEquals("30cm", product.getProdDetail());
		Assert.assertEquals("20200528", product.getManuDate());
		Assert.assertEquals(6300, product.getPrice());

		product.setProdName("�������ƺ�ī��2");
		product.setProdDetail("15cm");
		product.setManuDate("20200614");
		product.setPrice(1300);
		
		productService.updateProduct(product);

		product = productService.getProduct(10012);
		Assert.assertNotNull(product);
		
		//==> console Ȯ��
		//System.out.println(user);
			
		//==> API Ȯ��
		Assert.assertEquals("�������ƺ�ī��2", product.getProdName());
		Assert.assertEquals("15cm", product.getProdDetail());
		Assert.assertEquals("20200614", product.getManuDate());
		Assert.assertEquals(1300, product.getPrice());
	 }
	 
	
	 //==>  �ּ��� Ǯ�� �����ϸ�....
	 //@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map =productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	//@Test
	 public void testGetProductListByProdNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10013");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis()); 
	 	search.setSearchKeyword("10013");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	*/
}