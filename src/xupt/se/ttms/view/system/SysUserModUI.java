package xupt.se.ttms.view.system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import xupt.se.ttms.view.login.SystemMgUI;
import xupt.se.ttms.view.studio.StudioMgrUI;
import xupt.se.ttms.view.tmpl.MainUITmpl;
import xupt.se.ttms.view.tmpl.PopUITmpl;


public class SysUserModUI extends MainUITmpl {
	
	
//	JTabbedPane jtb = new JTabbedPane(JTabbedPane.LEFT);
	
	public SysUserModUI(){
		initContent();
//		this.setVisible(true);
	}
	

	protected void initContent(){
		JTabbedPane jtb = new JTabbedPane(JTabbedPane.LEFT);
		
		JPanel wjp = new JPanel();
		wjp.setLayout(null);
		wjp.add(new JLabel("用户1"));
		
		JPanel wjp2 = new JPanel();
		wjp2.setLayout(null);
		wjp2.add(new JLabel("我是用户"));
		
		JPanel wjp3 = new JPanel();
		wjp3.setLayout(null);
		wjp3.add(new JLabel("用户数据测试"));
		
		
		
		jtb.setBounds(0, 0, 1024, 600);
		
//		
		jtb.addTab("售票员",wjp );
		jtb.addTab("经理",wjp2 );
		jtb.addTab("系统管理者",wjp3 );
		jtb.setFont(new Font(" ",1,20));
		contPan.add(jtb);
		contPan.validate();


	}
	
//	protected void onWindowClosing(){
////		System.exit(0);
//		this.dispose();
//		new SystemMgUI().setVisible(true);
//	}


	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
		this.dispose();
		new SystemMgUI().setVisible(true);
	}	
	

}
