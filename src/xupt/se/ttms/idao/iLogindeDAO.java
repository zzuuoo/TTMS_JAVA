package xupt.se.ttms.idao;

import xupt.se.ttms.model.Loginde;

public interface iLogindeDAO {

	public int LogindeDAO_register(Loginde log);
	public int LogindeDAO_log(Loginde log);
	public int LogindeDAO_amendPassword(Loginde log,String newPassword);
//	int delete(String account);
//	List<Loginde> select();
}
