package xupt.se.ttms.service;

import java.util.List;

import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iemployeeDAO;
import xupt.se.ttms.model.Employee;



public class EmployeeSrv {
	private iemployeeDAO empDAO=DAOFactory.creatEmployeeDAO();
	
	public int add(Employee emp){
		return empDAO.insert(emp); 		
	}
	
	public int modify(Employee emp){
		return empDAO.update(emp); 		
	}
	
	public int delete(int ID){
		return empDAO.delete(ID); 		
	}
	
	public List<Employee> FetchAll(){
		return empDAO.selectAll();
	}
//	public List<Employee> FetchConnnection(String condt){
//		return empDAO.selectConnect(condt);
//	}
	
	public Employee FetchOne(String condt){
		return empDAO.select(condt);
	}
}
