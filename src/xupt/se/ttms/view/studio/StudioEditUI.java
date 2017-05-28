
package xupt.se.ttms.view.studio;


import javax.swing.JOptionPane;

import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.studio.StudioAddUI;;

public class StudioEditUI extends StudioAddUI{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Studio stud;
	private int row=-1,col=-1;

	public StudioEditUI(Studio stu){
		initData(stu);
	}
	
	public void initData(Studio stu) {
		if(null== stu){
			return;
		}
		row = stu.getRowCount();
		col = stu.getColCount();
		txtName.setText(stu.getName());
		txtRow.setText(Integer.toString(stu.getRowCount()));
		txtColumn.setText(Integer.toString(stu.getColCount()));
		txtIntro.setText(stu.getIntroduction());
		stud=stu;
		this.invalidate();
	}

	@Override
	protected void btnSaveClicked(){
		if (txtName.getText() != null && txtRow.getText() != null
				&& txtColumn.getText() != null) {
			StudioSrv stuSrv = new StudioSrv();
			Studio stu= stud;
			stu.setName(txtName.getText());
			stu.setRowCount(Integer.parseInt(txtRow.getText()));
			stu.setColCount(Integer.parseInt(txtColumn.getText()));
			stu.setIntroduction(txtIntro.getText());
			if(stuSrv.modify(stu)==1){
				if(row!=stu.getRowCount()||col!=stu.getColCount()){
					SeatSrv seatsrv = new SeatSrv();
					seatsrv.delete("studio_id = "+stu.getID());
					int studioID = stu.getID();
					for(int i=0;i<stu.getRowCount();i++){
						for(int j = 0;j<stu.getColCount();j++){
							seatsrv.add(new Seat(studioID,i,j));
							
						}
					}
				}
				this.setVisible(false);
				rst=true;
			}else{
				JOptionPane.showMessageDialog(null, "数据更新失败");
			}
			
			
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
	
}