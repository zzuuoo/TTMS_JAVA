package xupt.se.ttms.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
import javax.swing.plaf.ProgressBarUI;

import xupt.se.ttms.model.Seat;
import xupt.se.ttms.service.SeatSrv;

public class StudioAddDialog extends JFrame {

	private JProgressBar pro;
	private int frmWidth=1024;
	private int frmHeight=700;
	private JPanel jp;

	public StudioAddDialog(int studioID,int row,int col) {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pro = new JProgressBar(); // 创建进度条
		pro.setStringPainted(true);
//		pro.setSize(30, 100);
		pro.setMaximum(row*col);
//		pro.setPreferredSize(null);
//		pro.setBackground(Color.magenta);
		pro.setFont(new Font("",1,20));
//		pro.setToolTipText("aaaaaaa");
		
		JLabel jl = new JLabel("数据添加中....",JLabel.CENTER);
		jl.setFont(new Font("",1,20));
//		up.add(jl,BorderLayout.CENTER);
		jp = new JPanel(new BorderLayout());
		jp.add(jl,BorderLayout.NORTH);
		jp.add(pro,BorderLayout.CENTER);
		this.setLayout(new BorderLayout());
//		this.setLayout(new FlowLayout());
		this.setSize(frmWidth, frmHeight);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
//		this.add(jl,BorderLayout.NORTH);
//		this.add(pro,BorderLayout.CENTER);
		this.add(jp,BorderLayout.CENTER);
		this.setVisible(true);
		init(studioID,row,col);
//		this.setVisible(true);
	}
	
	
	
	private void init(int studioID,int row,int col){
//		int row=10,col=10;
		int n=1;
//		if(studioID!=0){
//			SeatSrv seatsrv = new SeatSrv();
//			new Thread(new Runnable(){
//
//				int n=1;
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					for(int i=0;i<row;i++){
//						for(int j = 0;j<col;j++){
////							seatsrv.add(new Seat(studioID,i,j));	
//							try {
//								Thread.sleep(100);
//							} catch (Exception e) {
//								e.getStackTrace();
//
//							}
//							pro.setValue(n++);//反复修改进度值
//						}
//					}
//					
//				}
//				
//			});
			
//			}
//		SeatSrv seatsrv = new SeatSrv();
			for(int i=0;i<row;i++){
				for(int j = 0;j<col;j++){
//					seatsrv.add(new Seat(studioID,i,j));	
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.getStackTrace();

					}
					pro.setValue(n++);//反复修改进度值
				}
			}
			pro.setString("完成!!");//设置进度条提示完成进度后的信息
			this.dispose();
//			sa=null;
			
//		}else{
//			JOptionPane.showMessageDialog(null, "数据添加失败");
//		}
	}

	public static void main(String[] args) {
		StudioAddDialog sa = new StudioAddDialog(12,10,10);
//		sa.setVisible(true);
////pro.setIndeterminate(true);
//		for (int i = 0; i <= 100; i++) {
//			try {
//				Thread.sleep(100);
//			} catch (Exception e) {
//				e.getStackTrace();
//
//			}
//			pro.setValue(i);//反复修改进度值
//		}
//		pro.setString("下载完成!!");//设置进度条提示完成进度后的信息
//		sa.dispose();
//		sa=null;

	}
}