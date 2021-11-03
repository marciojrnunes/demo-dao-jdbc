package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	//Cria uma dependencia com a conexão ao DB
	private Connection conn;
	
	//Construtor
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT seller.*,department.Name as DepName "
				+ "FROM seller INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id "
				+ "WHERE seller.Id = ?");
			
			st.setInt(1, id);  //O parametro 1 da consulta será substituido pelo argumento "id" do método
			rs = st.executeQuery();
			
			if(rs.next()) { //Se existe algum registro na consulta
				//Transforma a consulta na forma de linhas e colunas nos respectivos Objetos associados dos tipos Seller e Department
				Department dep = new Department();
				dep.setId(rs.getInt("DepartmentId"));  //atributo id do departament recebe a coluna "DepartmentId"
				dep.setName(rs.getString("DepName"));  //atributo id do departament recebe a coluna "DepartmentId"
				Seller obj = new Seller();
				obj.setId(rs.getInt("Id"));   //atributo id do seller recebe a coluna "Id"
				obj.setName(rs.getString("Name"));   //atributo Name do seller recebe a coluna "Name"
				obj.setEmail(rs.getString("Email"));   //atributo Email do seller recebe a coluna "Email"
				obj.setBaseSalary(rs.getDouble("BaseSalary"));   //atributo BaseSalary do seller recebe a coluna "BaseSalary-"
				obj.setBirthDate(rs.getDate("BirthDate"));   //atributo BirthDate do seller recebe a coluna "BirthDate"
				obj.setDepartment(dep);  //atributo Department recebe o objeto Department criado acima
				return obj;
			}
			return null;  //Se não retornou nenhum registro na consulta
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
