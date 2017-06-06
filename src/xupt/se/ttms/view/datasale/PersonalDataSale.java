package xupt.se.ttms.view.datasale;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.lang.model.type.TypeKind;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import java.util.Date;
import java.util.Iterator;

import xupt.se.ttms.model.GlobalVariable;
import xupt.se.ttms.model.Play;
import xupt.se.ttms.model.Sale;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.SaleSrv;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.login.Manager;
import xupt.se.ttms.view.login.Seller;
import xupt.se.ttms.view.login.SystemMgUI;
import xupt.se.ttms.view.tmpl.*;

class PersonDataSaleTable {
	/**
	 * 剧目表格绘制
	 * table.getTableHeader().setReorderingAllowed(false);   //不可整列移动   
  	table.getTableHeader().setResizingAllowed(false);   //不可拉动表格
	 */
	private static final long serialVersionUID = 1L;
	private JTable jt;

	public PersonDataSaleTable(JScrollPane jp) {
		
		DefaultTableModel tabModel=new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override              
			public boolean isCellEditable(int row,int column){
				return false;              
			};
		};
//		private int id;
//		private int empId;
//		private Date time ; 
//		private float payment;
//		private float change;
//		private int type;  // 1：销售单  -1：退款单
//		private int status;  // 0：待付款   1：已付款
		tabModel.addColumn("ID");
		tabModel.addColumn("empID");
		tabModel.addColumn("时间");
		tabModel.addColumn("收款");
		tabModel.addColumn("找零");
		tabModel.addColumn("单据");
		tabModel.addColumn("状态");
		
		
		//初始化列明
		jt=new JTable(tabModel);	
		jt.setSelectionBackground(Color.green);
		JTableHeader th = jt.getTableHeader();
		th.setFont(new Font("宋体",3,20));
		th.setPreferredSize(new Dimension(jt.getWidth(), 30));
		jt.setRowHeight(20);
		jt.setFont(new Font("宋体",1,16));
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	       render.setHorizontalAlignment(SwingConstants.CENTER);
	       
	       jt.getColumn("ID").setCellRenderer(render);
	       jt.getColumn("empID").setCellRenderer(render);
	       jt.getColumn("时间").setCellRenderer(render);
	       jt.getColumn("收款").setCellRenderer(render);
	       jt.getColumn("找零").setCellRenderer(render);
	       jt.getColumn("单据").setCellRenderer(render);
	       jt.getColumn("状态").setCellRenderer(render);
	       
	       
	       jt.getTableHeader().setReorderingAllowed(false);
	       jt.getTableHeader().setResizingAllowed(false); 
		//设置各列的宽度
	    TableColumnModel columnModel = jt.getColumnModel();
	    
	    //隐藏ID这一列
        TableColumn column = columnModel.getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setWidth(0);
//        column.setPreferredWidth(10);
        
        

        column = columnModel.getColumn(1);
        column.setPreferredWidth(10);
        column = columnModel.getColumn(2);
        column.setPreferredWidth(100);
        column = columnModel.getColumn(3);
        column.setPreferredWidth(10);
        column = columnModel.getColumn(4);
        column.setPreferredWidth(10);    
        column = columnModel.getColumn(5);
        column.setPreferredWidth(10);
        column = columnModel.getColumn(6);
        column.setPreferredWidth(10);
//        column = columnModel.getColumn(7);
//        column.setPreferredWidth(300);       

		
		jp.add(jt);
		jp.setViewportView(jt);
		
	}
//	tabModel.addColumn("ID");
//	tabModel.addColumn("empID");
//	tabModel.addColumn("时间");
//	tabModel.addColumn("收款");
//	tabModel.addColumn("change");
//	tabModel.addColumn("单据");
//	tabModel.addColumn("状态");
	
	public Sale getSale() {
		int rowSel=jt.getSelectedRow();
//		System.out.println(rowSel);
		SimpleDateFormat sdf = new SimpleDateFormat("YY-MM-dd HH:mm");
		if(rowSel>=0){
			
//			if(stu.getType()==1){
//				data[5]="销售单";
//			}else{
//				data[5]="退款单";
//			}
////			data[6] = stu.getStatus();
//			if(stu.getStatus()==0){
//				data[6]="待付款";
//			}else{
//				data[6]="已付款";
//			}
			Sale stud = new Sale();
			stud.setId(Integer.parseInt(jt.getValueAt(rowSel, 0).toString()));
			stud.setEmpId(Integer.parseInt(jt.getValueAt(rowSel,1).toString()));
			try {
				stud.setTime(sdf.parse(jt.getValueAt(rowSel, 2).toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				stud.setTime(null);
			}
			stud.setPayment(Float.parseFloat(jt.getValueAt(rowSel, 3).toString()));
			stud.setChange(Float.parseFloat(jt.getValueAt(rowSel, 4).toString()));
			if(jt.getValueAt(rowSel, 5).toString().equals("销售单")){
				stud.setType(1);
			}else{
				stud.setType(-1);
			}
			if(jt.getValueAt(rowSel, 6).toString().equals("待付款")){
				stud.setType(0);
			}else if(jt.getValueAt(rowSel, 6).toString().equals("已付款")){
				stud.setType(1);
			}else if(jt.getValueAt(rowSel, 6).toString().equals("已退款")){
				stud.setType(2);
			}
//			stud.setType(Integer.parseInt(jt.getValueAt(rowSel, 5).toString()));
//			stud.setStatus(Integer.parseInt(jt.getValueAt(rowSel, 6).toString()));
		
			return stud;
		}
		else{
			return null;
		}
			
	}
	
	// 创建JTable
	public void showPlayList(List<Sale> stuList) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
		try {
			DefaultTableModel tabModel = (DefaultTableModel) jt.getModel();
			tabModel.setRowCount(0);
//			tabModel.addColumn("ID");
//			tabModel.addColumn("empID");
//			tabModel.addColumn("时间");
//			tabModel.addColumn("收款");
//			tabModel.addColumn("change");
//			tabModel.addColumn("单据");
//			tabModel.addColumn("状态");
//			private int type;  // 1：销售单  -1：退款单
//			private int status;  // 0：待付款   1：已付款 2：已退款
			if(stuList==null)
				return ;
			Iterator<Sale> itr = stuList.iterator();
			while (itr.hasNext()) {
				Sale stu = itr.next();
				Object data[] = new Object[7];
				data[0] = Integer.toString(stu.getId());
				data[1] = stu.getEmpId();
				data[2] = sdf.format(stu.getTime());
				data[3] = stu.getPayment();
				data[4] = stu.getChange();
//				data[5] = stu.getType();
				if(stu.getType()==1){
					data[5]="销售单";
				}else{
					data[5]="退款单";
				}
//				data[6] = stu.getStatus();
				if(stu.getStatus()==0){
					data[6]="待付款";
				}else if(stu.getStatus()==1){
					data[6]="已付款";
				}else if(stu.getStatus()==2){
					data[6]="已退款";
				}
				tabModel.addRow(data);;
			}
			jt.invalidate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class PersonalDataSale extends MainUITmpl {
	/**
	 * author 剧目管理
	 */
	private static final long serialVersionUID = 1L;
	private JLabel ca1 = null; // 界面提示
	// 用来放表格的滚动控件
	private JScrollPane jsc;
	// 查找的提示和输出
	private JLabel hint;
	private JTextField input;
	protected JComboBox inquery;
	// 查找，编辑和删除按钮
	private JButton  btnEdit, btnQuery;
	
	PersonDataSaleTable tms; //显示演出厅列表


	public PersonalDataSale() {
		
	}

	// To be override by the detailed business block interface
	@Override
	protected void initContent() {
		Rectangle rect = contPan.getBounds();

		ca1 = new JLabel("个人销售数据", JLabel.CENTER);
		ca1.setBounds(0, 5, rect.width, 30);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		contPan.add(ca1);

		jsc = new JScrollPane();
		jsc.setBounds(0, 40, rect.width, rect.height - 100);
		contPan.add(jsc);

		hint = new JLabel("请输入名称:", JLabel.RIGHT);
		hint.setFont(new Font("",1,15));
		hint.setBounds(60, rect.height - 50, 150, 30);
		contPan.add(hint);

		input = new JTextField();
		input.setBounds(220, rect.height - 50, 200, 30);
		contPan.add(input);

		String [] inqueryType = {"按单据查找","按状态查找","按收款查找"};
		inquery = new JComboBox(inqueryType);
		inquery.setBounds(440, rect.height - 50, 130, 30);
		contPan.add(inquery);

		// 查找 ，删除和编辑的按钮，其中含有相关的事件处理！
		btnQuery = new JButton("查找");
		btnQuery.setBounds(600, rect.height - 50, 60, 30);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQueryClicked();
			}
		});
		contPan.add(btnQuery);


		btnEdit = new JButton("查看详细账单");
		btnEdit.setBounds(rect.width - 200, rect.height - 50, 120, 30);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnModClicked();
			}
		});
		contPan.add(btnEdit);

		
		tms = new PersonDataSaleTable(jsc);
		
		showTable();
	}

	

	private void btnModClicked() {
		
		Sale stud = tms.getSale();
		if(null== stud){
			JOptionPane.showMessageDialog(null, "请选择要查看的账单");
			return; 
		}	
		
		new ItemSale(stud).setVisible(true);
		this.dispose();

	}


	private void btnQueryClicked() {
		if (!input.getText().equals("")) {
//			//请自行补充
		} else {
			JOptionPane.showMessageDialog(null, "请输入检索条件");
//			showTable();
		}
	}

	private void showTable() {
		List<Sale> stuList = new SaleSrv().Fetch(" emp_id = "+GlobalVariable.emp_id);
		tms.showPlayList(stuList);
	}
	

	public static void main(String[] args) {
		PersonalDataSale frmStuMgr = new PersonalDataSale();
		frmStuMgr.setVisible(true);
	}
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
		new Seller().setVisible(true);
//		System.out.println(GlobalVariable.emp_id);
		this.dispose();
		
	}	
}


