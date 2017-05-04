package xupt.se.ttms.view.system;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import xupt.se.ttms.view.studio.StudioMgrUI;
import xupt.se.ttms.view.tmpl.PopUITmpl;


public class SysUserModUI extends PopUITmpl {
	
	
	public SysUserModUI(){
		this.setVisible(true);
		windowName.setForeground(Color.WHITE);
		windowName.setText("系统管理");
	}
	
	public static void main(String[] args) {
		SysUserModUI sy = new SysUserModUI();
		
	}
	protected void initContent(){
	
	
		Rectangle rect = contPan.getBounds();
		JButton btnStudioMg = new JButton("演出厅管理");
		JButton btnUserMg = new JButton("用户管理");
		
		btnStudioMg.setBounds(100, 120 , 100, 60);
		
		btnStudioMg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				new StudioMgrUI().setVisible(true);
			}
		});
		contPan.add(btnUserMg);
		contPan.add(btnStudioMg);
		

	}
	
	protected void onWindowClosing(){
//		System.exit(0);
		this.dispose();
	}


}
