package xupt.se.ttms.view.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.studio.StudioAddUI;;

public class ScheduleEditUI extends ScheduleAddUI{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Schedule stud;

	public ScheduleEditUI(Schedule stu){
		initData(stu);
	}
	
	public void initData(Schedule stu) {
		if(null== stu){
			return;
		}

//		stid.setText(stu.getStudio_id()+"");
//		plid.setText(stu.getPlay_id()+"");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
		pt.setText(sdf.format(stu.getSched_time()));
		pprice.setText(stu.getSched_ticket_price()+"");
		stud=stu;
		this.invalidate();
	}

	@Override
	protected void btnSaveClicked(){
		if (jstudioID!= null && jplayID!= null
				&& pt.getText() != null&&pprice.getText()!=null) {
			ScheduleSrv stuSrv = new ScheduleSrv();
			Schedule stu= stud;
//			stu.setPlay_id(Integer.parseInt(plid.getText()));
//			stu.setStudio_id(Integer.parseInt(stid.getText()));
//			stu.setSched_id(Integer.parseInt(stid.getText()));
			stu.setSched_ticket_price(Double.parseDouble(pprice.getText()));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			try {
				stu.setSched_time(sdf.parse(pt.getText()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("???");
			}
		
			stuSrv.modify(stu);
			this.setVisible(false);
			rst=true;
			
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
	
}