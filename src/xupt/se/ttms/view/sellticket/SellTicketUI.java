package xupt.se.ttms.view.sellticket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
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
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import xupt.se.ttms.model.Play;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.model.Ticket;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.SellTicketHandler;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.service.TicketSrv;
import xupt.se.ttms.view.login.Login;
import xupt.se.ttms.view.login.Seller;
import xupt.se.ttms.view.tmpl.MainUITmpl;
/**
 * 售票的UI
 * @author zuo
 *
 */
public class SellTicketUI extends MainUITmpl{


	private static final long serialVersionUID = -8069838656058091382L;
	private JTabbedPane tabPane;
	private JPanel salePanel;
	private JPanel upPanel;
	private JPanel leftPanel;
	private JPanel middlePanel;
	private JPanel rightPanel;
	private JButton left;
	private JButton right;
	
	private SellTicketHandler handler;
	private Play curPlay;
	private Schedule curSchedule;
	private DefaultMutableTreeNode curNode;
	private List<Play> scheduledPlay;
	private JTree tree;
	private JTextArea detail;
	private Ticket[][] ticketArray;

	@Override
	protected void initContent() {
		tabPane = new JTabbedPane();
		tabPane.setBounds(0, 0, 1024, 590);

		salePanel = new JPanel();
		salePanel.setLayout(new BorderLayout());

		handler = new SellTicketHandler();
		handler.makeNewSale();
		
		setUpPanel();
//		if(scheduledPlay.size()>0){
//			setLeftPanel(scheduledPlay.get(0).getId(),scheduledPlay.get(0).getName());
//			curPlay = scheduledPlay.get(0);
//		}
//		else
//			setLeftPanel(0,"【无信息】");
		
		setRightPanel();

		tabPane.addTab("正在上映", salePanel);
		tabPane.addTab("即将上映", new JLabel());
		tabPane.addTab("全部电影", new JLabel());
		contPan.add(tabPane);
		contPan.validate();
		
		
		/**
		 *这是座位测试数据。。。
		 */
		List<Ticket> tickets = new ArrayList<>();
		for(int i =1;i<9;i++){
			
			
			for(int j =1 ;j<9;j++){
			Ticket t = new Ticket();
			t.setId(i*j);
			t.setPlayName("test"+(i*j));
			t.setPrice(30+i);
			t.setStatus(0);
			t.setSeatId(i+2);
			Seat s = new Seat();
//			t.setScheduleId(1);
			s.setId(i*j);
			s.setRow(i);
			s.setColumn(j);
			t.setSeat(s);
			
			tickets.add(t);
			
			}
		}
		setMiddlePanel(8,8, tickets); 
		

	}

	private void setUpPanel() {
		PlaySrv service = new PlaySrv();
		scheduledPlay = service.selectScheduledPlay("");
		upPanel = new JPanel();
		upPanel.setLayout(new BorderLayout());
		left = new JButton(new ImageIcon("resource/image/left.png"));
		upPanel.add(left, BorderLayout.WEST);
		right = new JButton(new ImageIcon("resource/image/right.png"));
		upPanel.add(right, BorderLayout.EAST);
		JPanel filmPanel = new JPanel();
		filmPanel.setLayout(new GridLayout(1, 6));
		JButton btn1 = new JButton(new ImageIcon("resource/image/f1.jpg"));
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(scheduledPlay.size()>0){
					setLeftPanel(scheduledPlay.get(0).getId(),scheduledPlay.get(0).getName());
					curPlay = scheduledPlay.get(0);
				}
			}
		});
		filmPanel.add(btn1);
		
		JButton btn2 = new JButton(new ImageIcon("resource/image/f2.jpg"));
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(scheduledPlay.size()>1){
					setLeftPanel(scheduledPlay.get(1).getId(),scheduledPlay.get(1).getName());
					curPlay = scheduledPlay.get(1);
				}
			}
		});
		filmPanel.add(btn2);

		filmPanel.add(new JButton(new ImageIcon("resource/image/f3.jpg")));
		filmPanel.add(new JButton(new ImageIcon("resource/image/f4.jpg")));
		filmPanel.add(new JButton(new ImageIcon("resource/image/f5.jpg")));
		filmPanel.add(new JButton(new ImageIcon("resource/image/f6.jpg")));
		upPanel.add(filmPanel, BorderLayout.CENTER);
		salePanel.add(upPanel, BorderLayout.NORTH);
	}

	private void setLeftPanel(int play_id, String play_name) {
		if(leftPanel==null)
			leftPanel = new JPanel();
		else
			leftPanel.removeAll();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(play_name);
		ScheduleSrv service = new ScheduleSrv();
//		List<Schedule> list = service.Fetch("play_id="+play_id);
		List<Schedule> list = service.Fetch("play_id="+3);
		if (list.size() > 0) {
			List<String> dates = new ArrayList<String>();
			for (Schedule i : list) {
				String s = DateFormat.getDateInstance().format(i.getSched_time());
				if(!dates.contains(s)){
					dates.add(s);
				}
			}
			for(String i:dates){
				root.add(new DefaultMutableTreeNode(i));									
			}
			for (Schedule i : list) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)root.getFirstChild();
				for(int j=0; j<dates.size(); j++){
					if(node.getUserObject().toString().equals(DateFormat.getDateInstance().format(i.getSched_time()))){
						node.add(new DefaultMutableTreeNode(i));
						break;
					}else
						node = node.getNextSibling();
				}
			}
		}
		tree = new JTree(root);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			
		    public void valueChanged(TreeSelectionEvent e) {		    	
		        DefaultMutableTreeNode selectedNode=(DefaultMutableTreeNode) tree.getLastSelectedPathComponent();  
		        curNode = selectedNode;
		        getTickets(selectedNode);
		    }  
		});  
		leftPanel.add(tree);
		salePanel.add(leftPanel, BorderLayout.WEST);
		leftPanel.updateUI();
	}
	
	private void getTickets(DefaultMutableTreeNode node){
        if(node!=null && node.isLeaf()){
        	int m=0, n=0;
        	Schedule schedule = (Schedule)node.getUserObject();
        	curSchedule = schedule;
        	//System.out.println(schedule.getSched_id());
        	TicketSrv ticketSrv = new TicketSrv();
        	SeatSrv seatSrv = new SeatSrv();
        	StudioSrv studioSrv = new StudioSrv();
        	List<Ticket> tickets = ticketSrv.Fetch("sched_id = "+ schedule.getSched_id());
//        	List<Ticket> tickets = ticketSrv.Fetch("sched_id = "+ 10);
        	for(Ticket t : tickets){
        		List<Seat> tmp = seatSrv.Fetch("seat_id = " + t.getSeatId());
        		if(tmp.size()>0){
        			t.setPlayName(curPlay.getName());
        			t.setSchedule(curSchedule);
        			t.setSeat(tmp.get(0));
        			if(m==0){
        				List<Studio> studios = studioSrv.Fetch("studio_id = " + tmp.get(0).getStudioId());
        				if(studios.size()>0){
        					m = studios.get(0).getRowCount();
        					n = studios.get(0).getColCount();
        				}
        			}
        			if(handler.isTicketSelected(t)){
        				t.setStatus(2);
        			}
        		}
        	}
        	setMiddlePanel(m, n, tickets); 
        }
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
			seats[t.getSeat().getRow()][t.getSeat().getColumn()] = t.getStatus();
			ticketArray[t.getSeat().getRow()][t.getSeat().getColumn()] = t;
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
		salePanel.add(middlePanel, BorderLayout.CENTER);
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
					getTickets(curNode);
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
				getTickets(curNode);
			}
		});
		buttons.add(sale);
		buttons.add(clear);
		rightPanel.add(buttons, BorderLayout.SOUTH);
		salePanel.add(rightPanel, BorderLayout.EAST);
	}
	
	
	
	
//	byte b = 128;
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){

		new Seller().setVisible(true);
		this.dispose();
		
	}	
	
}
