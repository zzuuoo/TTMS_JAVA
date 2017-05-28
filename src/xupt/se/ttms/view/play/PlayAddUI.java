package xupt.se.ttms.view.play;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import xupt.se.ttms.model.Play;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.tmpl.PopUITmpl;

public class PlayAddUI extends PopUITmpl implements ActionListener {

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel lblName, lblRow, lblColumn,introduction,price;
	protected JTextField txtName,  txtlength,txtTicketPrice;
	JComboBox playstatus;

	protected JTextArea txtintroduction;
	public PlayAddUI() {
	}

	@Override
	protected void initContent(){
		this.setTitle("添加剧目");

		lblName = new JLabel("剧目名称：");
		lblName.setBounds(60, 30, 90, 30);
		contPan.add(lblName);
		txtName = new JTextField();
		txtName.setBounds(150, 30, 120, 30);
		contPan.add(txtName);

		lblRow = new JLabel("剧目时长(分钟)：");
		lblRow.setBounds(60, 80, 130, 30);
		contPan.add(lblRow);
		txtlength = new JTextField();
		txtlength.setBounds(150, 80, 120, 30);
		contPan.add(txtlength);

		lblColumn = new JLabel("剧目状态：");
		lblColumn.setBounds(60, 130, 90, 30);
		contPan.add(lblColumn);
	
		
		
//		 0：待安排演出    1：已安排演出    -1：下线
		String[] playStatus = {"待安排演出","已安排演出","下线"};
		playstatus = new JComboBox(playStatus);
		playstatus.setBounds(150, 130, 120, 30);
		contPan.add(playstatus);

		
		price = new JLabel("剧目票价：");
		price.setBounds(60, 180, 90, 30);
		contPan.add(price);
		txtTicketPrice = new JTextField();
		txtTicketPrice.setBounds(150, 180, 120, 30);
		contPan.add(txtTicketPrice);
		

		introduction = new JLabel("剧目简介：");
		introduction.setBounds(60, 230, 90, 30);
		contPan.add(introduction);
		txtintroduction = new JTextArea();
		txtintroduction.setLineWrap(true);
		txtintroduction.setBounds(150, 230, 400, 200);
		contPan.add(txtintroduction);

		
		btnSave = new JButton("保存");

		btnSave.addActionListener(this);
		btnSave.setBounds(60, 450, 60, 30);
		contPan.add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(180, 450, 60, 30);
		contPan.add(btnCancel);

	}
	
	
	public boolean getReturnStatus(){
		   return rst;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			rst=false;
			System.out.println("cancel");
			this.setVisible(false);
		} else if (e.getSource() == btnSave) {
			System.out.println("save");
			btnSaveClicked();
		}
	}

	//保存按钮点击事件
	protected void btnSaveClicked(){
		if (txtName.getText() != null && txtintroduction.getText() != null
				&& txtlength.getText() != null&&txtTicketPrice.getText()!=null
				&&playstatus.getSelectedItem()!=null) {
			PlaySrv stuSrv = new PlaySrv();
			Play stu=new Play();
			stu.setName(txtName.getText());
			System.out.println(txtlength.getText()+11111);
			stu.setLength(Integer.parseInt(txtlength.getText()+1));
			if(playstatus.getSelectedItem().equals("待安排演出")){
				stu.setStatus(0);
			}else if(playstatus.getSelectedItem().equals("以安排演出")){
				stu.setStatus(1);
			}else{
				stu.setStatus(-1);
			}
			
				
//				 0：待安排演出    1：已安排演出    -1：下线
			stu.setTicketPrice(Float.parseFloat(txtTicketPrice.getText()));
	
			stu.setIntroduction(txtintroduction.getText());

			stuSrv.add(stu);
			this.setVisible(false);
			this.dispose();
			rst=true;
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
	protected void onWindowClosing(){
		this.dispose();
}
}
