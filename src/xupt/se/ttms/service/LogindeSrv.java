package xupt.se.ttms.service;

import java.util.List;

import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iLogindeDAO;
import xupt.se.ttms.model.Loginde;

public class LogindeSrv {
	private iLogindeDAO logDAO=DAOFactory.creatLogindeDAO();
	
	public int Loginde_register(Loginde log){   //注册
		return logDAO.LogindeDAO_register(log);	
	}
	
	public int LogindeDAO_log(Loginde log){     //登陆
		return logDAO.LogindeDAO_log(log);
	}
	
	public int LogindeDAO_amendPassword(Loginde log,String newPassword){ //修改密码
		return logDAO.LogindeDAO_amendPassword(log, newPassword);		
	}
	
//	public int LogindeDAO_delete(String account){	//删除用户
//		return logDAO.delete(account);
//	}
	
//	public List<Loginde> LogindeDAO_inquireAllMessage(){	//查询所有用户信息
//		return logDAO.select();
//	}
}
