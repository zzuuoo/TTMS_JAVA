package xupt.se.ttms.view.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.PlaySrv;
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
		
//		System.out.println(jstudioID.getSelectedItem());
		if(null== stu){
			return;
		}

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
//		jstudioID.setSelectedItem(anObject);
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
//			stu.setPlay_id(jplayID.getSelectedItem());
			stu.setPlay_id((new PlaySrv().FetchOneById("play_name = '"+jplayID.getSelectedItem()+"'")).getId());
			stu.setSched_ticket_price(Double.parseDouble(pprice.getText()));
			stu.setStudio_id((new StudioSrv().FetchOneById("studio_name = '"+jstudioID.getSelectedItem()+"'")).getID());
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