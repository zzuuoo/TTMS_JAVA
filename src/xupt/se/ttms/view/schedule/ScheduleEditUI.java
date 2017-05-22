package xupt.se.ttms.view.schedule;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

import xupt.se.ttms.model.Schedule;

import xupt.se.ttms.service.ScheduleSrv;

import xupt.se.ttms.view.schedule.ScheduleAddUI;;

public class ScheduleEditUI extends ScheduleAddUI{

	public ScheduleEditUI(Schedule sch) {
		this.setTitle("修改演出计划");
		initData(sch);
	}
// stid, plid, pt;
	private void initData(Schedule sch) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(sch.getSched_time());
		stid.setText(sch.getStudio_id()+"");
		plid.setText(sch.getPlay_id()+"");
		pt.setText(cld.get(Calendar.YEAR)+"-"
		+(cld.get(Calendar.MONTH)+1)+"-"+cld.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	protected void btnSaveClicked(){
		if (stid.getText() != null && plid.getText() != null
				&& pt.getText() != null) {
			ScheduleSrv schSrv = new ScheduleSrv();
			Schedule stu=new Schedule();
			stu.setStudio_id(Integer.parseInt(stid.getText()));
			stu.setPlay_id(Integer.parseInt(stid.getText()));
			DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				stu.setSched_time(DF.parse(pt.getText()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			schSrv.modify(stu);
			this.setVisible(false);
			rst=true;
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}

}
