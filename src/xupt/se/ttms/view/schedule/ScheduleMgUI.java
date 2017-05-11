package xupt.se.ttms.view.schedule;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.login.Manager;
import xupt.se.ttms.view.studio.StudioAddUI;
import xupt.se.ttms.view.studio.StudioEditUI;

//import xupt.se.ttms.view.studio.StudioTableMouseListener;
import xupt.se.ttms.view.tmpl.MainUITmpl;

class StudioTableMouseListener extends MouseAdapter {

	private JTable jt;
	private static Schedule sche;

	public Schedule getSchedule() {
		return sche;
	}

	public StudioTableMouseListener(JTable jt, Object[] number, Schedule sche) {
		this.sche = sche;
		this.jt = jt;
	}
/**
 * private int sched_id;
	private int studio_id;
	private int play_id;
	private Date sched_time;
	private double sched_ticket_price;
 */
	// 监听到行号，将所选行的内容依次赋到 stud对象，以便传有值对象到修改面板进行修改
	public void mouseClicked(MouseEvent event) {
		int row = jt.getSelectedRow();
		sche.setSched_id(Integer.parseInt(jt.getValueAt(row, 0).toString()));
		sche.setStudio_id((int)jt.getValueAt(row, 1));
		sche.setPlay_id(((int)jt.getValueAt(row, 2)));
		sche.setSched_time(((Date)jt.getValueAt(row, 3)));
//		sche.setSched_time(DateFormat.parse(jt.getValueAt(row, 3).toString()));
		
		sche.setSched_ticket_price(((double)jt.getValueAt(row, 4)));
		System.out.println(jt.getValueAt(row, 1).toString());
	}
}

class ScheduleTable {

	private Schedule stud;
	private JTable jt = null;

	public ScheduleTable(Schedule stud) {
		this.stud = stud;
	}

	// 创建JTable
	public void createTable(JScrollPane jp, Object[] columnNames, List<Schedule> stuList) {
		try {

			Object data[][] = new Object[stuList.size()][columnNames.length];

			Iterator<Schedule> itr = stuList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				Schedule stu = itr.next();
				data[i][0] = Integer.toString(stu.getSched_id());
				data[i][1] = Integer.toString(stu.getPlay_id());
				data[i][2] = Integer.toString(stu.getPlay_id());
				data[i][3] = stu.getSched_time().toString();
				data[i][4] = Double.toString(stu.getSched_ticket_price());
				i++;
			}

			// 生成JTable
			jt = new JTable(data, columnNames);
			jt.setBounds(0, 0, 700, 450);

			// 添加鼠标监听，监听到所选行
			StudioTableMouseListener tml = new StudioTableMouseListener(jt, columnNames, stud);
			jt.addMouseListener(tml);

			// 设置可调整列宽
			jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			jp.add(jt);
			jp.setViewportView(jt);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class ScheduleMgUI extends MainUITmpl{
	
	
	private static final long serialVersionUID = 2L;
	private Schedule sche=new Schedule();
	private JLabel ca1 = null; // 界面提示
	// 用来放表格的滚动控件
	private JScrollPane jsc;
	// 查找的提示和输出
	private JLabel hint;
	private JTextField input;

	// 查找，编辑和删除按钮
	private JButton btnAdd, btnEdit, btnDel, btnQuery;

	

	public ScheduleMgUI(){
		initContent();
	}
	
	
	//To be override by the detailed business block interface 
		protected void initContent(){
			
			Rectangle rect = contPan.getBounds();

			ca1 = new JLabel("演出计划管理", JLabel.CENTER);
			ca1.setBounds(0, 5, rect.width, 30);
			ca1.setFont(new java.awt.Font("宋体", 1, 20));
			ca1.setForeground(Color.blue);
			contPan.add(ca1);

			jsc = new JScrollPane();
			jsc.setBounds(0, 40, rect.width, rect.height - 150);
			contPan.add(jsc);

			hint = new JLabel("请输入演出计划ID:", JLabel.RIGHT);
			hint.setBounds(60, rect.height - 80, 150, 30);
			contPan.add(hint);

			input = new JTextField();
			input.setBounds(220, rect.height - 80, 200, 30);
			contPan.add(input);

			// 查找 ，删除和编辑的按钮，其中含有相关的事件处理！
			btnQuery = new JButton("查找");
			btnQuery.setBounds(440, rect.height - 80, 60, 30);
			btnQuery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent Event) {
					btnQueryClicked();
				}
			});
			contPan.add(btnQuery);

			btnAdd = new JButton("添加");
			btnAdd.setBounds(rect.width - 220, rect.height - 80, 60, 30);
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent Event) {
					btnAddClicked();
				}
			});
			contPan.add(btnAdd);

			btnEdit = new JButton("修改");
			btnEdit.setBounds(rect.width - 150, rect.height - 80, 60, 30);
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent Event) {
					btnModClicked();
				}
			});
			contPan.add(btnEdit);

			btnDel = new JButton("删除");
			btnDel.setBounds(rect.width - 80, rect.height - 80, 60, 30);
			btnDel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent Event) {
					btnDelClicked();
				}
			});
			contPan.add(btnDel);
			contPan.add(ca1);
			showTable();
			
			
		}
		
		
		private void btnAddClicked() {
//			StudioAddUI addStud = new StudioAddUI();
//			addStud.setWindowName("添加演出厅");
//			addStud.toFront();
//			addStud.setModal(true);
//			addStud.setVisible(true);
//
//			if (addStud.getReturnStatus()) {
//				showTable();
//			}
			System.out.println("add");
			
		}

		private void btnModClicked() {
			System.out.println("修改");
				
//			StudioEditUI modStu = new StudioEditUI(stud);
//			modStu.setWindowName("修改演出厅");
//			modStu.toFront();
//			modStu.setModal(true);
//			modStu.setVisible(true);
//			if (modStu.getReturnStatus()) {
//				showTable();
//			}
		}

		private void btnDelClicked() {
//			
				System.out.println("delete");
		}

		private void btnQueryClicked() {
			if (!input.getText().equals("")) {


			} else {
				JOptionPane.showMessageDialog(null, "请输入检索条件");
			}
		}
		/**
		 * private int sched_id;
			private int studio_id;
			private int play_id;
			private Date sched_time;
			private double sched_ticket_price;
		 */

		public void showTable() {
			ScheduleTable tms = new ScheduleTable(sche);
			Object[] in = { "id", "studio_id", "play_id", "sched_time", "ticket_price" };
//			List<Schedule> stuList = new StudioSrv().FetchAll();
			List<Schedule> stuList = new ArrayList<>();
			tms.createTable(jsc, in, stuList);
			jsc.repaint();
		}
	
	//To be override by the detailed business block interface 
	protected void onWindowClosing(){
		System.exit(0);
	}
	
	
	
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
		new Manager().setVisible(true);
		this.dispose();
	}	

}
