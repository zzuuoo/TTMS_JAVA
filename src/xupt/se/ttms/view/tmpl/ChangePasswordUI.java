package xupt.se.ttms.view.tmpl;
/**
 * 修改密码弹窗
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import xupt.se.ttms.dao.LogindeDAO;
import xupt.se.ttms.idao.iLogindeDAO;
import xupt.se.ttms.model.GlobalVariable;
import xupt.se.ttms.model.Loginde;
import xupt.se.ttms.service.LogindeSrv;

public class ChangePasswordUI implements ActionListener{
	
	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel Loldpassword, Lnewpassword, Lpasswordagain;
	protected JTextField oldpassword;
	protected JPasswordField newpassword,passwordagain;
	private int emp_id = GlobalVariable.emp_id;
	
	JFrame regFr = new JFrame();
	
	public ChangePasswordUI() {
		regFr.setTitle("修改密码");
		regFr.setBounds(500, 150, 400, 500);
		regFr.setVisible(true);
		regFr.setBackground(Color.WHITE);
		regFr.setLayout(null);
		regFr.setLocationRelativeTo(null);
		regFr.setResizable(false);
		initContent();
	}
	
	
	
	protected void initContent(){
		

		Loldpassword = new JLabel("旧密码：");
		Loldpassword.setBounds(60, 50, 90, 30);
		regFr.add(Loldpassword);
		oldpassword = new JTextField();
		oldpassword.setBounds(150, 50, 120, 30);
		regFr.add(oldpassword);

		Lnewpassword = new JLabel("新密码：");
		Lnewpassword.setBounds(60, 110, 130, 30);
		regFr.add(Lnewpassword);
		newpassword = new JPasswordField();
		newpassword.setBounds(150, 110, 120, 30);
		regFr.add(newpassword);

		Lpasswordagain = new JLabel("新密码：");
		Lpasswordagain.setBounds(60, 160, 130, 30);
		regFr.add(Lpasswordagain);
		passwordagain = new JPasswordField();
		passwordagain.setBounds(150, 160, 120, 30);
		regFr.add(passwordagain);
		
//		lblColumn = new JLabel("身份：");
//		lblColumn.setBounds(60, 170, 110, 30);
//		regFr.add(lblColumn);

		
		btnSave = new JButton("保存");

		btnSave.addActionListener(this);
		btnSave.setBounds(100, 350, 60, 30);
		regFr.add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(200, 350, 60, 30);
		regFr.add(btnCancel);

	}
	
	
	public boolean getReturnStatus(){
		   return rst;
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnCancel) {
			rst=false;
			System.out.println("cancel");
			regFr.setVisible(false);
		} else if (e.getSource() == btnSave) {
			System.out.println("save");
			btnSaveClicked();
		}
	}

	//保存按钮点击事件
	protected void btnSaveClicked(){
		if (oldpassword.getText() != null && newpassword.getPassword() != null
				&&passwordagain.getPassword()!=null && new String(newpassword.getPassword()).compareTo(new String(passwordagain.getPassword()))==0) {
			int errorFeedback = 0;
			LogindeSrv logsrv = new LogindeSrv();
			Loginde lg = new Loginde();
			lg=findlog();
			if(oldpassword.getText().compareTo(lg.getPassword())==0){
				errorFeedback=logsrv.LogindeDAO_amendPassword(lg, new String(newpassword.getPassword()));
			}
			else{
				JOptionPane.showMessageDialog(null, "原密码不正确！ ");
				return ;
			}
			
			if(errorFeedback!=0){
				
				regFr.setVisible(false);
				regFr.dispose();
				rst=true;
				JOptionPane.showMessageDialog(null, "修改密码成功 ");	
			}
		} else {
			JOptionPane.showMessageDialog(null, "输入数据错误！");
			return ;
		}		
	}

	
	public Loginde findlog(){
		LogindeDAO  log = new LogindeDAO();
		List<Loginde> logList = null;
		logList=new LinkedList<Loginde>();
		logList=log.select();
		for(Loginde lg:logList){
			if(lg.getEmp_id()==emp_id){
				return lg;
			}
				
		}
		return  null;
	}
	
	protected void onWindowClosing(){
		regFr.dispose();
}
}
