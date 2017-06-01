package xupt.se.ttms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import xupt.se.ttms.idao.iSaleDAO;
import xupt.se.ttms.model.Ticket;
import xupt.se.util.DBUtil;

public class SaleDAO implements iSaleDAO {
	DBUtil db;
	Connection con;

	 
	public boolean doSale(List<Ticket> tickets) {
		try {
			int id = -1;
			db = new DBUtil();
			db.openConnection();
			con = db.getConn();
			con.setAutoCommit(false);

			double n=0;
			if(!tickets.isEmpty()){
				n=tickets.get(0).getPrice();
			}
			double payment = tickets.size()*n;
	        String sql = "insert into sale(sale_time, sale_payment,sale_type, sale_status) VALUES(?,"+payment+",1,1)";  
	        PreparedStatement prep = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
	        prep.setTimestamp(1, new Timestamp(new Date().getTime()));  
	        prep.executeUpdate();  
	        ResultSet rs = prep.getGeneratedKeys();  
	        if (rs.next()) {  
	            id = rs.getInt(1);  
	        }
	        if(id>0){
	        	for(Ticket t : tickets){
		        	double price = t.getSchedule().getSched_ticket_price();
			        String sql2 = "insert into sale_item(ticket_id, sale_ID, sale_item_price) VALUES(" +
		        	t.getId() + ", " + id + ", " + price + ")";  
		        	int flag = db.execCommand(sql2);
		        	if(flag==1){
				        String sql3 = "update ticket set ticket_status=9 where ticket_id = " + t.getId();
			        	int flag2 = db.execCommand(sql3);
			        	if(flag2!=1){
			        		return false;
			        	}
		        	}else
		        		return false;
	        	}
	        }
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return false;
			}
			return false;
		} finally {
			try {
				con.setAutoCommit(true);
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}


	@Override
	public boolean refund(List<Ticket> tickets) {
		try {
			int id = -1;
			db = new DBUtil();
			db.openConnection();
			con = db.getConn();
			con.setAutoCommit(false);

			double n=0;
			if(!tickets.isEmpty()){
				n=tickets.get(0).getPrice();
			}
			double payment = tickets.size()*n;
	        String sql = "insert into sale(sale_time, sale_payment,sale_type, sale_status) VALUES(?,"+payment+",-1,1)";  
	        PreparedStatement prep = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
	        prep.setTimestamp(1, new Timestamp(new Date().getTime()));  
	        prep.executeUpdate();  
	        ResultSet rs = prep.getGeneratedKeys();  
	        if (rs.next()) {  
	            id = rs.getInt(1);  
	        }
	        if(id>0){
	        	for(Ticket t : tickets){
		        	double price = t.getSchedule().getSched_ticket_price();
			        String sql2 = "insert into sale_item(ticket_id, sale_ID, sale_item_price) VALUES(" +
		        	t.getId() + ", " + id + ", " + price + ")";  
		        	int flag = db.execCommand(sql2);
		        	if(flag==1){
				        String sql3 = "update ticket set ticket_status=0 where ticket_id = " + t.getId();
			        	int flag2 = db.execCommand(sql3);
			        	if(flag2!=1){
			        		return false;
			        	}
		        	}else
		        		return false;
	        	}
	        }
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return false;
			}
			return false;
		} finally {
			try {
				con.setAutoCommit(true);
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
