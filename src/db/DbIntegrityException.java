package db;

public class DbIntegrityException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	//Repassa a mensagem para a superClasse RuntimeException
	public DbIntegrityException(String msg) {
		super(msg);
	}
	
}
