package xupt.se.ttms.view.schedule;

import javax.swing.JDialog;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

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

public class ScheduleAddUI extends PopUITmpl implements ActionListener,PropertyChangeListener {

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel studioid, playid, playtime,playprice;
	protected JTextField pprice;
	protected JComboBox jstudioID,jplayID;//剧目和演出厅的选择器
	protected JComboBox year,month,day,minute,hour;
	protected JLabel year1,month1,day1,minute1,hour1;
//	protected JCalendarChooser jc;
	//可供选择的演出厅和剧目
	List<Studio> Lstudio;
	List<Play> Lplay;
	
	private Schedule sc=null;
	
	//进度
		private ProgressMonitor progressMonitor;
		int n=1;
		 private Task task;
		 //内部类，添加座位

	public ScheduleAddUI() {
		
	}

	public int getDayCount(int year,int month){
		Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
//		return d;
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
//		jstudioID.setf
		jstudioID.setBounds(150, 30, 200, 30);
		contPan.add(jstudioID);
		
		playid = new JLabel("剧目ID：");
		playid.setBounds(60, 80, 50, 30);
		contPan.add(playid);
		jplayID = new JComboBox(playName);
		jplayID.setBounds(150, 80, 200, 30);
		contPan.add(jplayID);	

		playtime = new JLabel("演出时间：");
		playtime.setBounds(60, 130, 90, 30);
		contPan.add(playtime);
//		pt = new JTextField();
//		protected JComboBox year,month,day,minute,hour;
		Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		Integer [] intYear = new Integer[100];
		for(int i=y,j=0;i<y+100;i++){
			intYear[j]=i;
			j++;
		}
//		for()
		year = new JComboBox(intYear);
		year.setBounds(150, 130, 80, 30);
		year1 = new JLabel("年");
		year1.setBounds(250, 130, 20, 30);
		
		Integer [] intMonth={1,2,3,4,5,6,7,8,9,10,11,12};
		month = new JComboBox(intMonth);
		month.setBounds(290, 130, 60, 30);
		month1 = new JLabel("月");
		month1.setBounds(370, 130, 20, 30);
		
		Integer [] intDay={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,
				18,19,20,21,22,23,24,25,26,27,28,29,30,31};
		day = new JComboBox(intDay);
		day.setBounds(390, 130, 60, 30);
		day1 = new JLabel("日");
		day1.setBounds(470, 130, 20, 30);
		
		Integer [] intHour={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,
				18,19,20,21,22,23};
		hour = new JComboBox(intHour);
		hour.setBounds(510, 130, 60, 30);
		hour1 = new JLabel("时");
		hour1.setBounds(590, 130, 20, 30);
		
		Integer [] intMinute={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,
				18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,
				37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,
				58,59};
		minute = new JComboBox(intMinute);
		minute.setBounds(630, 130, 60, 30);
		minute1 = new JLabel("分");
		minute1.setBounds(710, 130, 20, 30);
//		pt.setBounds(150, 130, 120, 30);
//		contPan.add(pt);
		contPan.add(year);
		contPan.add(year1);
		contPan.add(month);
		contPan.add(month1);
		contPan.add(day);
		contPan.add(hour);
		contPan.add(minute);
		contPan.add(day1);
		contPan.add(hour1);
		contPan.add(minute1);
		
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

	}
	
	//返回操作结果
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

		} else if (e.getSource() == btnSave) {
			btnSaveClicked();		
		}

	}
	
	protected void btnSaveClicked(){
		if (jstudioID!=null&& jplayID != null
				&&pprice.getText()!=null) {
			ScheduleSrv stuSrv = new ScheduleSrv();
			Schedule sch=new Schedule();
			int pid=0,sid=0;
			sid = Lstudio.get(jstudioID.getSelectedIndex()).getID();
			pid = Lplay.get(jplayID.getSelectedIndex()).getId();
			sch.setPlay_id(pid);
			sch.setStudio_id(sid);
			sch.setSched_ticket_price(new Double(pprice.getText()));
			DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
			String time = year.getSelectedItem()+"-"
					+month.getSelectedItem()+"-"+day.getSelectedItem()+" "
					+hour.getSelectedItem()+":"+minute.getSelectedItem();
			int dayCount = getDayCount(Integer.parseInt(year.getSelectedItem()+""),Integer.parseInt(month.getSelectedItem()+""));
			if(Integer.parseInt(day.getSelectedItem()+"")>dayCount){
				JOptionPane.showMessageDialog(null, "日期天数超出当月范围");
				System.out.println("日期天数超出当月范围");
				return ;
			}
			Date t=null;
			try {
//				sch.setSched_time(DF.parse(pt.getText()));
				t = DF.parse(time)
;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(t.getTime()<new Date().getTime()){
				JOptionPane.showMessageDialog(null, "不能用过去的时间");
				return;
			}
			
			PlaySrv ps = new PlaySrv();
			long start =  t.getTime();
			long end = start + (ps.FetchOneById(" play_id = "+sch.getPlay_id()).getLength())*60*1000;
			
			
			List<Schedule> scheList = new ScheduleSrv().Fetch(" studio_id = "+sch.getStudio_id());
			for(Schedule s:scheList){
		
				long PlayTime = ps.FetchOneById(" play_id = "+sch.getPlay_id()).getLength()*60*1000;
				long startTime = s.getSched_time().getTime();
				long endTime = startTime+PlayTime;
				
				
				if(((startTime<=start) && (endTime>=start))||((startTime>=start)
						&& (startTime<=endTime))){
					JOptionPane.showMessageDialog(null, "这时间段已被安排");
					return ;
					}
			}
			

			sch.setSched_time(t);

			int scheduleID = stuSrv.add(sch);
			if(scheduleID!= 0 ){
				ScheduleSrv schSrv = new ScheduleSrv();
				sc = schSrv.FetchOne(" sched_id = "+scheduleID);
				List<Seat> Ls = new SeatSrv().Fetch(" studio_id = "+sc.getStudio_id());

					
				 progressMonitor = new ProgressMonitor(	this,
	                     "正在添加数据，请耐心等待！",
	                     "", 0, Ls.size());
				 
				 progressMonitor.setProgress(0);
				 task = new Task();
				 task.addPropertyChangeListener(this);
				 task.execute();
			}
		} else {
			
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
	//获取有效演出厅
	protected List<Studio> getValidStudio(){
		StudioSrv stuSrv = new StudioSrv();
		return stuSrv.FetchAll();
	}
	//获取有效剧目
	protected List<Play> getValidPlay(){
		PlaySrv stuSrv = new PlaySrv();
		return stuSrv.Fetch("play_status != -1");
	}
	//窗口关闭按钮
	protected void onWindowClosing(){
		this.dispose();
}
	
	class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
//            Random random = new Random();
            int progress = 0;
            setProgress(0);
            PlaySrv ps = new PlaySrv();
			Play p  = ps.FetchOneById(" play_id = "+sc.getPlay_id());
			p.setStatus(1);
			ps.modify(p);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			ScheduleSrv schSrv = new ScheduleSrv();
			List<Seat> Ls = new SeatSrv().Fetch(" studio_id = "+sc.getStudio_id());
			TicketSrv ts = new TicketSrv();
			for(int i=0;i<Ls.size();i++){
				Ticket t = new Ticket();
				t.setPrice(sc.getSched_ticket_price());
				t.setScheduleId(sc.getSched_id());
				t.setSeatId(Ls.get(i).getId());
				t.setStatus(0);
				ts.add(t);
				System.out.println("添加票中.."+t.getScheduleId());
				if(progress>98){
					progress--;
				}else{
					progress++;
				}
				setProgress(progress);
			}
			
			setProgress(100);
			ScheduleAddUI.this.setVisible(false);
			rst=true;
		
            return null;
        }
 
        @Override
        public void done() {
//            Toolkit.getDefaultToolkit().beep();
//            JOptionPane.showMessageDialog(null, "已完成");
            progressMonitor.setProgress(0);
            progressMonitor.close();
            
        }
    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		 if ("progress" == evt.getPropertyName() ) {
//	            int progress = (Integer) evt.getNewValue();
	            progressMonitor.setProgress(n);
	            n++;
//	            progressMonitor.setNote(message);
	            if (progressMonitor.isCanceled() || task.isDone()) {
	                Toolkit.getDefaultToolkit().beep();
	                if (progressMonitor.isCanceled()) {
	                    task.cancel(true);
	                    JOptionPane.showMessageDialog(null, "已取消");
	                   
	                } else {
	                   
	                    JOptionPane.showMessageDialog(null, "添加成功");
	                }
	                
	            }
	        }
	}

	
	
}
