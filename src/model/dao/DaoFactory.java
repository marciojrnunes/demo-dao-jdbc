package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {  //Cria um objeto JDBC e retorna um objeto Dao
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {  //Cria um objeto JDBC e retorna um objeto Dao
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
