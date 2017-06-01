package xupt.se.ttms.dao;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iLogindeDAO;
import xupt.se.ttms.idao.iemployeeDAO;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.GlobalVariable;
import xupt.se.ttms.model.Loginde;
import xupt.se.util.DBUtil;


public class LogindeDAO implements iLogindeDAO{
//	private String account;
//	private String password;
//	private String status; //"经理" "系统管理者""售票员"
	
	//注册   
	//return 1-工号不存在 不能注册     2-该工号已经有账号  不能注册   3-账号与其他账号重复
	public int LogindeDAO_register(Loginde log,int emp_id) {	
		if(JudgeEmp_ID(emp_id)){	//判断工号  true-工号不存在不能注册 
			return 1;
		}
		if(JudgeEmp_ID_inLoginde(emp_id)){	//判断该工号是否已经有账号
			return 2;
		}
		if(JudgeAccount(log.getAccount())){		//判断账号是否重复
			insert(log);
			return 0;
		}
		return 3;
	}
	
	//登陆
	public int LogindeDAO_log(Loginde log) {				
		if(JudgeAccount(log.getAccount())){		//不能登陆
			System.out.println("登陆失败，账号不存在");
			return 0;
		}
		else{									//可以登陆
			List<Loginde> logList = null;
			//logList = select("account ="+log.getAccount());
			logList = select();
			for(Loginde lg:logList){
				if(lg.getAccount().compareTo(log.getAccount())==0&&lg.getPassword().compareTo(log.getPassword())==0&&lg.getStatus().compareTo(log.getStatus())==0){
					//System.out.println("登陆成功");	
					GlobalVariable.emp_id = lg.getEmp_id();
//					System.out.println(lg.getEmp_id()+"bbbbb");
					return 1;
				}
			}
			System.out.println("遍历结束，没有匹配项目，登陆失败");
			return 0;
		}
	}
	
	//修改密码
	public int LogindeDAO_amendPassword(Loginde log,String newPassword){		
		List<Loginde> logList = null;
		//logList = select("account ="+log.getAccount());
		logList = select();
		for(Loginde lg:logList){
			if(lg.getAccount().compareTo(log.getAccount())==0&&lg.getPassword().compareTo(log.getPassword())==0&&lg.getStatus().compareTo(log.getStatus())==0){
				//账号密码验证成功
			    update_password(log, newPassword);
			    return 1;
			}
		}
		//System.out.println("账号密码不匹配");
		return 0;
	}
	
	
	
	//判断工号
	public boolean JudgeEmp_ID(int emp_id){
		List<Employee> empList = null;
		iemployeeDAO emp=DAOFactory.creatEmployeeDAO();
		empList = emp.selectAll();
		for(Employee em:empList){
			if(em.getEmp_id()==emp_id);
			return false;   //工号存在
		}
		return true;		//工号不存在
	}
	
	//判断该工号是否已经有账号
	public boolean JudgeEmp_ID_inLoginde(int emp_id){ 
		List<Loginde> logList = null;
		logList = select();
		for(Loginde lg:logList){
			if(lg.getEmp_id()==emp_id)
					return true;	//该工号已经在数据库中或者说已经有账号
		}
		return false;	
	}
	
	//判断账号是否存在
	public boolean JudgeAccount(String account){
		List<Loginde> logList = null;
		//logList = select("account ="+account);
		logList = select();
		for(Loginde lg:logList){
			if(lg.getAccount().compareTo(account) == 0);
			return false;	
		} 
		//if(logList == null)
		//	return false;		
		return true;
	}
	
	//从数据库中取出所有信息
	public List<Loginde> select(){
		List<Loginde> logList = null;
		logList=new LinkedList<Loginde>();
		try {
			String sql = "select emp_id,account,password,status "
					+ "from Loginde";
			DBUtil db = new DBUtil();
			if(!db.openConnection()){
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst!=null) {
				while(rst.next()){
					Loginde stu=new Loginde();
					stu.setEmp_id(rst.getInt("emp_id"));
					stu.setAccount(rst.getString("account"));
					stu.setPassword(rst.getString("password"));
					stu.setStatus(rst.getString("status"));
					logList.add(stu);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
		}
		
		return logList;
	}
	
	//插入数据
	public int insert(Loginde log) {
		try {
			String sql = "insert into Loginde(account,password,status)"
					+ " values('"
					+ log.getAccount()
					+ "', '"
					+ log.getPassword()
					+ "', '" 
					+ log.getStatus()
					+ "' )";
			DBUtil db = new DBUtil();
			db.openConnection();
			System.out.println("数据库连接成功");
			ResultSet rst = db.getInsertObjectIDs(sql);

//				      
//			if (rst!=null && rst.first()) {
//				stu.setID(rst.getInt(1));
//			}
//			System.out.println("hello");
//			if(rst.next()&&rst.first()){
//				stu.setID(rst.getInt(1));
//			}
			db.close(rst);
			db.close();
			return 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	//只修改密码
	public int update_password(Loginde log,String newPassword) {
	int rtn=0;
	try {
		String sql = "update Loginde set " 
				+ " account ='"
				+ log.getAccount() + "', " 
				+ " password = '"
				+ newPassword + "', "
				+ " status = '"
				+ log.getStatus() +  "', \n";

		sql += " where account = '" + log.getAccount() + "'";
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
	
	//删除用户
	public int delete(String account) {
		int rtn=0;		
		try{
			String sql = "delete from  Loginde";
			sql += " where account = " + account;
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

	@Override
	public int LogindeDAO_register(Loginde log) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
