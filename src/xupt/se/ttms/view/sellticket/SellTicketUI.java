package xupt.se.ttms.view.sellticket;

import java.awt.event.ActionEvent;

import xupt.se.ttms.view.login.Login;
import xupt.se.ttms.view.tmpl.MainUITmpl;
/**
 * 售票员的UI
 * @author zuo
 *
 */
public class SellTicketUI extends MainUITmpl{

	
	protected void initContent(){
		
		//在这添加售票的内容
		
		
	}
	
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
//		System.exit(0);
		this.dispose();
		new Login().setVisible(true);;
	}	
	
}
