package xupt.se.ttms.view.studio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.tmpl.*;

public class StudioAddUI extends PopUITmpl implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel lblName, lblRow, lblColumn, lblIntro;
	protected JTextField txtName, txtRow, txtColumn;
	protected JTextArea txtIntro;
	

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

/*		ImageJPanel imageJP = new ImageJPanel(new ImageIcon(
				"files/imgs/pencil.jpg").getImage());
		imageJP.setBounds(360, 160, 100, 100);
		imageJP.setLayout(null);
		this.add(imageJP);
		*/
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
	
	protected void btnSaveClicked(){
		if (txtName.getText() != null && txtRow.getText() != null
				&& txtColumn.getText() != null) {
			StudioSrv stuSrv = new StudioSrv();
			Studio stu=new Studio();
			stu.setName(txtName.getText());
			stu.setRowCount(Integer.parseInt(txtRow.getText()));
			stu.setColCount(Integer.parseInt(txtColumn.getText()));
			stu.setIntroduction(txtIntro.getText());

			/**
			 * 生成演出厅的同时生成座位数据
			 */
//			SeatSrv seatsrv = new SeatSrv();
//			int studioID = stu.getID();
//			for(int i =0;i<stu.getRowCount();i++){
//				for(int j =0;j<stu.getColCount();j++){
//					seatsrv.add(new Seat(studioID,i,j));
//				}
//			}
			
			
			stuSrv.add(stu);
			this.setVisible(false);
			rst=true;
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}

}







//package xupt.se.ttms.view.studio;
//
//import javax.swing.JDialog;
//
//import java.awt.Color;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//
////import view.studioUI.ImageJPanel;
//import xupt.se.ttms.model.Studio;
//import xupt.se.ttms.service.StudioSrv;
//import xupt.se.ttms.view.tmpl.*;
//
//public class StudioAddUI extends PopUITmpl implements ActionListener {
//
//	private JButton btnCancel, btnSave; 	//取消，保存按鈕
//
//	protected boolean rst=false; 				//操作结果
//	private JLabel lblName, lblRow, lblColumn,introduction;
//	protected JTextField txtName, txtRow, txtColumn,txtintroduction;
//
//	public StudioAddUI() {
//	}
//
//
//	
//	@Override
//	protected void initContent(){
//		this.setTitle("添加演出厅");
//
//		lblName = new JLabel("演出厅名称：");
//		lblName.setBounds(60, 30, 80, 30);
//		contPan.add(lblName);
//		txtName = new JTextField();
//		txtName.setBounds(150, 30, 120, 30);
//		contPan.add(txtName);
//
//		lblRow = new JLabel("座位行数：");
//		lblRow.setBounds(60, 80, 50, 30);
//		contPan.add(lblRow);
//		txtRow = new JTextField();
//		txtRow.setBounds(150, 80, 120, 30);
//		contPan.add(txtRow);
//
//		lblColumn = new JLabel("座位列数：");
//		lblColumn.setBounds(60, 130, 90, 30);
//		contPan.add(lblColumn);
//		txtColumn = new JTextField();
//		txtColumn.setBounds(150, 130, 120, 30);
//		contPan.add(txtColumn);
//
//		
//		introduction = new JLabel("演出厅简介：");
//		introduction.setBounds(60, 180, 80, 30);
//		contPan.add(introduction);
//		txtintroduction = new JTextField();
//		txtintroduction.setBounds(150, 180, 120, 30);
//		contPan.add(txtintroduction);
//
//		
//		btnSave = new JButton("保存");
//
//		btnSave.addActionListener(this);
//		btnSave.setBounds(60, 220, 60, 30);
//		contPan.add(btnSave);
//
//		btnCancel = new JButton("取消");
//		btnCancel.addActionListener(this);
//		btnCancel.setBounds(180, 220, 60, 30);
//		contPan.add(btnCancel);
////
////		ImageJPanel imageJP = new ImageJPanel(new ImageIcon(
////				"files/imgs/pencil.jpg").getImage());
////		imageJP.setBounds(360, 160, 100, 100);
////		imageJP.setLayout(null);
////		this.add(imageJP);
//	}
//	
//	
//	public boolean getReturnStatus(){
//		   return rst;
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == btnCancel) {
//			rst=false;
////			this.dispose();
//			this.setVisible(false);
////			getParent().setVisible(true);
//
//		} else if (e.getSource() == btnSave) {
//			btnSaveClicked();		//以前未调用，新添加的调用语句
//		}
//
//	}
//	
//	protected void btnSaveClicked(){
//		if (txtName.getText() != null && txtRow.getText() != null
//				&& txtColumn.getText() != null) {
//			StudioSrv stuSrv = new StudioSrv();
//			Studio stu=new Studio();
//			stu.setName(txtName.getText());
//		
//			System.out.println(txtRow.getText());
//			stu.setRowCount(Integer.parseInt(txtRow.getText()));
//			stu.setColCount(Integer.parseInt(txtColumn.getText()));
//	
//			stu.setIntroduction(txtintroduction.getText());
//
//			stuSrv.add(stu);
//			this.setVisible(false);
////			this.dispose();
//			rst=true;
////			getParent().setVisible(true);/
//		} else {
//			JOptionPane.showMessageDialog(null, "数据不完整");
//		}		
//	}
//	protected void onWindowClosing(){
//		//System.exit(0);
//		this.dispose();
////		new SystemMgUI().setVisible(true);
//}
//}
