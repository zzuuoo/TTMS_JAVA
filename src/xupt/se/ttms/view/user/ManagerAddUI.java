package xupt.se.ttms.view.user;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Play;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.EmployeeSrv;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.tmpl.PopUITmpl;

public class ManagerAddUI extends PopUITmpl implements ActionListener {

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	protected JLabel lblemp_id, lblname, lblstatus,lblaccount,lbltel,lbladdr,lblemail;
	protected JTextField empid,name,status,account,tel,email;
	protected JTextArea addr;

	public ManagerAddUI() {
		
	}

	@Override
	protected void initContent(){
		this.setTitle("添加用户");

		lblname = new JLabel("姓名：");
		lblname.setBounds(60, 30, 120, 30);
		contPan.add(lblname);
		name = new JTextField();
		name.setBounds(150, 30, 120, 30);
		contPan.add(name);

		
		lbltel = new JLabel("电话：");
		lbltel.setBounds(60, 80, 120, 30);
		contPan.add(lbltel);
		tel = new JTextField();
		tel.setBounds(150, 80, 120, 30);
		contPan.add(tel);
	
		
		lblemail = new JLabel("邮箱：");
		lblemail.setBounds(60, 130, 120, 30);
		contPan.add(lblemail);
		email = new JTextField();
		email.setBounds(150, 130, 120, 30);
		contPan.add(email);
		
		lbladdr = new JLabel("地址：");
		lbladdr.setBounds(60, 180, 120, 30);
		contPan.add(lbladdr);
		addr = new JTextArea();
		addr.setBounds(150, 180, 400, 100);
		contPan.add(addr);


		
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
		if (name.getText() != null&&tel.getText()!=null
				&&addr.getText()!=null&&email.getText()!=null) {
			EmployeeSrv empSrv = new EmployeeSrv();
			Employee emp=new Employee();
			
			emp.setEmp_name(name.getText());
			emp.setEmp_tel_num(tel.getText());
			emp.setEmp_addr(tel.getText());
			emp.setEmp_email(email.getText());

			empSrv.add(emp);
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
