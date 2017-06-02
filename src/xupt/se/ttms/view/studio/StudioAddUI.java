package xupt.se.ttms.view.studio;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.tmpl.*;

public class StudioAddUI extends PopUITmpl implements ActionListener,PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel lblName, lblRow, lblColumn, lblIntro;
	protected JTextField txtName, txtRow, txtColumn;
	protected JTextArea txtIntro;
	
	
	//方便添加座位而加的数据
	private int studioID=0,row=-1,col=-1;
	int n=0;
	 

	//进度
	private ProgressMonitor progressMonitor;
	
	 private Task task;
	 //内部类，添加座位
	

	@Override
	protected void initContent(){
		this.setTitle("添加演出厅");

		
		
		lblName = new JLabel("演出厅名称:");
		lblName.setBounds(60, 30, 80, 30);
		contPan.add(lblName);
		txtName = new JTextField();
		txtName.setBounds(150, 30, 400, 30);
		contPan.add(txtName);

		lblRow = new JLabel("座位  行数:");
		lblRow.setBounds(60, 80, 80, 30);
		contPan.add(lblRow);
		txtRow = new JTextField();
		txtRow.setBounds(150, 80, 120, 30);
		contPan.add(txtRow);

		lblColumn = new JLabel("座位  列数:");
		lblColumn.setBounds(340, 80, 80, 30);
		contPan.add(lblColumn);
		txtColumn = new JTextField();
		txtColumn.setBounds(430, 80, 120, 30);
		contPan.add(txtColumn);
		
		lblIntro = new JLabel("演出厅简介:");
		lblIntro.setBounds(60, 130, 80, 30);
		contPan.add(lblIntro);
		txtIntro = new JTextArea();
		txtIntro.setLineWrap(true);
		txtIntro.setBounds(150, 130, 400, 100);
		contPan.add(txtIntro);

		
		btnSave = new JButton("保存");
		btnSave.addActionListener(this);
		btnSave.setBounds(60, 280, 60, 30);
		contPan.add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(180, 280, 60, 30);
		contPan.add(btnCancel);

	}
	
	public boolean getReturnStatus(){
		   return rst;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			rst=false;
			this.setVisible(false);
		} else if (e.getSource() == btnSave) {
			 btnSaveClicked();
		}
	}
	protected  void btnSaveClicked(){
		if (txtName.getText() != null && txtRow.getText() != null
				&& txtColumn.getText() != null) {
			StudioSrv stuSrv = new StudioSrv();
			Studio stu=new Studio();
			stu.setName(txtName.getText());
			stu.setRowCount(Integer.parseInt(txtRow.getText()));
			stu.setColCount(Integer.parseInt(txtColumn.getText()));
			stu.setIntroduction(txtIntro.getText());
			
			studioID = stuSrv.add(stu);
			System.out.println("演出厅ID："+studioID);
			row = stu.getRowCount();
			col = stu.getColCount();
			if(studioID!=0){
				this.setVisible(false);
				rst=true;				
				 progressMonitor = new ProgressMonitor(	this,
	                     "正在添加数据，请耐心等待！",
	                     "", 0, row*col);
				 
				 progressMonitor.setProgress(0);
				 task = new Task();
				 task.addPropertyChangeListener(this);
				 task.execute();
				
				
			}
		} 
		else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
		
	}
	
	
	
	class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
//            Random random = new Random();
            int progress = 0;
            setProgress(0);
			SeatSrv seatsrv = new SeatSrv();
			for(int i=0;i<row;i++){
				for(int j = 0;j<col;j++){
					seatsrv.add(new Seat(studioID,i,j));	
					if(progress>98){
						progress--;
					}else{
						progress++;
					}
					setProgress(progress);
				}
			}
			setProgress(100);
			StudioAddUI.this.setVisible(false);
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
	            n+=1;
//	            progressMonitor.setNote(message);
	            if (progressMonitor.isCanceled() || task.isDone()) {
	                Toolkit.getDefaultToolkit().beep();
	                if (progressMonitor.isCanceled()) {
	                    task.cancel(true);
	                    JOptionPane.showMessageDialog(null, "已取消");
	                   
	                } else {
	                   
	                    JOptionPane.showMessageDialog(null, "已完成");
	                }
	                
	            }
	        }
	}

}


