package xupt.se.ttms.dao;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import xupt.se.ttms.idao.iemployeeDAO;
import xupt.se.ttms.model.Employee;
import xupt.se.util.DBUtil;

public class employeeDAO implements iemployeeDAO{

	@Override
	public int insert(Employee emp) {
		// TODO Auto-generated method stub
		
		try {
			String sql = "insert into employee(emp_id, emp_name, emp_tel_num, emp_addr,emp_email )"
					+ " values("
					+ emp.getEmp_id()
					+ ",' "
					+ emp.getEmp_name()
					+ "',' " 
					+ emp.getEmp_tel_num()
					+ "', '"
					+ emp.getEmp_addr()
					+ "', '"
					+ emp.getEmp_email()
					+ "' )";
			DBUtil db = new DBUtil();
			db.openConnection();
			System.out.println("数据库连接成功");
			ResultSet rst = db.getInsertObjectIDs(sql);

			db.close(rst);
			db.close();
			
			//System.out.println(stu.getID());
			return 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int update(Employee emp) {
		// TODO Auto-generated method stub
		int rtn=0;
		try {
			String sql = "update employee set " 
					+ " emp_id ="+ emp.getEmp_id() + ", " 
					+ " emp_name = '"+ emp.getEmp_name() + "', " 
					+ " emp_tel_num = '"+ emp.getEmp_tel_num() + "', " 
					+ " emp_addr = '"+ emp.getEmp_addr() + "' ,"
					+ " emp_email = '"+ emp.getEmp_email() + "' \n";

			sql += " where emp_id = " + emp.getEmp_id();
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn =db.execCommand(sql);
			System.out.println("数据库连接成功--修改");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	@Override
	public int delete(int ID) {
		// TODO Auto-generated method stub
		int rtn=0;		
		try{
			String sql = "delete from  emp_id ";
			sql += " where emp_id = " + ID;
			DBUtil db = new DBUtil();
			db.openConnection();
			System.out.println("数据库连接成功__删除");
			rtn=db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;		
	}
	

//	@Override
//	public List<Employee> select(String condt) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<Employee> selectAll() {
		// TODO Auto-generated method stub
		List<Employee> empList = null;
		empList=new LinkedList<Employee>();
		try {
			String sql = "select emp_id, emp_name, emp_tel_num, emp_addr, emp_email from employee";
			
			DBUtil db = new DBUtil();
			if(!db.openConnection()){
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst!=null) {
				while(rst.next()){
					Employee emp=new Employee();
					emp.setEmp_id(rst.getInt("emp_id"));
					emp.setEmp_name(rst.getString("emp_name"));
					emp.setEmp_tel_num(rst.getString("emp_tel_num"));
					emp.setEmp_addr(rst.getString("emp_addr"));
					emp.setEmp_email(rst.getString("emp_email"));
					empList.add(emp);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
		}
		
		return empList;
	}

	@Override
	public Employee select(String condt) {

		Employee emp=new Employee();
		// TODO Auto-generated method stub
		try {
			String sql = "select emp_id, emp_name, emp_tel_num, emp_addr, emp_email from employee";
					condt.trim();
			if (!condt.isEmpty())
				sql += " where " + condt;
			
			DBUtil db = new DBUtil();
			if(!db.openConnection()){
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst!=null) {
				while(rst.next()){
					emp.setEmp_id(rst.getInt("emp_id"));
					emp.setEmp_name(rst.getString("emp_name"));
					emp.setEmp_tel_num(rst.getString("emp_tel_num"));
					emp.setEmp_addr(rst.getString("emp_addr"));
					emp.setEmp_email(rst.getString("emp_email"));
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
		}
		
		
		return emp;
	}

}
