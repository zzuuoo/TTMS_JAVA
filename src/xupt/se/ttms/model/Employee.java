package xupt.se.ttms.model;

import java.io.Serializable;

public class Employee implements Serializable {
	private int emp_id;
	private String emp_name;
	private String emp_tel_num;
	private String emp_addr;
	private String emp_email;
	private String account;
	private String status;
	
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	
	public Employee(int emp_id,String emp_name,String emp_tel_num,String emp_addr,String emp_email) {
		// TODO Auto-generated constructor stub
		this.emp_id      =  emp_id;
		this.emp_name    =  emp_name;
		this.emp_tel_num =  emp_tel_num;
		this.emp_addr    =  emp_addr;
		this.emp_email   =  emp_email;
	}
	
	public int getEmp_id() {
		return emp_id;
	}
	
	public String getEmp_name() {
		return emp_name;
	}
	
	public String getEmp_tel_num() {
		return emp_tel_num;
	}
	
	public String getEmp_addr() {
		return emp_addr;
	}
	
	public String getEmp_email() {
		return emp_email;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	
	public void setEmp_tel_num(String emp_tel_num) {
		this.emp_tel_num = emp_tel_num;
	}
	
	public void setEmp_addr(String emp_addr) {
		this.emp_addr = emp_addr;
	}
	
	public void setEmp_email(String emp_email) {
		this.emp_email = emp_email;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
}