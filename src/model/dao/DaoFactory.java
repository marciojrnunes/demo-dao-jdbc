package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {  //Cria um objeto JDBC e retorna um objeto Dao
		return new SellerDaoJDBC();
	}
}
