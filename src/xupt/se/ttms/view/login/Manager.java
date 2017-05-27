package xupt.se.ttms.view.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
		
		JPanel workPanel = new JPanel();
//		ImagePanel workPanel = new ImagePanel("resource/image/l3.jpg");
		workPanel.setLayout(null);
		workPanel.setBounds(0, 0, 1024, 600);
		workPanel.setBackground(Color.white);
		
//		JPanel workPanel2 = new JPanel();
//		workPanel2.setLayout(null);
////		workPanel2.setBounds(0, 0, 1024, 240);
//		workPanel2.setSize( 1024, 240);
//		workPanel2.add(new JLabel(" 此广告位招租 "));
//		workPanel2.setBackground(Color.white);
//		
		
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
		
		palyManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {

				new ScheduleMgUI().setVisible(true);
				Manager.this.dispose();
			
			}
		});
		
//		JButton seatmanager = new JButton();
//		seatmanager.setVerticalTextPosition(SwingConstants.BOTTOM);
//		seatmanager.setHorizontalTextPosition(SwingConstants.CENTER);
//		seatmanager.setIcon(new ImageIcon("resource/image/seatm.png"));
//		seatmanager.setBackground(Color.WHITE);
//		seatmanager.setText(" 座位管理 ");
//		seatmanager.setBounds(550, 100, 160, 160);
//		seatmanager.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				new StudioSeat().setVisible(true);
//				Manager.this.dispose();
//				
//			}
//		});
		
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
//		workPanel.add(seatmanager);
		workPanel.add(checkC);
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
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
//		System.exit(0);
		new Login().setVisible(true);
		this.dispose();
	}	

}
