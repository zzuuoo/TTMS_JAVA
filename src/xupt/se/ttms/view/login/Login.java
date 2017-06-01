package xupt.se.ttms.view.login;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import xupt.se.ttms.model.Loginde;
import xupt.se.ttms.service.LogindeSrv;
/**
 * 登陆
 * @author zuo
 *
 */
public class Login extends JFrame implements ActionListener{

	private String who="经理";
	private JPanel jp =new JPanel(){  
		   @Override  
		   protected void paintComponent(Graphics g) {  
		    super.paintComponent(g);  
		    ImageIcon img = new ImageIcon("resource/image/login2.jpg"); 
		    img.paintIcon(this, g, 0, 0);  
		   }
		  };  
	
	private JButton log_in = new JButton("登陆");
	private JButton register = new JButton("注册");
	
	private JLabel account_tag = new JLabel("账号");
	private JLabel password_tag = new JLabel("密码");
	private JLabel welccome = new JLabel("欢迎进入千达票务管理系统");
	
	
	private JTextField account = new JTextField("");
	private JPasswordField password = new JPasswordField("");
	
	JRadioButton jrb1 = new JRadioButton("经理",true);
	JRadioButton jrb2 = new JRadioButton("系统管理者");
	JRadioButton jrb3 = new JRadioButton("售票员");
	ButtonGroup bg = new ButtonGroup();

	public Login(){
		super("TTMS");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	
//		监听输入账号时键盘的敲击
		account.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub.
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
					password.requestFocus();
				}
			}
		});
		//监听输入密码时键盘 的敲击
		password.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					log_in.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
//				if(e.getKeyCode()==KeyEvent.VK_ENTER){
//					log_in.doClick();
//				}
			}
			
		});
		
		log_in.setFont(new Font(" ",1,16));//设置字体大小
		register.setFont(new Font(" ",1,16));//设置字体大小
		jp.setLayout(null);
		
	
		this.setSize(1024, 700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		log_in.setBounds(400, 400, 70, 35);
		register.setBounds(500, 400, 70, 35);
		log_in.setBorder(BorderFactory.createRaisedBevelBorder()); 
		register.setBorder(BorderFactory.createRaisedBevelBorder()); 
		log_in.addActionListener(this);
		register.addActionListener(this);


		password_tag.setBounds(330, 300, 50, 30);
		password_tag.setFont(new Font(" ",1,18));//设置字体大小
		account_tag.setBounds(330,250,50,30);
		account_tag.setFont(new Font(" ",1,18));//设置字体大小
		
		welccome.setBounds(300, 150, 400, 50);
		welccome.setFont(new Font("宋体",1,30));
		
		account.setEditable(true);
		account.setFont(new Font(" ",1,20));
		password.setBounds(400, 300, 200, 30);
		account.setBounds(400,250,200,30);
	
		jrb1.setFont(new Font(" ",1,16));
		jrb2.setFont(new Font(" ",1,16));
		jrb3.setFont(new Font(" ",1,16));
		jrb1.addActionListener(this);
		jrb2.addActionListener(this);
		jrb3.addActionListener(this);
		jrb1.setBounds(360, 350, 60, 30);
		jrb2.setBounds(440, 350, 120, 30);
		jrb3.setBounds(570, 350, 100, 30);
		jrb1.setOpaque(false);
		jrb2.setOpaque(false);
		jrb3.setOpaque(false);
		bg.add(jrb1);
		bg.add(jrb2);
		bg.add(jrb3);
		
		
  
        jp.add(welccome);
		jp.add(account_tag);
		jp.add(account);
		jp.add(password_tag);
		jp.add(password);
		jp.add(log_in);
		jp.add(register);
		jp.add(jrb1);
		jp.add(jrb2);
		jp.add(jrb3);
		jp.setOpaque(false);  //设置为透明
		this.add(jp);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Login().setVisible(true);;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj instanceof JRadioButton){
			who = e.getActionCommand();
			
		}else{
		
			switch(e.getActionCommand()){
			
			case "登陆":
				if(who.compareTo("经理")==0){
					password.getPassword();
					Loginde  lg = new Loginde(account.getText().replaceAll("\n", ""),new String(password.getPassword()),"经理");
					LogindeSrv lgs = new LogindeSrv();
					if(lgs.LogindeDAO_log(lg)==1){
						Manager  mgr = new Manager();
						mgr.setVisible(true);
						this.dispose();
					}
					else{
						System.out.println("账号或密码错误");	
						JOptionPane.showMessageDialog(null, "账号或密码错误");	
					}
					System.out.println("账号："+account.getText().replaceAll("\n", "")+"\n密码："+new String(password.getPassword()));

				}
				else if(who.compareTo("系统管理者")==0){
					Loginde  lg2 = new Loginde(account.getText().replaceAll("\n", ""),new String(password.getPassword()),"系统管理者");
					LogindeSrv lgs2 = new LogindeSrv();
					if(lgs2.LogindeDAO_log(lg2)==1){
						SystemMgUI  smg = new SystemMgUI();
						smg.setVisible(true);
						this.dispose();
					}
					else{
						System.out.println("账号或密码错误");
						JOptionPane.showMessageDialog(null, "账号或密码错误");	
					}
					System.out.println("账号："+account.getText().replaceAll("\n", "")+"\n密码："+new String(password.getPassword()));

				}
				else if (who.compareTo("售票员")==0){
					Loginde  lg3 = new Loginde(account.getText().replaceAll("\n", ""),new String(password.getPassword()),"售票员");
					LogindeSrv lgs3 = new LogindeSrv();
					if(lgs3.LogindeDAO_log(lg3)==1){
						Seller se = new Seller();
						se.setVisible(true);
						this.dispose();
					}
					else{
						System.out.println("账号或密码错误");
						JOptionPane.showMessageDialog(null, "账号或密码错误");	
					}
					System.out.println("账号："+account.getText().replaceAll("\n", "")+"\n密码："+new String(password.getPassword()));
				}
				break;
			case "注册":

				account.setText(who+e.getActionCommand());
				break;
			default:
				break;
			}
			
		}
	}
	
}

