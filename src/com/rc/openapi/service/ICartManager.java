package com.rc.openapi.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.rc.openapi.model.cart.CartItem;
import com.rc.openapi.model.cart.CartParam;
import com.rc.openapi.vo.TCart;


public interface ICartManager {
	
	
	public Map accounts(CartParam arg) throws SQLException;
	public List<CartItem> getCartItem(CartParam arg)throws SQLException;
	
	/**
	 * 计算购物车
	 * @return
	 * @throws SQLException
	 */
	public BigDecimal calculate(List<Long> goods) throws SQLException;
	

	
	/**
	 * 获取购物车
	 * @param arg
	 * @return
	 * @throws SQLException
	 */
	public TCart getCart(CartParam arg) throws SQLException;
	
	/**
	 *  添加购物车
	 * @param goodsId
	 * @param userId
	 * @param uuid
	 * @return
	 * @throws SQLException
	 */
	public long addCart(CartParam arg,int count)throws SQLException;
	
	/**
	 * 移除购物车的单个商品
	 * @param goodsId
	 * @param userId
	 * @param uuid
	 * @param islogin
	 */
	public int removeGoodsFromCart(CartParam arg)throws SQLException;
	
	/**
	 * 批量删除购车的商品
	 * @param list
	 * @param islogin
	 * @param userId
	 * @param uuid
	 * @return
	 * @throws SQLException
	 */
	public int batchRemoveGoodsFromCart(List<Long> list,CartParam arg) throws SQLException;
	
	
	/**
	 * 修改购物车商品的数量
	 * @param islogin
	 * @param userId
	 * @param uuid
	 * @param goodsId
	 * @return flag = 1 : 加 flag=2:减法 flag=3 手动输入数量
	 * @throws SQLException
	 */
	public int updateGoodsCount(String cartid,String goodsid,int count,String flag) throws SQLException;
	
	 
	/**
	 * 清空购物车
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public int emptyCart(CartParam arg) throws SQLException;
	
	
	public void synCart(long userId,String uuid)  throws SQLException ;
	
	
	/**
	 * 重新购买
	 * @param orderid
	 * @throws SQLException
	 */
	 public String againBuy(long orderid)  throws SQLException ;
	
	
	
	 public void combineCart(Long memberId, String uuid) throws SQLException,Exception;

}
