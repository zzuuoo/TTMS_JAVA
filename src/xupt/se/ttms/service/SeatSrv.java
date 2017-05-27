package xupt.se.ttms.service;

import java.util.List;

import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iSeatDAO;
import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;

public class SeatSrv {
	private iSeatDAO stuDAO=DAOFactory.creatSeatDAO();
	
	public int add(Seat stu){
		return stuDAO.insert(stu); 		
	}
	
	public int modify(Seat stu){
		return stuDAO.update(stu); 		
	}
	
	public int delete(int ID){
		return stuDAO.delete(ID); 		
	}
	public int delete(String condt){
		return stuDAO.delete(condt); 		
	}
	
	public List<Seat> Fetch(String condt){
		return stuDAO.select(condt);		
	}
	
	public List<Seat> FetchAll(){
		return stuDAO.select("");		
	}
	public void addSeat(Studio stu){
		
		for(int i=0;i<stu.getColCount();i++){
			for(int j =0;j<stu.getRowCount();j++){
				add(new Seat(stu.getID(),j,i));
			}
		}
	}
}
