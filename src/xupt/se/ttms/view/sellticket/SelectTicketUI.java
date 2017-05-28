package xupt.se.ttms.view.sellticket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import xupt.se.ttms.model.Play;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.model.Ticket;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.SellTicketHandler;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.service.TicketSrv;
import xupt.se.ttms.view.studio.StudioMgrUI;
import xupt.se.ttms.view.tmpl.MainUITmpl;

public class SelectTicketUI extends MainUITmpl{
	
	//中、右、上的面板
	private JPanel  middlePanel;
	private JPanel rightPanel;
	private JPanel upPanel;
	
	private Ticket[][] ticketArray;//对应演出计划的票
	private SellTicketHandler handler;//售票工具
	private JTextArea detail;//选票信息展示
	//票关联的演出计划、演出厅、剧目
	private Schedule sched;
	private Studio studio;
	private Play play;
	//演出厅行、列
	private int col=0;
	private int row=0;//行
	
	private List<Ticket> tList;
	private List<Seat> sList;
	
	public SelectTicketUI(Schedule s){
		this.sched=s;
		initContent();
	}
	
	//重新获取票的状态，刷新选票区
	private void getTickets(Schedule schedule){
		
		if(sched!=null){
			studio = new StudioSrv().FetchOneById("studio_id = "+sched.getStudio_id());
			row =  studio.getRowCount();
			col = studio.getColCount();
			play = new PlaySrv().FetchOneById(" play_id = "+sched.getPlay_id());
			tList = new TicketSrv().Fetch("sched_id = "+sched.getSched_id());
			sList = new SeatSrv().Fetch(" studio_id = "+studio.getID());
			
			for(Ticket t:tList){
				
				t.setPlayName(play.getName());
				t.setSchedule(sched);
				for(Seat s:sList){
					if(t.getSeatId()==s.getId()){
						t.setSeat(s);
					}
				}
				if(handler.isTicketSelected(t)){
    				t.setStatus(2);
    			}
				
			}
        	setMiddlePanel(studio.getRowCount(), studio.getColCount(), tList); 
        }
	}
	
	protected void initContent(){

		contPan.setLayout(new BorderLayout());
		handler = new SellTicketHandler();
		handler.makeNewSale();
		if(sched!=null){
			studio = new StudioSrv().FetchOneById("studio_id = "+sched.getStudio_id());
			row =  studio.getRowCount();
			col = studio.getColCount();
			play = new PlaySrv().FetchOneById(" play_id = "+sched.getPlay_id());
			tList = new TicketSrv().Fetch("sched_id = "+sched.getSched_id());
			sList = new SeatSrv().Fetch(" studio_id = "+studio.getID());
			
			for(Ticket t:tList){
				
				t.setPlayName(play.getName());
				t.setSchedule(sched);
				for(Seat s:sList){
					if(t.getSeatId()==s.getId()){
						t.setSeat(s);
					}
				}
				if(handler.isTicketSelected(t)){
    				t.setStatus(2);
    			}
				
			}
			middlePanel = new JPanel();
			middlePanel.setBounds(0, 0, 800, 500);
			if(tList!=null){
			setMiddlePanel(row,col,tList);
			}
			setUpPanel();
			setRightPanel();
		}
		Rectangle rect = contPan.getBounds();

		contPan.validate();
	}
	
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
		new SellTicketMgUI().setVisible(true);
		this.dispose();
	}	
	
	private void setUpPanel() {
		
		upPanel = new JPanel();
		upPanel.setLayout(new BorderLayout());
		JPanel filmPanel = new JPanel();
		if(play!=null){
			JLabel label = new JLabel("演出厅:"+studio.getName()+", 影片:"+play.getName());
			label.setFont(new Font("宋体",1,20));
		filmPanel.add(label);
		}
		upPanel.add(filmPanel, BorderLayout.CENTER);
		contPan.add(upPanel, BorderLayout.NORTH);
		contPan.add(new JPanel(), BorderLayout.SOUTH);
	}
	
	private void setMiddlePanel(int m, int n, List<Ticket> tickets) {
		if(middlePanel==null)
			middlePanel = new JPanel();
		else
			middlePanel.removeAll();
		JLabel lmainview = new JLabel();
		ImageIcon selectsite = new ImageIcon("resource/image/selectsite.png");
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
				if (ticketArray[i][j].getStatus()==0) {
					ticketArray[i][j].setStatus(2);
					site.setIcon(siteimggreen);
					handler.addTicket(ticketArray[i][j]);
					detail.setText(handler.getInfo());
				} else if (ticketArray[i][j].getStatus()==2) {
					ticketArray[i][j].setStatus(0);
					site.setIcon(siteimgwhite);
					handler.removeTicket(ticketArray[i][j]);
					detail.setText(handler.getInfo());
				}
			}
		};

		// 座位标示   -1:无座, 0:待销售   1:锁定   2:已选   9:卖出
		int[][] seats = new int[m+1][n+1];
		ticketArray = new Ticket[m+1][n+1];
		for (int i = 0; i < m+1; i++) {
			for (int j = 0; j < n+1; j++) {
				seats[i][j] = -1;
				ticketArray[i][j] = null;
			}
		}
		
		for(Ticket t : tickets){
			seats[t.getSeat().getRow()+1][t.getSeat().getColumn()+1] = t.getStatus();
			ticketArray[t.getSeat().getRow()+1][t.getSeat().getColumn()+1] = t;
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
						sites.add(new JLabel("  "));
					} else if (seats[i][j] == 0) {
						JButton site = new JButton(act);
						site.setBackground(Color.WHITE);
						site.setIcon(siteimgwhite);
						site.setName(i+","+j);
						sites.add(site);
					} else if (seats[i][j] == 2) {
						JButton site = new JButton(act);
						site.setBackground(Color.WHITE);
						site.setIcon(siteimggreen);
						site.setName(i+","+j);
						sites.add(site);
					} else{
						JButton site = new JButton();
						site.setBackground(Color.WHITE);
						site.setIcon(siteimgred);
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

	private void setRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		detail = new JTextArea("");
		JScrollPane scroll = new JScrollPane(detail);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		rightPanel.add(scroll, BorderLayout.CENTER);
		JPanel buttons = new JPanel();
		JButton sale = new JButton("出票");
		sale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(handler.doSale()){
					detail.setText("");					
					getTickets(sched);
					JOptionPane.showMessageDialog(null, "出票成功。");
				}else{
					JOptionPane.showMessageDialog(null, "出现错误，请重试。");					
				}
			}
		});
		JButton clear = new JButton("清除");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handler.clearSale();
				detail.setText("");
				getTickets(sched);
			}
		});
		buttons.add(sale);
		buttons.add(clear);
		rightPanel.add(buttons, BorderLayout.SOUTH);
		contPan.add(rightPanel, BorderLayout.EAST);
	}

}
