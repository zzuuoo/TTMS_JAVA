package xupt.se.ttms.view.login;

import java.awt.event.ActionEvent;

import xupt.se.ttms.view.tmpl.MainUITmpl;
/**
 * 经理UI
 * @author zuo
 *
 */
public class Manager extends MainUITmpl{
	
	protected void initContent(){
		
	}
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
//		System.exit(0);
		this.dispose();
		new Login().setVisible(true);;
	}	

}
