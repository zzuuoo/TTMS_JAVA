package xupt.se.ttms.view.login;
/**
 * 注册弹窗
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import xupt.se.ttms.model.Loginde;
import xupt.se.ttms.model.Play;
import xupt.se.ttms.service.LogindeSrv;
import xupt.se.ttms.service.PlaySrv;


public class Register  implements ActionListener{

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel lblaccount, lblpassword, lblColumn,lblemp_id;
	protected JTextField account,txtemp_id;
	protected JPasswordField txtpassword;
	JComboBox Logstatus;
	
	JFrame regFr = new JFrame();
	
	public Register() {
		regFr.setTitle("注册");
		regFr.setBounds(500, 150, 400, 500);
		regFr.setVisible(true);
		regFr.setBackground(Color.WHITE);
		regFr.setLayout(null);
		regFr.setLocationRelativeTo(null);
		regFr.setResizable(false);
		initContent();
	}
	
	
	
	protected void initContent(){
		

		lblaccount = new JLabel("用户名：");
		lblaccount.setBounds(60, 50, 90, 30);
		regFr.add(lblaccount);
		account = new JTextField();
		account.setBounds(150, 50, 120, 30);
		regFr.add(account);

		lblpassword = new JLabel("密码：");
		lblpassword.setBounds(60, 110, 130, 30);
		regFr.add(lblpassword);
		txtpassword = new JPasswordField();
		txtpassword.setBounds(150, 110, 120, 30);
		regFr.add(txtpassword);

		lblColumn = new JLabel("身份：");
		lblColumn.setBounds(60, 170, 110, 30);
		regFr.add(lblColumn);
	
		
		
//		 0：待安排演出    1：已安排演出    -1：下线
		String[] playStatus = {"经理","系统管理者","售票员"};
		Logstatus = new JComboBox(playStatus);
		Logstatus.setBounds(150, 170, 120, 30);
		regFr.add(Logstatus);

		
		lblemp_id = new JLabel("工号：");
		lblemp_id.setBounds(60, 230, 90, 30);
		regFr.add(lblemp_id);
		txtemp_id = new JTextField();
		txtemp_id.setBounds(150, 230, 120, 30);
		regFr.add(txtemp_id);

		
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
		if (account!=null&&txtpassword!=null&&
				txtemp_id!=null&&account.getText() != "" && txtpassword.getText() != ""
				&&txtemp_id.getText()!=""
				&&Logstatus.getSelectedItem()!="") {
			LogindeSrv logsrv = new LogindeSrv();
			Loginde lg = new Loginde();
			lg.setAccount(account.getText());		
			lg.setPassword(txtpassword.getText());
			if(Logstatus.getSelectedItem().equals("经理")){
				lg.setStatus("经理");
			}else if(Logstatus.getSelectedItem().equals("系统管理者")){
				lg.setStatus("系统管理者");
			}else{
				lg.setStatus("售票员");
			}
			lg.setEmp_id(new Integer(txtemp_id.getText()));
			//System.out.println(txtemp_id.getText());
			//System.out.println(lg.getAccount()+lg.getPassword()+lg.getStatus()+lg.getEmp_id());
			
			//return 1-工号不存在 不能注册     2-该工号已经有账号  不能注册   3-账号与其他账号重复
			int errorFeedback=logsrv.Loginde_register(lg); 
			switch(errorFeedback){
				case 0:
					rst=true;
					JOptionPane.showMessageDialog(null, "注册成功");
					break;
				case 1:
					JOptionPane.showMessageDialog(null, "注册失败 "
							+ "工号不存在");	
//					System.out.println("1-工号不存在 不能注册 ");
					break;
				case 2:
					JOptionPane.showMessageDialog(null, "注册失败 "
							+ "该工号已经存在账号");	
//					System.out.println("2-该工号已经有账号  不能注册");
					break;
				case 3:
					JOptionPane.showMessageDialog(null, "注册失败 "
							+ "该账号已经存在");	
//					System.out.println("3-账号与其他账号重复");
					break;
			}
			
			regFr.setVisible(false);
			regFr.dispose();
//			rst=true;
//			JOptionPane.showMessageDialog(null, "注册成功");
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
//	protected void onWindowClosing(){
//		regFr.dispose();
//}

}
