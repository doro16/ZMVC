package com.model2.mvc.service.purchase;

import java.util.List;

import com.model2.mvc.common.Search;

import com.model2.mvc.service.domain.Purchase;


public interface PurchaseDao {

	public void addPurchase(Purchase purchase) throws Exception;

	public Purchase getPurchase(int tranNo) throws Exception;
	
	public Purchase getPurchase2(int prodNo) throws Exception;
	
	//sqlSession.selectList
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception;

	public void updatePurchase(Purchase purchase) throws Exception;

	public void updateTranCodeByProd(Purchase purchase) throws Exception;
	
	public void updateTranCodeByTran(Purchase purchase) throws Exception;
	// SELECT COUNT(*)  
	// FROM (  SELECT * FROM transaction WHERE  buyer_id= 'user03' ) countTable; 
	//  COUNT(*)
	// ---------
    //    7
	// return totalCount;	//buyer_id로 검색했을때 만족하는 결과 수 원래 하나 리턴함
	public int getTotalCount(Search search, String buyerId) throws Exception ;
	
}