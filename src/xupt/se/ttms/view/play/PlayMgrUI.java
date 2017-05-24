package xupt.se.ttms.view.play;


import java.awt.Color;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.lang.model.type.TypeKind;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.util.List;
import java.util.Iterator;

import xupt.se.ttms.model.Play;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.login.Manager;
import xupt.se.ttms.view.login.SystemMgUI;
import xupt.se.ttms.view.tmpl.*;

class PlayTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable jt;

	public PlayTable(JScrollPane jp) {
		
		DefaultTableModel tabModel=new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override              
			public boolean isCellEditable(int row,int column){
				return false;              
			};
		};
//Object[] in = { "id", "name", "image", "introduction", "length","status","ticketprice" };

		tabModel.addColumn("id");
		tabModel.addColumn("name");
		tabModel.addColumn("image");
		tabModel.addColumn("length");
		tabModel.addColumn("status");
		tabModel.addColumn("ticketPrice");
		tabModel.addColumn("introduction");
		
		//初始化列明
		jt=new JTable(tabModel);	
		
		//设置各列的宽度
	    TableColumnModel columnModel = jt.getColumnModel();
	    
	    //隐藏ID这一列
        TableColumn column = columnModel.getColumn(0);
//        column.setMinWidth(0);
//        column.setMaxWidth(0);
//        column.setWidth(0);
        column.setPreferredWidth(10);

        column = columnModel.getColumn(1);
        column.setPreferredWidth(10);
        column = columnModel.getColumn(2);
        column.setPreferredWidth(10);
        column = columnModel.getColumn(3);
        column.setPreferredWidth(10);
        column = columnModel.getColumn(4);
        column.setPreferredWidth(10);    
        column = columnModel.getColumn(5);
        column.setPreferredWidth(10);
        column = columnModel.getColumn(6);
        column.setPreferredWidth(300);
//        column = columnModel.getColumn(7);
//        column.setPreferredWidth(300);       

		
		jp.add(jt);
		jp.setViewportView(jt);
		
	}
//Object[] in = { "id", "name", "image", "length","status","ticketprice" "introduction"};

	public Play getPlay() {
		int rowSel=jt.getSelectedRow();
		System.out.println(rowSel);
		if(rowSel>=0){
			Play stud = new Play();
			stud.setId(Integer.parseInt(jt.getValueAt(rowSel, 0).toString()));
			stud.setName(jt.getValueAt(rowSel, 1).toString());
		
			stud.setLength(Integer.parseInt(""+jt.getValueAt(rowSel, 3)));
			stud.setStatus(Integer.parseInt(""+jt.getValueAt(rowSel, 4)));
			stud.setTicketPrice(Float.parseFloat(""+jt.getValueAt(rowSel, 5)));
			if(jt.getValueAt(rowSel, 6)!=null){
			stud.setIntroduction(jt.getValueAt(rowSel, 6).toString());

			}
			else{
				stud.setIntroduction(" ");
			}
			return stud;
		}
		else{
			return null;
		}
			
	}
	
	// 创建JTable
	public void showPlayList(List<Play> stuList) {
		try {
			DefaultTableModel tabModel = (DefaultTableModel) jt.getModel();
			tabModel.setRowCount(0);
//Object[] in = { "id", "name", "image", "length","status","ticketprice" "introduction"};

			Iterator<Play> itr = stuList.iterator();
			while (itr.hasNext()) {
				Play stu = itr.next();
				Object data[] = new Object[7];
				data[0] = Integer.toString(stu.getId());
				data[1] = stu.getName();
				data[2] = stu.getImage();
				data[3] = Integer.toString(stu.getLength());
				data[4] = Integer.toString(stu.getStatus());
				data[5] = Float.toString(stu.getTicketPrice());
				data[6] = stu.getIntroduction();
				tabModel.addRow(data);;
			}
			jt.invalidate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class PlayMgrUI extends MainUITmpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel ca1 = null; // 界面提示
	// 用来放表格的滚动控件
	private JScrollPane jsc;
	// 查找的提示和输出
	private JLabel hint;
	private JTextField input;

	// 查找，编辑和删除按钮
	private JButton btnAdd, btnEdit, btnDel, btnQuery;
	
	PlayTable tms; //显示演出厅列表


	public PlayMgrUI() {
		
	}

	// To be override by the detailed business block interface
	@Override
	protected void initContent() {
		Rectangle rect = contPan.getBounds();

		ca1 = new JLabel("剧目管理", JLabel.CENTER);
		ca1.setBounds(0, 5, rect.width, 30);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		contPan.add(ca1);

		jsc = new JScrollPane();
		jsc.setBounds(0, 40, rect.width, rect.height - 90);
		contPan.add(jsc);

		hint = new JLabel("请输入剧目名称:", JLabel.RIGHT);
		hint.setBounds(60, rect.height - 45, 150, 30);
		contPan.add(hint);

		input = new JTextField();
		input.setBounds(220, rect.height - 45, 200, 30);
		contPan.add(input);

		// 查找 ，删除和编辑的按钮，其中含有相关的事件处理！
		btnQuery = new JButton("查找");
		btnQuery.setBounds(440, rect.height - 45, 60, 30);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQueryClicked();
			}
		});
		contPan.add(btnQuery);

		btnAdd = new JButton("添加");
		btnAdd.setBounds(rect.width - 220, rect.height - 45, 60, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnAddClicked();
			}
		});
		contPan.add(btnAdd);

		btnEdit = new JButton("修改");
		btnEdit.setBounds(rect.width - 150, rect.height - 45, 60, 30);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnModClicked();
			}
		});
		contPan.add(btnEdit);

		btnDel = new JButton("删除");
		btnDel.setBounds(rect.width - 80, rect.height - 45, 60, 30);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnDelClicked();
			}
		});
		contPan.add(btnDel);
		contPan.add(ca1);
		
		tms = new PlayTable(jsc);
		
		showTable();
	}

	private void btnAddClicked() {

		PlayAddUI addStuUI=null;
		
		addStuUI = new PlayAddUI();
		addStuUI.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addStuUI.setWindowName("添加剧目");
		addStuUI.toFront();
		addStuUI.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		addStuUI.setVisible(true);
		if (addStuUI.getReturnStatus()) {
			showTable();
		}
	}

	private void btnModClicked() {
		Play stud = tms.getPlay();
		if(null== stud){
			JOptionPane.showMessageDialog(null, "请选择要修改的演出厅");
			return; 
		}
		
		PlayEditUI modStuUI = new PlayEditUI(stud);
		modStuUI.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		modStuUI.setWindowName("修改剧目");
		modStuUI.initData(stud);
		modStuUI.toFront();
		modStuUI.setModal(true);
		modStuUI.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		modStuUI.setVisible(true);

		if (modStuUI.getReturnStatus()) {
			showTable();
		}	
	}

	private void btnDelClicked() {
		Play stud = tms.getPlay();
		if(null== stud){
			JOptionPane.showMessageDialog(null, "请选择要删除的剧目");
			return; 
		}		
		
		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			PlaySrv stuSrv = new PlaySrv();
			stuSrv.delete(stud.getId());
			showTable();
		}
	}

	private void btnQueryClicked() {
		if (!input.getText().equals("")) {
			//请自行补充

		} else {
			JOptionPane.showMessageDialog(null, "请输入检索条件");
		}
	}

	private void showTable() {
		List<Play> stuList = new PlaySrv().FetchAll();
		tms.showPlayList(stuList);
	}
	

	public static void main(String[] args) {
		PlayMgrUI frmStuMgr = new PlayMgrUI();
		frmStuMgr.setVisible(true);
	}
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
		new Manager().setVisible(true);
		this.dispose();
		
	}	
}


//package xupt.se.ttms.view.studio;
///**
// * 演出厅管理
// */
//import java.awt.Color;
//import java.awt.Label;
//import java.awt.Rectangle;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//
//import java.util.List;
//import java.util.Iterator;
//
//import xupt.se.ttms.model.Studio;
//import xupt.se.ttms.service.StudioSrv;
//import xupt.se.ttms.view.login.Login;
//import xupt.se.ttms.view.login.SystemMgUI;
//import xupt.se.ttms.view.tmpl.*;
//
//class StudioTableMouseListener extends MouseAdapter {
//
//	private JTable jt;
//	private static Studio stud;
//
//	public Studio getStudio() {
//		return stud;
//	}
//
//	public StudioTableMouseListener(JTable jt, Object[] number, Studio stud) {
//		this.stud = stud;
//		this.jt = jt;
//	}
//
//	// 监听到行号，将所选行的内容依次赋到 stud对象，以便传有值对象到修改面板进行修改
//	public void mouseClicked(MouseEvent event) {
//		int row = jt.getSelectedRow();
//		stud.setID(Integer.parseInt(jt.getValueAt(row, 0).toString()));
//		stud.setName(jt.getValueAt(row, 1).toString());
//		stud.setRowCount(Integer.parseInt(jt.getValueAt(row, 2).toString())); // 0
//		stud.setColCount(Integer.parseInt(jt.getValueAt(row, 3).toString()));
//		if (jt.getValueAt(row, 4) != null)
//			stud.setIntroduction(jt.getValueAt(row, 4).toString());
//		else
//			stud.setIntroduction("");
//		System.out.println(jt.getValueAt(row, 0).toString());
//	}
//}
//
//class StudioTable {
//
//	private Studio stud;
//	private JTable jt = null;
//
//	public StudioTable(Studio stud) {
//		this.stud = stud;
//	}
//
//	// 创建JTable
//	public void createTable(JScrollPane jp, Object[] columnNames, List<Studio> stuList) {
//		try {
//
//			Object data[][] = new Object[stuList.size()][columnNames.length];
//
//			Iterator<Studio> itr = stuList.iterator();
//			int i = 0;
//			while (itr.hasNext()) {
//				Studio stu = itr.next();
//				data[i][0] = Integer.toString(stu.getID());
//				data[i][1] = stu.getName();
//				data[i][2] = Integer.toString(stu.getRowCount());
//				data[i][3] = Integer.toString(stu.getColCount());
//				data[i][4] = stu.getIntroduction();
//				i++;
//			}
//
//			// 生成JTable
//			jt = new JTable(data, columnNames);
//			jt.setBounds(0, 0, 700, 450);
//
//			// 添加鼠标监听，监听到所选行
//			StudioTableMouseListener tml = new StudioTableMouseListener(jt, columnNames, stud);
//			jt.addMouseListener(tml);
//
//			// 设置可调整列宽
//			jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//			jp.add(jt);
//			jp.setViewportView(jt);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//public class StudioMgrUI extends MainUITmpl {
//	/**
//	 * 
//	 */
//	
//	protected void onWindowClosing(){
//		this.dispose();
//	}
//	
//	private static final long serialVersionUID = 1L;
//	private Studio stud=new Studio();
//	private JLabel ca1 = null; // 界面提示
//	// 用来放表格的滚动控件
//	private JScrollPane jsc;
//	// 查找的提示和输出
//	private JLabel hint;
//	private JTextField input;
//
//	// 查找，编辑和删除按钮
//	private JButton btnAdd, btnEdit, btnDel, btnQuery;
//
//	public StudioMgrUI() {
//		showTable();
//	}
//
//	// To be override by the detailed business block interface
//	@Override
//	protected void initContent() {
//		Rectangle rect = contPan.getBounds();
//
//		ca1 = new JLabel("演出厅管理", JLabel.CENTER);
//		ca1.setBounds(0, 5, rect.width, 30);
//		ca1.setFont(new java.awt.Font("宋体", 1, 20));
//		ca1.setForeground(Color.blue);
//		contPan.add(ca1);
//
//		jsc = new JScrollPane();
//		jsc.setBounds(0, 40, rect.width, rect.height - 150);
//		contPan.add(jsc);
//
//		hint = new JLabel("请输入演出厅名称:", JLabel.RIGHT);
//		hint.setBounds(60, rect.height - 80, 150, 30);
//		contPan.add(hint);
//
//		input = new JTextField();
//		input.setBounds(220, rect.height - 80, 200, 30);
//		contPan.add(input);
//
//		// 查找 ，删除和编辑的按钮，其中含有相关的事件处理！
//		btnQuery = new JButton("查找");
//		btnQuery.setBounds(440, rect.height - 80, 60, 30);
//		btnQuery.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnQueryClicked();
//			}
//		});
//		contPan.add(btnQuery);
//
//		btnAdd = new JButton("添加");
//		btnAdd.setBounds(rect.width - 220, rect.height - 80, 60, 30);
//		btnAdd.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnAddClicked();
//			}
//		});
//		contPan.add(btnAdd);
//
//		btnEdit = new JButton("修改");
//		btnEdit.setBounds(rect.width - 150, rect.height - 80, 60, 30);
//		btnEdit.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnModClicked();
//			}
//		});
//		contPan.add(btnEdit);
//
//		btnDel = new JButton("删除");
//		btnDel.setBounds(rect.width - 80, rect.height - 80, 60, 30);
//		btnDel.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnDelClicked();
//			}
//		});
//		contPan.add(btnDel);
//		contPan.add(ca1);
//		showTable();
//	}
//
//	private void btnAddClicked() {
//		StudioAddUI addStud = new StudioAddUI();
//		addStud.setWindowName("添加演出厅");
//		addStud.toFront();
//		addStud.setModal(true);
//		addStud.setVisible(true);
//
//		if (addStud.getReturnStatus()) {
//			showTable();
//		}
//	}
//
//	private void btnModClicked() {
//			
//		StudioEditUI modStu = new StudioEditUI(stud);
//		modStu.setWindowName("修改演出厅");
//		modStu.toFront();
//		modStu.setModal(true);
//		modStu.setVisible(true);
//		if (modStu.getReturnStatus()) {
//			showTable();
//		}
//		showTable();
//	}
//
//	private void btnDelClicked() {
//		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
//		if (confirm == JOptionPane.YES_OPTION) {
//			StudioSrv stuSrv = new StudioSrv();
//			stuSrv.delete(stud.getID());
//			showTable();
//		}
//	}
//
//	private void btnQueryClicked() {
//		if (!input.getText().equals("")) {
//
//			JOptionPane.showMessageDialog(null, "哈哈哈哈");
//
//		} else {
//			JOptionPane.showMessageDialog(null, "请输入检索条件");
//		}
//	}
//
//	public void showTable() {
//		StudioTable tms = new StudioTable(stud);
//		Object[] in = { "id", "name", "row", "column", "studio desciption" };
//		List<Studio> stuList = new StudioSrv().FetchAll();
//
//		tms.createTable(jsc, in, stuList);
//		jsc.repaint();
//	}
//
//	public static void main(String[] args) {
//		StudioMgrUI frmStuMgr = new StudioMgrUI();
//		frmStuMgr.setVisible(true);
//	}
//	protected void btnExitClicked(ActionEvent Event){
//		
//		new SystemMgUI().setVisible(true);
//		this.dispose();
//
////		this.getParent().setVisible(true);
////		new Login().setVisible(true);;
////		SystemMgUI  smg = new SystemMgUI();
////		smg.setVisible(true);
//	}	
//}


//import java.awt.Color;
//import java.awt.Rectangle;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.List;
//
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JScrollPane;
//import javax.swing.JTextField;
//
//import java.awt.Color;
//import java.awt.Label;
//import java.awt.Rectangle;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//
//import java.util.List;
//import java.util.Iterator;
//
//import xupt.se.ttms.model.Play;
//import xupt.se.ttms.model.Studio;
//import xupt.se.ttms.service.PlaySrv;
//import xupt.se.ttms.service.StudioSrv;
//import xupt.se.ttms.view.login.Login;
//import xupt.se.ttms.view.login.Manager;
//import xupt.se.ttms.view.login.SystemMgUI;
//import xupt.se.ttms.view.tmpl.*;
//
//class PlayTableMouseListener extends MouseAdapter {
//
//	private JTable jt;
//	private static Play stud;
//
//	public Play getPlay() {
//		return stud;
//	}
//
//	public PlayTableMouseListener(JTable jt, Object[] number, Play stud) {
//		this.stud = stud;
//		this.jt = jt;
//	}
////Object[] in = { "id", "name", "image", "introduction", "length","status","ticketprice" };
//
//
//	// 监听到行号，将所选行的内容依次赋到 stud对象，以便传有值对象到修改面板进行修改
//	public void mouseClicked(MouseEvent event) {
//		int row = jt.getSelectedRow();
//		stud.setId(Integer.parseInt(jt.getValueAt(row, 0).toString()));
//		stud.setName(jt.getValueAt(row, 1).toString());
//		stud.setImage((int[]) jt.getValueAt(row, 2));
//		stud.setIntroduction(jt.getValueAt(row, 3).toString());
//		stud.setLength(Integer.parseInt(jt.getValueAt(row, 4).toString()));
//		stud.setStatus(Integer.parseInt(jt.getValueAt(row, 5).toString()));
//		stud.setTicketPrice(Float.parseFloat(jt.getValueAt(row, 6).toString()));
////		stud.setID(Integer.parseInt(jt.getValueAt(row, 0).toString()));
////		stud.setName(jt.getValueAt(row, 1).toString());
////		stud.setRowCount(Integer.parseInt(jt.getValueAt(row, 2).toString())); // 0
////		stud.setColCount(Integer.parseInt(jt.getValueAt(row, 3).toString()));
////		if (jt.getValueAt(row, 4) != null)
////			stud.setIntroduction(jt.getValueAt(row, 4).toString());
////		else
////			stud.setIntroduction("");
////		System.out.println(jt.getValueAt(row, 0).toString());
//	}
//}
//
//class PlayTable {
//
//	private Play stud;
//	private JTable jt = null;
//
//	public PlayTable(Play stud) {
//		this.stud = stud;
//	}
//
//	// 创建JTable
//	public void createTable(JScrollPane jp, Object[] columnNames, List<Play> stuList) {
//		try {
//
//			Object data[][] = new Object[stuList.size()][columnNames.length];
//
//			Iterator<Play> itr = stuList.iterator();
//			int i = 0;
////Object[] in = { "id", "name", "image", "introduction", "length","status","ticketprice" };
//
//			while (itr.hasNext()) {
//				Play stu = itr.next();
//				data[i][0] = stu.getId();
//				data[i][1] = stu.getName();
//				data[i][2] = stu.getImage();
//				data[i][3] = stu.getIntroduction();
//				data[i][4] = stu.getLength();
//				data[i][5] = stu.getStatus();
//				data[i][6] = stu.getTicketPrice();
//				
////				data[i][0] = Integer.toString(stu.getID());
////				data[i][1] = stu.getName();
////				data[i][2] = Integer.toString(stu.getRowCount());
////				data[i][3] = Integer.toString(stu.getColCount());
////				data[i][4] = stu.getIntroduction();
//				i++;
//			}
//
//			// 生成JTable
//			jt = new JTable(data, columnNames);
//			jt.setBounds(0, 0, 700, 450);
//
//			// 添加鼠标监听，监听到所选行
//			PlayTableMouseListener tml = new PlayTableMouseListener(jt, columnNames, stud);
//			jt.addMouseListener(tml);
//
//			// 设置可调整列宽
//			jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//			jp.add(jt);
//			jp.setViewportView(jt);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//
//public class PlayMgUI extends MainUITmpl {
//	/**
//	 * 
//	 */
//	
//	protected void onWindowClosing(){
//		this.dispose();
//	}
//	
//	private static final long serialVersionUID = 1L;
//	private Play stud=new Play();
//	private JLabel ca1 = null; // 界面提示
//	// 用来放表格的滚动控件
//	private JScrollPane jsc;
//	// 查找的提示和输出
//	private JLabel hint;
//	private JTextField input;
//
//	// 查找，编辑和删除按钮
//	private JButton btnAdd, btnEdit, btnDel, btnQuery;
//
//	public PlayMgUI() {
//		showTable();
//	}
//
//	// To be override by the detailed business block interface
//	@Override
//	protected void initContent() {
//		Rectangle rect = contPan.getBounds();
//
//		ca1 = new JLabel("剧目管理", JLabel.CENTER);
//		ca1.setBounds(0, 5, rect.width, 30);
//		ca1.setFont(new java.awt.Font("宋体", 1, 20));
//		ca1.setForeground(Color.blue);
//		contPan.add(ca1);
//
//		jsc = new JScrollPane();
//		jsc.setBounds(0, 40, rect.width, rect.height - 150);
//		contPan.add(jsc);
//
//		hint = new JLabel("请输入演出厅名称:", JLabel.RIGHT);
//		hint.setBounds(60, rect.height - 80, 150, 30);
//		contPan.add(hint);
//
//		input = new JTextField();
//		input.setBounds(220, rect.height - 80, 200, 30);
//		contPan.add(input);
//
//		// 查找 ，删除和编辑的按钮，其中含有相关的事件处理！
//		btnQuery = new JButton("查找");
//		btnQuery.setBounds(440, rect.height - 80, 60, 30);
//		btnQuery.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnQueryClicked();
//			}
//		});
//		contPan.add(btnQuery);
//
//		btnAdd = new JButton("添加");
//		btnAdd.setBounds(rect.width - 220, rect.height - 80, 60, 30);
//		btnAdd.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnAddClicked();
//			}
//		});
//		contPan.add(btnAdd);
//
//		btnEdit = new JButton("修改");
//		btnEdit.setBounds(rect.width - 150, rect.height - 80, 60, 30);
//		btnEdit.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnModClicked();
//			}
//		});
//		contPan.add(btnEdit);
//
//		btnDel = new JButton("删除");
//		btnDel.setBounds(rect.width - 80, rect.height - 80, 60, 30);
//		btnDel.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnDelClicked();
//			}
//		});
//		contPan.add(btnDel);
//		contPan.add(ca1);
//		showTable();
//	}
//
//	private void btnAddClicked() {
//		PlayAddUI addStud = new PlayAddUI();
//		addStud.setWindowName("添加演出厅");
//		addStud.toFront();
//		addStud.setModal(true);
//		addStud.setVisible(true);
//
//		if (addStud.getReturnStatus()) {
//			showTable();
//		}
//	}
//
//	private void btnModClicked() {
//			
////		StudioEditUI modStu = new StudioEditUI(stud);
////		modStu.setWindowName("修改演出厅");
////		modStu.toFront();
////		modStu.setModal(true);
////		modStu.setVisible(true);
////		if (modStu.getReturnStatus()) {
////			showTable();
////		}
////		showTable();
//	}
//
//	private void btnDelClicked() {
//		
//		Play stud = tms.getStudio();
//		if(null== stud){
//			JOptionPane.showMessageDialog(null, "请选择要删除的演出厅");
//			return; 
//		}		
//		
//		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
//		if (confirm == JOptionPane.YES_OPTION) {
//			PlaySrv stuSrv = new PlaySrv();
//			stuSrv.delete(stud.getId());
//			showTable();
//		}
//	}
//
//	private void btnQueryClicked() {
//		if (!input.getText().equals("")) {
//
//			JOptionPane.showMessageDialog(null, "哈哈哈哈");
//
//		} else {
//			JOptionPane.showMessageDialog(null, "请输入检索条件");
//		}
//	}
//
//	public void showTable() {
//		PlayTable tms = new PlayTable(stud);
//		Object[] in = { "id", "name", "image", "introduction", "length","status","ticketprice" };
//		List<Play> stuList = new PlaySrv().FetchAll();
//
////		System.out.println(stuList.toString());
//		tms.createTable(jsc, in, stuList);
//		jsc.repaint();
//	}
//
//	public static void main(String[] args) {
//		PlayMgUI frmStuMgr = new PlayMgUI();
//		frmStuMgr.setVisible(true);
//	}
//	protected void btnExitClicked(ActionEvent Event){
//		
//		new Manager().setVisible(true);
//		this.dispose();
//
////		this.getParent().setVisible(true);
////		new Login().setVisible(true);;
////		SystemMgUI  smg = new SystemMgUI();
////		smg.setVisible(true);
//	}	
//}
