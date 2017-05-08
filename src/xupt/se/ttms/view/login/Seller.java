package xupt.se.ttms.view.login;
/**
 * 售票员UI
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import xupt.se.ttms.view.sellticket.QuitTicketUIPop;
import xupt.se.ttms.view.sellticket.SellTicketUI;
import xupt.se.ttms.view.tmpl.ImagePanel;
import xupt.se.ttms.view.tmpl.MainUITmpl;

public class Seller extends MainUITmpl{

	//To be override by the detailed business block interface 
		protected void btnExitClicked(ActionEvent Event){
//			System.exit(0);
			this.dispose();
			new Login().setVisible(true);;
		}	
	
	private static final long serialVersionUID = 1025028999012028956L;//序列化

	public Seller(){
		initContent();
	}

	@Override
	protected void initContent() {
		JPanel workPanel = new JPanel();
//		ImagePanel workPanel = new ImagePanel("resource/image/l3.jpg");
		workPanel.setLayout(null);
		workPanel.setBounds(0, 0, 1024, 600);
		
		JButton sale = new JButton();
		sale.setVerticalTextPosition(SwingConstants.BOTTOM);
		sale.setHorizontalTextPosition(SwingConstants.CENTER);
		sale.setIcon(new ImageIcon("resource/image/p3.jpg"));
		sale.setBackground(Color.WHITE);
		sale.setText(" 售 票 ");
		sale.setBounds(150, 100, 160, 160);
		
		sale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				SellTicketUI sellTicketUI = new SellTicketUI();
				sellTicketUI.setVisible(true);
				Seller.this.dispose();
			
			}
		});
		
		JButton refund = new JButton();
		refund.setVerticalTextPosition(SwingConstants.BOTTOM);
		refund.setHorizontalTextPosition(SwingConstants.CENTER);
		refund.setIcon(new ImageIcon("resource/image/p4.jpg"));
		refund.setBackground(Color.WHITE);
		refund.setText(" 退 票 ");
		refund.setBounds(450, 100, 160, 160);
		refund.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new QuitTicketUIPop().setVisible(true);
				Seller.this.dispose();
				
			}
		});
		
		JButton checkList = new JButton();
		checkList.setVerticalTextPosition(SwingConstants.BOTTOM);
		checkList.setHorizontalTextPosition(SwingConstants.CENTER);
		checkList.setIcon(new ImageIcon("resource/image/p5.jpg"));
		checkList.setBackground(Color.WHITE);
		checkList.setText("查看销售记录");
		checkList.setBounds(750, 100, 160, 160);
		checkList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JLabel jb = new JLabel();
		jb.setIcon(new ImageIcon("resource/image/study.gif"));
		jb.setBounds(450,200,1024,400);
		
		workPanel.add(sale);
		workPanel.add(refund);
		workPanel.add(checkList);
		workPanel.add(jb);
		workPanel.setBackground(Color.white);
		
		contPan.add(workPanel);
		contPan.validate();
		
	}

}
