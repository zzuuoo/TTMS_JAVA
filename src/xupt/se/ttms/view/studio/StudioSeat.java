package xupt.se.ttms.view.studio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.model.Ticket;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.SellTicketHandler;
import xupt.se.ttms.view.login.Manager;
import xupt.se.ttms.view.tmpl.MainUITmpl;

public class StudioSeat extends MainUITmpl implements ActionListener{
//	

	private JPanel  middlePanel;//座位区
	private Studio stu;
	private JButton btnSave,btnCancle;
	int[][] seats;

	
	public StudioSeat(Studio stu){
		setMiddlePanel(stu);
		this.stu = stu;
	
	}
	public StudioSeat(){
		
	}
	
	//To be override by the detailed business block interface 
		protected void onWindowClosing(){
			System.exit(0);
		}
		
		
		//To be override by the detailed business block interface 
		protected void initContent(){
			
			Rectangle rect = contPan.getBounds();
			middlePanel = new JPanel();
			middlePanel.setBounds(0, 0, 1024, 600);

			btnSave = new JButton("保存");
			btnSave.setBounds(400, rect.height - 100, 60, 30);
			btnSave.addActionListener(this);
			contPan.add(btnSave);

			btnCancle = new JButton("取消");
			btnCancle.setBounds(500, rect.height - 100, 60, 30);
			btnCancle.addActionListener(this);
			
			contPan.add(btnCancle);	
			contPan.add(middlePanel);
			contPan.validate();
					
		}
		
		//To be override by the detailed business block interface 
		protected void btnExitClicked(ActionEvent Event){
			new StudioMgrUI().setVisible(true);
			this.dispose();
		}	
	
		private void setMiddlePanel(Studio stu) {
			int m =stu.getRowCount(),n=stu.getColCount();
			seats = new int[m+1][n+1];
			if(middlePanel==null)
				middlePanel = new JPanel();
			else
				middlePanel.removeAll();
			JLabel lmainview = new JLabel();

			ImageIcon selectsite = new ImageIcon("resource/image/selectsite1.png");
			lmainview.setIcon(selectsite);

			JPanel sites = new JPanel();
			GridLayout gridLayout = new GridLayout(m+1, n+1);
			gridLayout.setHgap(8);
			gridLayout.setVgap(3);
			sites.setLayout(gridLayout);
			sites.setOpaque(false); // 设置背景为透明
			sites.setBounds(105, 120, 510, 300);

			final ImageIcon siteimgwhite = new ImageIcon("resource/image/white.png");
			final ImageIcon siteimggreen = new ImageIcon("resource/image/green.png");
			final ImageIcon siteimgred = new ImageIcon("resource/image/red.jpg");

			Action act = new AbstractAction() {
				private static final long serialVersionUID = -144569051730123316L;

				public void actionPerformed(ActionEvent e) {
					JButton site = (JButton) e.getSource();
					String name = site.getName();
					String tmp[] = name.split(",");
					int i = Integer.valueOf(tmp[0]);
					int j = Integer.valueOf(tmp[1]);
					if(seats[i][j]==-1){
						seats[i][j]=0;
						site.setIcon(siteimgwhite);
					}else{
					seats[i][j]=-1;
					site.setIcon(null);
					}
				}
			};

			// 座位标示   -1:无座, 0:待销售   1:锁定   2:已选   9:卖出
			for (int i = 0; i < m+1; i++) {
				for (int j = 0; j < n+1; j++) {
					seats[i][j] = -1;
				}
			}

			List<Seat> Ls = new SeatSrv().Fetch(" studio_id = "+stu.getID());
			if(Ls!=null){
			for(int i=0;i<Ls.size();i++){
				Seat s = Ls.get(i);
				seats[s.getRow()+1][s.getColumn()+1]=0;
			}
			}
			for (int i = 0; i < m+1; i++) {
				for (int j = 0; j < n+1; j++) {
					if(i==0){
						if(j==0)
							sites.add(new JLabel("  "));
						else
							sites.add(new JLabel(" " + j + "座"));
					}else if(j==0){
						if(i>0)
							sites.add(new JLabel(i + "排"));
					}else{
						if (seats[i][j] == -1) {
							JButton site = new JButton(act);
							site.setBackground(Color.WHITE);
							site.setIcon(null);
							site.setName(i+","+j);
							sites.add(site);
						} else{
							JButton site = new JButton(act);
							site.setBackground(Color.WHITE);
							site.setIcon(siteimgwhite);
							site.setName(i+","+j);
							sites.add(site);
						} 
					}
				}
			}

			lmainview.add(sites);
			middlePanel.add(lmainview);
			contPan.add(middlePanel, BorderLayout.CENTER);
			middlePanel.updateUI();
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == btnCancle) {
				new StudioMgrUI().setVisible(true);
				this.dispose();
			} else if (e.getSource() == btnSave) {
				btnSaveClicked();
			}
			
		}
		private void btnSaveClicked() {
			// TODO Auto-generated method stub
			SeatSrv seatsrv = new SeatSrv();
			
			if(seatsrv.delete(" studio_id = "+stu.getID())!=0){
			for(int i=1;i<stu.getRowCount()+1;i++ ){
				for(int j = 1 ;j<stu.getColCount()+1;j++){
					if(seats[i][j]==0){
						seatsrv.add(new Seat(stu.getID(),i-1,j-1));
					}
				}
			}
			}else{
				JOptionPane.showMessageDialog(null, "该演出厅正在使用，不能修改");
			}
			new StudioMgrUI().setVisible(true);
			this.dispose();
		}
}
