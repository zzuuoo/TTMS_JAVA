package xupt.se.ttms.service;

import java.util.ArrayList;
import java.util.List;

import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iPlayDAO;
import xupt.se.ttms.model.Play;

public class PlaySrv {
	private iPlayDAO stuDAO=DAOFactory.creatPlayDAO();
	
	public int add(Play stu){
		return stuDAO.insert(stu); 		
	}
	
	public int modify(Play stu){
		return stuDAO.update(stu); 		
	}
	
	public int delete(int ID){
		return stuDAO.delete(ID); 		
	}
	
	public List<Play> Fetch(String condt){
		return stuDAO.select(condt);		
	}
	
	public List<Play> FetchAll(){
		return stuDAO.select("");		
	}
	
	public List<Play> selectScheduledPlay(String condt){
		List<Play> list = new ArrayList<Play>();
		Play play1 = new Play();
		play1.setName("test1");
		Play play2 = new Play();
		play2.setName("test1");
		list.add(play1);
		list.add(play2);
		return list;
	}
}
