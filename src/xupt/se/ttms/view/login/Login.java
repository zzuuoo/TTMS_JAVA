package xupt.se.ttms.view.login;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import xupt.se.ttms.view.sellticket.SellTicketUI;
import xupt.se.ttms.view.tmpl.PopUITmpl;
/**
 * 登陆
 * @author zuo
 *
 */
public class Login extends JFrame implements ActionListener{

	
	
	private String who="经理";
//	private JPanel jp = new JPanel();
	private JPanel jp =new JPanel(){  
		   @Override  
		   protected void paintComponent(Graphics g) {  
		    super.paintComponent(g);  
		    ImageIcon img = new ImageIcon("resource/image/mui.jpg"); 
		    img.paintIcon(this, g, 0, 0);  
		   }  
		  };  
	
	
	
	private JButton log_in = new JButton("登陆");
	private JButton zhuce = new JButton("注册");
	
	private JLabel hao = new JLabel("账号");
	private JLabel mi = new JLabel("密码");
	private JLabel welccome = new JLabel("欢迎进入千达票务管理系统");
	
	
	private JTextArea zhanghao = new JTextArea("");
	private JPasswordField mima = new JPasswordField("");
	
	JRadioButton jrb1 = new JRadioButton("经理",true);
	JRadioButton jrb2 = new JRadioButton("系统管理者");
	JRadioButton jrb3 = new JRadioButton("售票员");
	ButtonGroup bg = new ButtonGroup();

	
	public Login(){

		
		super("TTMS");
		
		
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		
		mima.addKeyListener(new KeyListener(){

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
		zhuce.setFont(new Font(" ",1,16));//设置字体大小
		jp.setLayout(null);
		
	
		this.setSize(1024, 700);
//		this.setSize(800, 800);
		this.setLocation(500, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		log_in.setBounds(400, 400, 70, 35);
		zhuce.setBounds(500, 400, 70, 35);
		log_in.setBackground(Color.GREEN);
		zhuce.setBackground(Color.green);
		log_in.addActionListener(this);
		zhuce.addActionListener(this);


		mi.setBounds(330, 300, 50, 30);
		mi.setFont(new Font(" ",1,18));//设置字体大小
		hao.setBounds(330,250,50,30);
		hao.setFont(new Font(" ",1,18));//设置字体大小
		
		welccome.setBounds(300, 150, 400, 50);
		welccome.setFont(new Font("宋体",1,30));
		
		zhanghao.setEditable(true);
		zhanghao.setFont(new Font(" ",1,20));
		mima.setBounds(400, 300, 200, 30);
		zhanghao.setBounds(400,250,200,30);
	
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
		jp.add(hao);
		jp.add(zhanghao);
		jp.add(mi);
		jp.add(mima);
		jp.add(log_in);
		jp.add(zhuce);
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
					System.out.println("账号："+zhanghao.getText()+"   密码："+mima.getText());
					Manager  mgr = new Manager();
					mgr.setVisible(true);
					this.dispose();
				}else if(who.compareTo("系统管理者")==0){
					System.out.println("账号："+zhanghao.getText()+"   密码："+mima.getText());
					SystemMgUI  smg = new SystemMgUI();
					smg.setVisible(true);
					this.dispose();
				}else if (who.compareTo("售票员")==0){
					System.out.println("账号："+zhanghao.getText()+"   密码："+mima.getText());
					Seller se = new Seller();
					se.setVisible(true);
					this.dispose();
				}
				break;
			case "注册":
				zhanghao.setText(who+e.getActionCommand());
				break;
			default:
				break;
			}
			
		}
	}

	
}

