package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {
	
	public PurchaseDAO() {
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
				
		Connection con = DBUtil.getConnection();
		
		String sql="INSERT INTO transaction"
				+ " VALUES (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,SYSDATE,?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getTranCode());
		stmt.setString(9, purchase.getDivyDate());
		
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
		
	}
	
	public Purchase findPurchase(int tranNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql="SELECT * FROM transaction WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		
		Purchase purchase=null;
		
		while(rs.next()) {
			
			User user=new User();
			UserService userUservice=new UserServiceImpl();
		//  인터페이스		                                  구현체 -> userDao=new UserDao() 생성됨	
			user=userUservice.getUser(rs.getString("BUYER_ID"));
			
			Product product=new Product();
			ProductService productService=new ProductServiceImpl();
			product=productService.getProduct(rs.getInt("PROD_NO"));
						
			purchase=new Purchase();
			purchase.setBuyer(user);
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setOrderDate(rs.getDate("ORDER_DATA"));
			purchase.setPaymentOption((rs.getString("payment_option")));
			purchase.setPurchaseProd(product);
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			purchase.setTranNo(rs.getInt("tran_no"));	
		}
		
		con.close();
		
		
		return purchase;
	}
	
	public HashMap<String, Object> getPurchaseList(Search search, String buyerId) throws Exception{
		
		HashMap<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		//buyerId가 구매한거 다 보여줘
		String sql="SELECT * FROM transaction WHERE buyer_id='"+buyerId+"'";
		//==> totalCount =  buyer_id로 검색했을때 만족하는 결과 수 count(*)
		int totalCount = this.getTotalCount(sql);

		//==> CurrentPage 게시물만 받도록 Query 다시구성
		// buyer_id로 검색했을때 만족하는 결과 근데 rownum으로 조건줘서 현재페이지 것만 나옴
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println("rownum으로 조건줘서 현재페이지 것만 나옴: "+search);

		List<Purchase> list = new ArrayList<Purchase>();

		while(rs.next()) {
			
			User user=new User();
			UserService userUservice=new UserServiceImpl();
			user=userUservice.getUser(buyerId);
			// 	 userDAO.findUser(userId) 인자로 받아온 buyerId를 이용
	

			Product product=new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			ProductService productService=new ProductServiceImpl();
			product=productService.getProduct(product.getProdNo());
			//		transaction 테이블의 prod_no값으로 product의 prod_no 셋팅

			Purchase purchase=new Purchase();
			purchase.setBuyer(user);
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setPurchaseProd(product);
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			list.add(purchase);
			
			System.out.println("getPurchaseList: "+purchase);
		}
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount)); // 총 게시물 수 
		map.put("list", list); // list : 현재페이지에서 보여지는 purchase들
		//==> currentPage 의 게시물 정보 갖는 List 저장
		rs.close();
		pStmt.close();
		con.close();
		
		return map;
	}
	
	
//	구매정보수정
	public void updatePurchase(Purchase purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql="UPDATE transaction SET receiver_name=?, payment_option=?,"
				+ "receiver_phone=?, demailaddr=?, dlvy_request=?,"
				+ "dlvy_date=? WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getReceiverName());
		stmt.setString(2, purchase.getPaymentOption());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());
		stmt.executeUpdate();
		
		con.close();

	}
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	// SELECT COUNT(*)  
	// FROM (  SELECT * FROM transaction WHERE  buyer_id= 'user03' ) countTable; 
	//  COUNT(*)
	// ---------
    //    7
	private int getTotalCount(String sql) throws Exception {
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;	//buyer_id로 검색했을때 만족하는 결과 수
	}
	// 게시판 currentPage Row 만  return 
	//  SELECT * FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq  	
	//						FROM (	SELECT * FROM transaction WHERE buyer_id='user03' ) inner_table WHERE ROWNUM <=3 ) 	
	//						WHERE row_seq BETWEEN 1 AND 3
    // TRAN_NO    PROD_NO BUYER_ID   PAYMEN RECEIVER_NAME      RECEIVER_PHONE     DEMAILADDR     DLVY_REQUEST  TRAN_S ORDER_DA DLVY_DAT    ROW_SEQ
    // ---------- ---------- ---------------------------------------- ------ ---------------------------------------- ----------------------------
	//10063      10005 user03              1      SCOTT                        null                       null		1		 20/05/01  		1	                                 
	//10005      10010 user03              1      SCOTT                        null                       null		1		 20/04/28    	2                                 
	//10006      10010 user03              1      SCOTT                        null                       null		1 		 20/04/28    	3
	//이 sql은 현재페이지 결과 가져옴
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " + //1*3= rownum은 3
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("ROWNUM사용 makeCurrentPageSql "+ sql);	
		
		return sql;	// buyer_id로 검색한 결과 근데 rownum으로 조건줌
	}
	
	
	public void updateTranCode(Purchase purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		//listProduct.jsp
		//UpdateTranCodeByProdAction.java에서 시킴
		if(purchase.getPurchaseProd().getProTranCode().equals("2")) {
			
			String sql="UPDATE transaction SET TRAN_STATUS_CODE='2'"
					+ " WHERE prod_no=?";
		
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
			stmt.executeUpdate();
		//listPurchase.jsp
		//UpdateTranCodeActiton.java에서 시킴	
		//product에 있는 ProTranCode가 3이면 테이블에 있는 TRAN_STATUS_CODE 3으로 바꾸기 배송완료 
		}else if(purchase.getPurchaseProd().getProTranCode().equals("3")) {
			
			String sql="UPDATE transaction SET TRAN_STATUS_CODE='3'"
					+ " WHERE tran_no=?";
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, purchase.getTranNo());
			stmt.executeUpdate();
		}
		
		con.close();
	}

}
