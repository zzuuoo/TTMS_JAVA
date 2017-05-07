package xupt.se.ttms.view.studio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import xupt.se.ttms.view.login.Manager;
import xupt.se.ttms.view.tmpl.MainUITmpl;

public class StudioSeat extends MainUITmpl{
//	
//	private int col;
//	private int row;
//	
//	public StudioSeat(int col,int row){
//		this.col=col;
//		this.row=row;
//	}
	
	public StudioSeat(){
		
	}
	
	
	//To be override by the detailed business block interface 
		protected void onWindowClosing(){
			System.exit(0);
		}
		
		
		//To be override by the detailed business block interface 
		protected void initContent(){
			Rectangle rect = contPan.getBounds();
			JPanel jp =new JPanel();
//			jp.setLayout(new GridLayout(7,3));
			jp.setSize(1024, 500);
			for(int i=0;i<80;i++){
//				JButton jb = new JButton();
				JLabel jb = new JLabel();
//				jb.setBounds(0, 0, 64, 64);
				jb.setBackground(Color.white);
				jb.setIcon(new ImageIcon("resource/image/seat_g.png"));
				jp.add(jb);
//				jp.setOpaque(false);
			}
			
			JTabbedPane jtb = new JTabbedPane(JTabbedPane.LEFT);
			
			JPanel wjp = new JPanel();
			wjp.setLayout(new BorderLayout());
			JLabel jl1 = new JLabel("A");
			jl1.setBounds(rect.width/2, 10, rect.width, 30);
//			wjp.add(BorderLayout.NORTH,jl1);
			wjp.add(BorderLayout.CENTER, jp);
			
//			JPanel wjp2 = new JPanel();
//			wjp2.setLayout(new BorderLayout());
//			JLabel jl2 = new JLabel("A");
//			jl2.setBounds(0, 5, rect.width, 30);
//			wjp2.add(BorderLayout.NORTH,jl2);
//			wjp2.add(BorderLayout.CENTER, jp);
//			
//			JPanel wjp3 = new JPanel();
//			wjp3.setLayout(new BorderLayout());
//			JLabel jl3 = new JLabel("A");
//			jl3.setBounds(0, 5, rect.width, 30);
//			wjp3.add(BorderLayout.NORTH,jl3);
//			wjp3.add(BorderLayout.CENTER, jp);
			
			
			
			jtb.setBounds(0, 0, 1024, 600);
			
//			
			jtb.addTab("A厅",wjp );
//			jtb.addTab("B厅",wjp2 );
//			jtb.addTab("C厅",wjp3 );
			jtb.setFont(new Font(" ",1,20));
			
			contPan.add(jtb);
			contPan.validate();
			
		}
		
		//To be override by the detailed business block interface 
		protected void btnExitClicked(ActionEvent Event){
			this.dispose();
			new Manager().setVisible(true);
		}	
	
}
