
package xupt.se.ttms.view.studio;


import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.studio.StudioAddUI;

public class StudioEditUI extends StudioAddUI{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Studio stud;
	private int studioID = 0,row=-1,col=-1;
	int n=1;
	//进度
	private ProgressMonitor progressMonitor;
	private Task task;
	 //内部类，添加座位
	

	public StudioEditUI(Studio stu){
		initData(stu);
	}
	
	public void initData(Studio stu) {
		this.setTitle("修改演出厅");
		if(null== stu){
			return;
		}
		row = stu.getRowCount();
		col = stu.getColCount();
		studioID =stu.getID();
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
					
					row=stu.getRowCount();
					col=stu.getColCount();
					 progressMonitor = new ProgressMonitor(	this,
		                     "正在更改数据，请耐心等待！",
		                     "", 0, row*col);
					 progressMonitor.setMaximum(row*col);
					 
					 progressMonitor.setProgress(0);
					 task = new Task();
					 task.addPropertyChangeListener(this);
					 task.execute();
					
					
					

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
	
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
		 if ("progress" == evt.getPropertyName() ) {
//	            int progress = (Integer) evt.getNewValue();
			 	n=n+1;
	            progressMonitor.setProgress(n);
//	            progressMonitor.setNote(message);
	            if (progressMonitor.isCanceled() || task.isDone()) {
	                Toolkit.getDefaultToolkit().beep();
	                if (progressMonitor.isCanceled()) {
	                    task.cancel(true);
	                    JOptionPane.showMessageDialog(null, "已取消");
	                   
	                } else {
	                	progressMonitor.setProgress(col*row);
	                    JOptionPane.showMessageDialog(null, "已完成修改");
	                }
	                
	            }
	        }
	}
	class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
//            Random random = new Random();
            int progress = 0;
            setProgress(0);
			SeatSrv seatsrv = new SeatSrv();
			seatsrv.delete("studio_id = "+studioID);
			System.out.println(row+"--"+col);
			for(int i=0;i<row;i++){
				for(int j = 0;j<col;j++){
					seatsrv.add(new Seat(studioID,i,j));	
					setProgress(progress);
					if(progress>98){
						progress--;
					}else{
						progress++;
					}
				}
			}
			setProgress(100);
			StudioEditUI.this.setVisible(false);
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
	
}