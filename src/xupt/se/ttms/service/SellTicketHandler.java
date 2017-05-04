package xupt.se.ttms.service;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xupt.se.ttms.model.Ticket;


public class SellTicketHandler {
	private List<Ticket> curTickets;
	
	public void makeNewSale(){
		curTickets = new ArrayList<Ticket>();
	}

	public void addTicket(Ticket ticket) {
		curTickets.add(ticket);
		Date date = new Date();
    	TicketSrv ticketSrv = new TicketSrv();
    	ticketSrv.lockTicket(ticket.getId(), DateFormat.getDateTimeInstance().format(date));
    	ticket.setCurrent_locked_time(date);
	} 
	
	public void removeTicket(Ticket ticket) {
		curTickets.remove(ticket);
    	TicketSrv ticketSrv = new TicketSrv();
    	ticketSrv.unlockTicket(ticket.getId());
    	ticket.setCurrent_locked_time(null);
	} 
	
    public String getInfo(){
    	int i=0;
    	double price = 0;
    	String info = "";
    	for(Ticket t: curTickets){
    		info+="影片："+t.getPlayName()+"\n";
    		info+="场次："+DateFormat.getDateTimeInstance().format(t.getSchedule().getSched_time())+"\n";
    		info+="座位："+t.getSeat().getRow()+"排"+t.getSeat().getColumn()+"座\n";
    		info+="价格："+t.getSchedule().getSched_ticket_price()+"元\n\n";
    		i++;
    		price+=t.getSchedule().getSched_ticket_price();
    	}
    	if(curTickets.size()>0){
    		info+="=================\n";
    		info+="共"+i+"张票  "+ price + "元\n";
    	}
    	return info;
    }
    
    public boolean isTicketSelected(Ticket ticket){
    	for(Ticket t: curTickets){
    		if(t.getId()==ticket.getId())
    			return true;
    	}
    	return false;
    }
    
    public void clearSale(){
    	while(curTickets.size()>0){
    		removeTicket(curTickets.get(0));
    	}
    	makeNewSale();
    }

    public boolean doSale(){
    	if(new SaleSrv().doSale(curTickets)){
    		makeNewSale();
    		return true;
    	}
    	return false;
    }
}
