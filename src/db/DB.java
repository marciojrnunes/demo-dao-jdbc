package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	

	//Metodo auxiliar para carregar os dados de db.properties
	private static Properties loadProperties() {
		//Abre o arquivo db.properties e guarda os dados em Properties
		try (FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			//Faz a leitura e guarda dentro do objeto
			props.load(fs);
			return props;
		} catch (IOException e) {
			//lança a exceção personalizada
			throw new DbException(e.getMessage());
		}
	}
	
	//Método para conectar com o banco de dados
	private static Connection conn = null;
	
	public static Connection getConnection() {
		//conecta com o banco se já não estiver conectado
		if(conn == null) {
			try {
				Properties props = loadProperties();  //pega as propriedades do banco
				String url = props.getProperty("dburl");  //pega a URL definida no arquivo
				conn = DriverManager.getConnection(url, props);  //conecta com o banco (instancia um objeto Connection)
			}
			catch(SQLException e) {  //caso ocorra um erro no getConnection
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	//Método para fechar a conexão
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeStatement(Statement st) {
		if(st != null) {  //Se objeto Statement não é vazio
			try {
				st.close();  //fecha objeto Statement
			} catch (SQLException e) {  //Em caso de exececao
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet	rs) {
		if(rs != null) {  //Se objeto ResultSet não é vazio
			try {
				rs.close();  //fecha objeto ResultSet
			} catch (SQLException e) {  //Em caso de exececao
				throw new DbException(e.getMessage());
			}
		}
	}
	
}
