package xupt.se.ttms.view.schedule;

import javax.swing.JDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import xupt.se.ttms.model.Play;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.model.Ticket;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.service.TicketSrv;
import xupt.se.ttms.view.tmpl.*;

public class ScheduleAddUI extends PopUITmpl implements ActionListener {

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel studioid, playid, playtime,playprice;
	protected JTextField pt,pprice;
	protected JComboBox jstudioID,jplayID;
	List<Studio> Lstudio;
	List<Play> Lplay;

	public ScheduleAddUI() {
	
		
	}

	@Override
	protected void initContent(){
		this.setTitle("添加演出计划");

		Lstudio = getValidStudio();
		String []studioName =new String[Lstudio.size()];
		for(int i=0;i<Lstudio.size();i++){
			studioName[i]=Lstudio.get(i).getName();
		}
		Lplay = getValidPlay();
		String []playName = new String[Lplay.size()];
		for(int i =0;i<Lplay.size();i++){
			playName[i] = Lplay.get(i).getName();
		}
		
		
		studioid = new JLabel("演出厅：");
		studioid.setBounds(60, 30, 80, 30);
		contPan.add(studioid);
		jstudioID = new JComboBox(studioName);
		jstudioID.setBounds(150, 30, 200, 30);
		contPan.add(jstudioID);
		
//		stid = new JTextField();
//		stid.setBounds(150, 30, 120, 30);
//		contPan.add(stid);

		playid = new JLabel("剧目ID：");
		playid.setBounds(60, 80, 50, 30);
		contPan.add(playid);
		jplayID = new JComboBox(playName);
		jplayID.setBounds(150, 80, 200, 30);
		contPan.add(jplayID);
		
//		plid = new JTextField();
//		plid.setBounds(150, 80, 120, 30);
//		contPan.add(plid);

		playtime = new JLabel("演出时间：");
		playtime.setBounds(60, 130, 90, 30);
		contPan.add(playtime);
		pt = new JTextField();
		pt.setBounds(150, 130, 120, 30);
		contPan.add(pt);
		
		playprice = new JLabel("剧目票价：");
		playprice.setBounds(60, 180, 90, 30);
		contPan.add(playprice);
		pprice = new JTextField();
		pprice.setBounds(150, 180, 120, 30);
		contPan.add(pprice);

		btnSave = new JButton("保存");

		btnSave.addActionListener(this);
		btnSave.setBounds(60, 220, 60, 30);
		contPan.add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(180, 220, 60, 30);
		contPan.add(btnCancel);
//
//		ImageJPanel imageJP = new ImageJPanel(new ImageIcon(
//				"files/imgs/pencil.jpg").getImage());
//		imageJP.setBounds(360, 160, 100, 100);
//		imageJP.setLayout(null);
//		this.add(imageJP);
	}
	
	
	public boolean getReturnStatus(){
		   return rst;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			rst=false;
			this.onWindowClosing();
			this.dispose();
			System.gc();
//			getParent().setVisible(true);

		} else if (e.getSource() == btnSave) {
			btnSaveClicked();		//以前未调用，新添加的调用语句
		}

	}
	
	protected void btnSaveClicked(){
		
//		System.out.println(studioID.getSelectedItem().toString());
		
		if (jstudioID!=null&& jplayID != null
				&& pt.getText() != null&&pprice.getText()!=null) {
			ScheduleSrv stuSrv = new ScheduleSrv();
			Schedule sch=new Schedule();
			int pid=0,sid=0;
//			try{
//			pid = Integer.parseInt(plid.getText());
//			sid = Integer.parseInt(stid.getText());
//			}catch (NumberFormatException e){
//				e.printStackTrace();
//			}
			sid = Lstudio.get(jstudioID.getSelectedIndex()).getID();
			pid = Lplay.get(jplayID.getSelectedIndex()).getId();
			sch.setPlay_id(pid);
			sch.setStudio_id(sid);
			sch.setSched_ticket_price(new Double(pprice.getText()));
			DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
			try {
				sch.setSched_time(DF.parse(pt.getText()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			

			int scheduleID = stuSrv.add(sch);
			if(scheduleID!= 0 ){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ScheduleSrv schSrv = new ScheduleSrv();
//				Schedule sc = schSrv.FetchOne(" studio_id = "+sch.getStudio_id()+" and "
//						+" play_id = "+sch.getPlay_id()+" and "+"sched_time = "
//						+sdf.format(sch.getSched_time())+" and "
//						+" sched_ticket_price = "+sch.getSched_ticket_price());
				Schedule sc = schSrv.FetchOne(" sched_id = "+scheduleID);
				List<Seat> Ls = new SeatSrv().Fetch(" studio_id = "+sc.getStudio_id());
//				public Ticket(int seatId, int scheduleId, float price, int status) {
				TicketSrv ts = new TicketSrv();
				for(int i=0;i<Ls.size();i++){
					Ticket t = new Ticket();
					t.setPrice(sc.getSched_ticket_price());
					t.setScheduleId(sc.getSched_id());
					t.setSeatId(Ls.get(i).getId());
					t.setStatus(0);
					ts.add(t);
				}
				
				
				this.setVisible(false);
				rst=true;
			}
//			stuSrv.add(sch);
//			this.setVisible(false);
//			System.gc();
//			rst=true;
//			getParent().setVisible(true);
		} else {
			
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
	
	protected List<Studio> getValidStudio(){
		StudioSrv stuSrv = new StudioSrv();
		return stuSrv.FetchAll();
	}
	
	protected List<Play> getValidPlay(){
		PlaySrv stuSrv = new PlaySrv();
		return stuSrv.Fetch("play_status != -1");
	}
	
	
	
	protected void onWindowClosing(){
		//System.exit(0);
		this.dispose();
}
}
