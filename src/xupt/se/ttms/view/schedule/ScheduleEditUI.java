package xupt.se.ttms.view.schedule;

import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
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
import xupt.se.ttms.view.schedule.ScheduleAddUI.Task;
import xupt.se.ttms.view.studio.StudioAddUI;;

public class ScheduleEditUI extends ScheduleAddUI{
	/**
	 * 演出计划修改
	 */
	private static final long serialVersionUID = 1L;
	private Schedule stud;
	private int fstudioID = 0;
	private double firstprice=0;

	
	//进度
	private ProgressMonitor progressMonitor;
	int n=0;
	private Task task;
		 //内部类，添加座位
	public ScheduleEditUI(Schedule stu){
		initData(stu);
	}
	
	public void initData(Schedule stu) {
		this.setTitle("修改演出计划");
		if(null== stu){
			return;
		}

		fstudioID = stu.getStudio_id();
		firstprice=stu.getSched_ticket_price();
		String sname = (new StudioSrv().FetchOneById("studio_id = "+stu.getStudio_id())).getName();
		for(int i=0;i<Lstudio.size();i++){
			if(Lstudio.get(i).getName().equals(sname)){
				jstudioID.setSelectedIndex(i);
				break;
			}
		}
		
		String pname = (new PlaySrv().FetchOneById("play_id = "+stu.getPlay_id())).getName();
		for(int i=0;i<Lplay.size();i++){
			if(Lplay.get(i).getName().equals(pname)){
				jplayID.setSelectedIndex(i);
				break;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
//		pt.setText(sdf.format(stu.getSched_time()));
		Calendar c =  Calendar.getInstance();
		c.setTime(stu.getSched_time());
		year.setSelectedItem(c.get(Calendar.YEAR));
		month.setSelectedItem(c.get(Calendar.MONTH)+1);
		day.setSelectedItem(c.get(Calendar.DAY_OF_MONTH));
		hour.setSelectedItem(c.get(Calendar.HOUR_OF_DAY));
		minute.setSelectedItem(c.get(Calendar.MINUTE));
		pprice.setText(stu.getSched_ticket_price()+"");
		stud=stu;
		this.invalidate();
	}

	@Override
	protected void btnSaveClicked(){
		if (jstudioID!= null && jplayID!= null
				&&pprice.getText()!=null) {
			ScheduleSrv stuSrv = new ScheduleSrv();
			Schedule stu= stud;
			stu.setPlay_id((new PlaySrv().FetchOneById("play_name = '"+jplayID.getSelectedItem()+"'")).getId());
			stu.setSched_ticket_price(Double.parseDouble(pprice.getText()));
			stu.setStudio_id((new StudioSrv().FetchOneById("studio_name = '"+jstudioID.getSelectedItem()+"'")).getID());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 

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
				t = sdf.parse(time)
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
			long end = start + (ps.FetchOneById(" play_id = "+stu.getPlay_id()).getLength())*60*1000;
			
			
			List<Schedule> scheList = new ScheduleSrv().Fetch(" studio_id = "+stu.getStudio_id());
			for(Schedule s:scheList){
		
				if(s.getSched_id()==stu.getSched_id()){
					continue;
				}
				long PlayTime = ps.FetchOneById(" play_id = "+stu.getPlay_id()).getLength()*60*1000;
				long startTime = s.getSched_time().getTime();
				long endTime = startTime+PlayTime;
				
				
				if(((startTime<=start) && (endTime>=start))||((startTime>=start)
						&& (startTime<=endTime))){
					JOptionPane.showMessageDialog(null, "这时间段已被安排");
					return ;
					}
			}
			

			stu.setSched_time(t);
		
			stuSrv.modify(stu);
			Play p  = ps.FetchOneById(" play_id = "+stu.getPlay_id());
			p.setStatus(1);
			ps.modify(p);
			stud=stu;
			int pros=0;
			
			
			pros=new TicketSrv().Fetch(" sched_id = "+stu.getSched_id()).size()+new SeatSrv().Fetch(" studio_id = "+stu.getStudio_id()).size();
		
			
			
			 progressMonitor = new ProgressMonitor(	this,
                     "正在更新数据，请耐心等待！",
                     "", 0, pros);
			 
			 progressMonitor.setProgress(0);
			 task = new Task();
			 task.addPropertyChangeListener(this);
			 task.execute();

			
//			if(fstudioID != stu.getStudio_id()){
//				List<Seat> Ls = new SeatSrv().Fetch(" studio_id = "+stu.getStudio_id());
//				TicketSrv ts = new TicketSrv();
//				ts.delete(" sched_id = "+stu.getSched_id());
//				for(int i=0;i<Ls.size();i++){
//					Ticket t = new Ticket();
//					t.setPrice(stu.getSched_ticket_price());
//					t.setScheduleId(stu.getSched_id());
//					t.setSeatId(Ls.get(i).getId());
//					t.setStatus(0);
//					ts.add(t);
//				}
//			}
//			this.setVisible(false);
//			rst=true;
			
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
	
	
	class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
//            Random random = new Random();
            int progress = 0;
            setProgress(0);
            
            TicketSrv tsrv = new TicketSrv();
			List<Ticket> lt = tsrv.Fetch(" sched_id = "+stud.getSched_id());
			for(Ticket t:lt){
				t.setPrice(stud.getSched_ticket_price());
				tsrv.modify(t);
				if(progress>98){
					progress--;
				}else{
					progress++;
				}
				setProgress(progress);
			}
			
			if(fstudioID != stud.getStudio_id()){
				List<Seat> Ls = new SeatSrv().Fetch(" studio_id = "+stud.getStudio_id());
				TicketSrv ts = new TicketSrv();
				ts.delete(" sched_id = "+stud.getSched_id());
				for(int i=0;i<Ls.size();i++){
					Ticket t = new Ticket();
					t.setPrice(stud.getSched_ticket_price());
					t.setScheduleId(stud.getSched_id());
					t.setSeatId(Ls.get(i).getId());
					t.setStatus(0);
					ts.add(t);
					if(progress>98){
						progress--;
					}else{
						progress++;
					}
					setProgress(progress);
				}
			}
            
			setProgress(100);
			ScheduleEditUI.this.setVisible(false);
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
	                   
	                    JOptionPane.showMessageDialog(null, "修改成功");
	                }
	                
	            }
	        }
	}

	
}