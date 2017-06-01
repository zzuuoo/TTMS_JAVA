package xupt.se.ttms.model;

public class Loginde {
	private String account;
	private String password;
	private String status; //"经理" "系统管理者""售票员"
	private int emp_id;
	
	public Loginde() {
		// TODO Auto-generated constructor stub
	}
	public Loginde(String account,String password,String status) {
		// TODO Auto-generated constructor stub
		this.password =  password;
		this.account  =  account;
		this.status   =  status;
	}
	public Loginde(String account,String password,String status,int emp_id) {
		// TODO Auto-generated constructor stub
		this.password =  password;
		this.account  =  account;
		this.status   =  status;
		this.emp_id   =  emp_id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
}
