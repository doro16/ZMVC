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
		//  �������̽�		                                  ����ü -> userDao=new UserDao() ������	
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
		//buyerId�� �����Ѱ� �� ������
		String sql="SELECT * FROM transaction WHERE buyer_id='"+buyerId+"'";
		//==> totalCount =  buyer_id�� �˻������� �����ϴ� ��� �� count(*)
		int totalCount = this.getTotalCount(sql);

		//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		// buyer_id�� �˻������� �����ϴ� ��� �ٵ� rownum���� �����༭ ���������� �͸� ����
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println("rownum���� �����༭ ���������� �͸� ����: "+search);

		List<Purchase> list = new ArrayList<Purchase>();

		while(rs.next()) {
			
			User user=new User();
			UserService userUservice=new UserServiceImpl();
			user=userUservice.getUser(buyerId);
			// 	 userDAO.findUser(userId) ���ڷ� �޾ƿ� buyerId�� �̿�
	

			Product product=new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			ProductService productService=new ProductServiceImpl();
			product=productService.getProduct(product.getProdNo());
			//		transaction ���̺��� prod_no������ product�� prod_no ����

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
		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount)); // �� �Խù� �� 
		map.put("list", list); // list : �������������� �������� purchase��
		//==> currentPage �� �Խù� ���� ���� List ����
		rs.close();
		pStmt.close();
		con.close();
		
		return map;
	}
	
	
//	������������
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
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
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
		
		return totalCount;	//buyer_id�� �˻������� �����ϴ� ��� ��
	}
	// �Խ��� currentPage Row ��  return 
	//  SELECT * FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq  	
	//						FROM (	SELECT * FROM transaction WHERE buyer_id='user03' ) inner_table WHERE ROWNUM <=3 ) 	
	//						WHERE row_seq BETWEEN 1 AND 3
    // TRAN_NO    PROD_NO BUYER_ID   PAYMEN RECEIVER_NAME      RECEIVER_PHONE     DEMAILADDR     DLVY_REQUEST  TRAN_S ORDER_DA DLVY_DAT    ROW_SEQ
    // ---------- ---------- ---------------------------------------- ------ ---------------------------------------- ----------------------------
	//10063      10005 user03              1      SCOTT                        null                       null		1		 20/05/01  		1	                                 
	//10005      10010 user03              1      SCOTT                        null                       null		1		 20/04/28    	2                                 
	//10006      10010 user03              1      SCOTT                        null                       null		1 		 20/04/28    	3
	//�� sql�� ���������� ��� ������
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " + //1*3= rownum�� 3
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("ROWNUM��� makeCurrentPageSql "+ sql);	
		
		return sql;	// buyer_id�� �˻��� ��� �ٵ� rownum���� ������
	}
	
	
	public void updateTranCode(Purchase purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		//listProduct.jsp
		//UpdateTranCodeByProdAction.java���� ��Ŵ
		if(purchase.getPurchaseProd().getProTranCode().equals("2")) {
			
			String sql="UPDATE transaction SET TRAN_STATUS_CODE='2'"
					+ " WHERE prod_no=?";
		
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
			stmt.executeUpdate();
		//listPurchase.jsp
		//UpdateTranCodeActiton.java���� ��Ŵ	
		//product�� �ִ� ProTranCode�� 3�̸� ���̺� �ִ� TRAN_STATUS_CODE 3���� �ٲٱ� ��ۿϷ� 
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
