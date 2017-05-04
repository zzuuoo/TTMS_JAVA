package xupt.se.ttms.view.login;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import xupt.se.ttms.view.studio.StudioMgrUI;
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
		btnStudioMg.setBounds(100, 120 , 100, 60);
		
		btnStudioMg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				new StudioMgrUI().setVisible(true);
			}
		});
		
		JButton btnUserMg = new JButton("用户管理");
		btnUserMg.setBounds(200, 120, 100, 60);
		btnUserMg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				System.out.println("用户管理");
			}
		});
		
		contPan.add(btnUserMg);
		contPan.add(btnStudioMg);
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
