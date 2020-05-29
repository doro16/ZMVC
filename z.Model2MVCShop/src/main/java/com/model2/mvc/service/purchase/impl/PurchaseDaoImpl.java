package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;


@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao{
	
	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public PurchaseDaoImpl() {
		System.out.println(this.getClass());
	}

	///Method

	///Method
	public void addPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);
	}

	public Purchase getPurchase(int tranNo) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);
	}

	public Purchase getPurchase2(int prodNo) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getPurchase", prodNo);
	}
	
	//sqlSession.selectList
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);	
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
	}

	public void updatePurchase(Purchase purchase) throws Exception{
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	}

	public void updateTranCodeByTran(Purchase purchase) throws Exception{
		sqlSession.update("PurchaseMapper.updateTranCodeByTran", purchase);
	}
	
	public void updateTranCodeByProd(Purchase purchase) throws Exception{
		sqlSession.update("PurchaseMapper.updateTranCodeByProd", purchase);
	}
	// SELECT COUNT(*)  
	// FROM (  SELECT * FROM transaction WHERE  buyer_id= 'user03' ) countTable; 
	//  COUNT(*)
	// ---------
    //    7
	// return totalCount;	//buyer_id로 검색했을때 만족하는 결과 수 원래 하나 리턴함
	public int getTotalCount(Search search, String buyerId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);
		
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", map);
	}

	
}