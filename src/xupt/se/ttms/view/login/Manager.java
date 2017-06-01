package xupt.se.ttms.view.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.xml.bind.JAXBElement.GlobalScope;

import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.GlobalVariable;
import xupt.se.ttms.service.EmployeeSrv;
import xupt.se.ttms.view.play.PlayMgrUI;
import xupt.se.ttms.view.schedule.ScheduleMgUI;
import xupt.se.ttms.view.sellticket.SellTicketUI;
import xupt.se.ttms.view.studio.StudioSeat;
import xupt.se.ttms.view.tmpl.MainUITmpl;
/**
 * 经理UI
 * @author zuo
 *
 */
public class Manager extends MainUITmpl{
	
	public void Manager(){
		initContent();
	}
	
	protected void initContent(){
		
		System.out.println(GlobalVariable.emp_id);
		JPanel workPanel = new JPanel();
		workPanel.setLayout(null);
		workPanel.setBounds(0, 0, 1024, 600);
		workPanel.setBackground(Color.white);
	
		JButton filmManager = new JButton();
		filmManager.setVerticalTextPosition(SwingConstants.BOTTOM);
		filmManager.setHorizontalTextPosition(SwingConstants.CENTER);
		filmManager.setIcon(new ImageIcon("resource/image/film.png"));
		filmManager.setBackground(Color.WHITE);
		filmManager.setText(" 剧目管理 ");
		filmManager.setBounds(150, 100, 160, 160);
		filmManager.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new PlayMgrUI().setVisible(true);
				Manager.this.dispose();
				
			}
		});
		
		JButton palyManager = new JButton();
		palyManager.setVerticalTextPosition(SwingConstants.BOTTOM);
		palyManager.setHorizontalTextPosition(SwingConstants.CENTER);
		palyManager.setIcon(new ImageIcon("resource/image/playm.png"));
		palyManager.setBackground(Color.WHITE);
		palyManager.setText(" 演出计划管理 ");
		palyManager.setBounds(430, 100, 160, 160);
		
		//演出计划管理按钮监听
		palyManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {

				new ScheduleMgUI().setVisible(true);
				Manager.this.dispose();
			
			}
		});
		
		JButton checkC = new JButton();
		checkC.setVerticalTextPosition(SwingConstants.BOTTOM);
		checkC.setHorizontalTextPosition(SwingConstants.CENTER);
		checkC.setIcon(new ImageIcon("resource/image/checkc.png"));
		checkC.setBackground(Color.WHITE);
		checkC.setText("统计与查询");
		checkC.setBounds(700, 100, 160, 160);
		checkC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		workPanel.add(filmManager);
		workPanel.add(palyManager);
		workPanel.add(checkC);
		
		
		
		

//		JLabel jb = new JLabel();
//		jb.setIcon(new ImageIcon("resource/image/dabai.gif"));
//		jb.setBounds(450,200,1024,400);
		
		JLabel jb = new JLabel("    此广告位招租 ");
		jb.setFont(new Font("",2,36));
		jb.setBounds(100,400,1024,200);
		jb.setBackground(Color.GREEN);
		jb.setIcon(new ImageIcon("resource/image/a1.gif"));
		workPanel.add(jb);
		
		contPan.add(workPanel);
//		contPan.add(workPanel2);
		contPan.validate();
		
		
	}
	
	protected void showCurrentUser(){
//		LoginedUser curUser=LoginedUser.getInstance();
//		String name=curUser.getEmpName();
		Employee ep = new EmployeeSrv().FetchOne(" emp_id =  "+GlobalVariable.emp_id);
		String name =ep.getEmp_name();
		if(null==name ||  name.isEmpty())
			usrName.setText("匿名用户");
		else
			usrName.setText(name);		
	}
	
	
	protected void initHeader() {
		try {

			usrLabel.setBounds(frmWidth-160, 5, 80, 30);
			usrLabel.setText("当前用户：");
			headPan.add(usrLabel);
			
			usrName.setBounds(frmWidth-80, 5, 80, 30);
			usrName.setText("匿名");
			usrName.setFont(new java.awt.Font("宋体", 1, 15));
			usrName.setForeground(Color.black);				
			headPan.add(usrName);
			
			btnModPwd.setBounds(frmWidth-160, 40, 80, 30);
			btnModPwd.setMargin(new Insets(0,0,0,0));
			btnModPwd.setContentAreaFilled(false);
			btnModPwd.setForeground(Color.black);
			headPan.add(btnModPwd);
			btnModPwd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent Event) {
					btnModUserClicked();
				}
			});
			
			btnExit.setBounds(frmWidth-80, 40, 80, 30);
			btnExit.setContentAreaFilled(false);
			btnExit.setForeground(Color.black);
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent Event) {
					btnExitClicked(Event);
				}
			});
			
			headPan.add(btnExit);	
			
			//Show the information of current user
			showCurrentUser();
			
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(null, e, "Exception", 0);
			e.printStackTrace();
		}
	}
	
	
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
//		System.exit(0);
		new Login().setVisible(true);
		this.dispose();
	}	

}
