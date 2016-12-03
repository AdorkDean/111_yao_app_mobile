package com.rc.openapi.dao;

import java.sql.SQLException;
import java.util.Map;

import com.rc.openapi.model.cart.CartParam;

public interface IDaoManager {
	
	
	public Map accounts(CartParam arg) throws SQLException;
	
	
	

}
