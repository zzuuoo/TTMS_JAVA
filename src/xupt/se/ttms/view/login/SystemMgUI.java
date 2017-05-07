package xupt.se.ttms.view.login;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import xupt.se.ttms.view.studio.StudioMgrUI;
import xupt.se.ttms.view.system.SysUserModUI;
import xupt.se.ttms.view.tmpl.ImagePanel;
import xupt.se.ttms.view.tmpl.MainUITmpl;
/**
 * 系统管理者UI
 * @author zuo
 *
 */
public class SystemMgUI extends MainUITmpl{
	
	protected  ImagePanel mainPan = new ImagePanel("resource/image/mui.jpg");
	

	protected void initContent(){

		Rectangle rect = contPan.getBounds();
		JButton btnStudioMg = new JButton("演出厅管理");
		btnStudioMg.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnStudioMg.setHorizontalTextPosition(SwingConstants.CENTER);
		btnStudioMg.setIcon(new ImageIcon("resource/image/hall.png"));
		btnStudioMg.setBackground(Color.white);
		btnStudioMg.setBounds(250,300 , 160, 160);
		
		btnStudioMg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				new StudioMgrUI().setVisible(true);
			}
		});
		
		JButton btnUserMg = new JButton("用户管理");
		btnUserMg.setBounds(550, 300, 160, 160);
		btnUserMg.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnUserMg.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUserMg.setIcon(new ImageIcon("resource/image/user.png"));
		btnUserMg.setBackground(Color.white);
//		btnUserMg.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		
		btnUserMg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
//				System.out.println("用户管理");
				SystemMgUI.this.dispose();
				new SysUserModUI ().setVisible(true);
			}
		});
		
		JLabel jb = new JLabel();
		jb.setBounds(335, 50, 1024, 200);
//		jb.setSize(1024, 200);
		jb.setIcon(new ImageIcon("resource/image/tan.gif"));
		
		contPan.add(jb);
		contPan.add(btnUserMg);
		contPan.add(btnStudioMg);
		contPan.setBackground(Color.white);
//		mainPan.setBounds(0, 0, contPan.getWidth(), contPan.getHeight());
//		mainPan.setLayout(null);
//		contPan.add(mainPan);
	}
	
	protected void btnExitClicked(ActionEvent Event){
//		this.getParent().setVisible(true);
		this.dispose();
		new Login().setVisible(true);;
	}	
	
}
