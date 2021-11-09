package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	//Cria a conexao com o Banco de Dados
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO department " +
					"(Name) " +
					"VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			
			//Substitui os placeholders
			st.setString(1, obj.getName());
			//Executa a consulta
			int rowsAffected = st.executeUpdate();
						
			//Se inseriu algum dado
			if(rowsAffected > 0) {
				rs = st.getGeneratedKeys();  //Pega as chaves geradas
				if(rs.next()) {  //aponta para a primeira chave
					obj.setId(rs.getInt(1));  //atribui a chave ao objeto
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE department " + 
					"SET Id = ?, Name = ? " +
					"WHERE Id = ?"
					);
			//Substituicao dos placeholders
			st.setInt(1, obj.getId());
			st.setString(2, obj.getName());
			st.setInt(3, obj.getId());
			//Executa a consulta
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"DELETE FROM department " +
					"WHERE Id = ?");
			//Substitui o placeholher e executa query
			st.setInt(1, id);
			st.executeUpdate();
			
		} catch (SQLException e) { //SE ocorreu erro
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;  //Objeto para consulta estruturada
		ResultSet rs = null;  //Objeto para armazenar o resultado da consulta
		
		try { //Tenta executar a consulta SQL
			st = conn.prepareStatement(
					"SELECT * FROM department "+
					"WHERE Id = ?");
			st.setInt(1,id);  //O parametro 1 da consulta será substituido pelo argumento "id" do método
			rs = st.executeQuery();  //Executa consulta
			
			if(rs.next()) {  //Se tem algum elemento no resultado da consulta
				Department dep = instantiateDepartment(rs);  //Instancia novo department
				return dep;
			}
			return null;//Se não retornou nenhum registro na consulta
			
		} catch (SQLException e) {  //SE ocorreu erro
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Department> list = new ArrayList<Department>();
		try {
			st = conn.prepareStatement("SELECT department.* FROM department ORDER BY Name");
			rs = st.executeQuery();
			
			while(rs.next()) {
				Department dep = new Department();
				dep = instantiateDepartment(rs);
				list.add(dep);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		return new Department(rs.getInt("Id"), rs.getString("Name"));
	}

}
