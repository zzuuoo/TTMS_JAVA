package xupt.se.ttms.view.sellticket;

import java.awt.event.ActionEvent;

import xupt.se.ttms.view.tmpl.PopUITmpl;

public class QuitTicketUIPop extends PopUITmpl{
	
	public QuitTicketUIPop(){
		setWindowName(" 退  票 ");
		initContent();
		
	}
	

	//Set the name of the popup window 
	public void setWindowName(String name){
		windowName.setText(name);
	}
	
	//To be override by the detailed business block interface 
	protected void onWindowClosing(){
//		System.exit(0);
		this.dispose();
	}
	
	
	//To be override by the detailed business block interface 
	protected void initContent(){
		
		
		
	}
	

	
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
		System.exit(0);
	}
	
}
