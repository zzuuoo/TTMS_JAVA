package xupt.se.ttms.view.play;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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

		tabModel.addColumn("ID");
		tabModel.addColumn("名字");
		tabModel.addColumn("image");
		tabModel.addColumn("时长");
		tabModel.addColumn("状态");
		tabModel.addColumn("票价");
		tabModel.addColumn("简介");
		
		//初始化列明
		jt=new JTable(tabModel);	
		
//
		jt.setSelectionBackground(Color.green);
////		jt.setGridColor(Color.orange);
		JTableHeader th = jt.getTableHeader();
//		th.setResizingAllowed(true);
		th.setFont(new Font("宋体",3,15));
		th.setPreferredSize(new Dimension(jt.getWidth(), 30));
		jt.setRowHeight(20);
		jt.setFont(new Font("宋体",1,12));
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	       render.setHorizontalAlignment(SwingConstants.CENTER);
	       
	       jt.getColumn("ID").setCellRenderer(render);
	       jt.getColumn("名字").setCellRenderer(render);
	       jt.getColumn("image").setCellRenderer(render);
	       jt.getColumn("时长").setCellRenderer(render);
	       jt.getColumn("票价").setCellRenderer(render);
	       jt.getColumn("状态").setCellRenderer(render);
	       jt.getColumn("简介").setCellRenderer(render);
		
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
			
			if((jt.getValueAt(rowSel, 4)+"").equals("待安排演出")){
				stud.setStatus(0);
			}else if((jt.getValueAt(rowSel, 4)+"").equals("已安排演出")){
				stud.setStatus(1);
			}else {
				stud.setStatus(-1);
			}
			
//			stud.setStatus(Integer.parseInt(""+jt.getValueAt(rowSel, 4)));
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
				
//				data[4] = Integer.toString(stu.getStatus());
				if(stu.getStatus()==0){
					data[4]="待安排演出";
				}else if(stu.getStatus()==1){
					data[4]="已安排演出";
				}else {
					data[4]="已下线";
				}
				
				
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


