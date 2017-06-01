package xupt.se.ttms.idao;

import java.util.List;

import xupt.se.ttms.model.Employee;

public interface iemployeeDAO {
		public int insert(Employee emp);
		public int update(Employee emp);
		public int delete(int ID);
		public Employee select(String condt); 
		public List<Employee> selectAll(); 

}
