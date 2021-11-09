package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		

		//Department obj = new Department(1, "Books");
		//Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.0, obj);
		
		Scanner sc = new Scanner(System.in);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();  //Utilizando o padrão Factory o programa não conhece a implementação sellerDaoJDBC
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();  //Utilizando o padrão Factory o programa não conhece a implementação departmentDaoJDBC
		
		System.out.println("=== TEST 1: department findById====");
		Department department = departmentDao.findById(2);
		System.out.println(department);
		
		System.out.println("=== TEST 2: department DeleteById====");
		System.out.print("Enter the department Id to delete: ");
		Integer deleteId = sc.nextInt(); 
		sc.nextLine();
		departmentDao.deleteById(deleteId);
		
		System.out.println("\n=== TEST 3: Department update ====");
		department = departmentDao.findById(4);  //pega o vendedor de id = 4
		department.setName("CDs");  //Atribui um novo nome
		departmentDao.update(department);  //Atualiza os dados desse vendedor
		System.out.println("Update Department Completed");
		
		System.out.println("\n=== TEST 4: Department insert ====");
		System.out.println("Insert department name to insert: ");
		String name = sc.nextLine();
		Department newDep = new Department(null, name);
		departmentDao.insert(newDep);
		System.out.println("Inserted! = New id = " + newDep.getId());
		
		System.out.println("\n=== TEST 4: Department FindAll ====");
		List<Department> depList = departmentDao.findAll();
		System.out.println("Department List: ");
		for (Department dep : depList) {
			System.out.println(dep);
		}
		
		System.out.println("\n=== TEST 1: seller findById====");
		Seller seller = sellerDao.findById(3);
		
		System.out.println(seller);
		
		System.out.println("\n=== TEST 2: seller findByDepartment ====");
		department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 3: seller findAll ====");
		list = sellerDao.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 4: seller insert ====");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! = New id = " + newSeller.getId());
		
		System.out.println("\n=== TEST 5: seller update ====");
		seller = sellerDao.findById(1);  //pega o vendedor de id = 1
		seller.setName("MArtha Waine");  //Atribui um novo nome
		sellerDao.update(seller);  //Atualiza os dados desse vendedor
		System.out.println("Update Completed");
		
		System.out.println("\n=== TEST 6: seller delete ====");
		System.out.println("Enter id for delete test: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Delete completed");
		
		sc.close();
	}

}
