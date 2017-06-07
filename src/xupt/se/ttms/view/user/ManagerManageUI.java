package xupt.se.ttms.view.user;

//public class ManagerManageUI {
//
//}

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
import java.util.Iterator;
import java.util.LinkedList;

import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Play;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.EmployeeSrv;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.login.Manager;
import xupt.se.ttms.view.login.SystemMgUI;
import xupt.se.ttms.view.tmpl.*;

class ManagerTable {
	/**
	 * 经理表格绘制
	 */
	private static final long serialVersionUID = 1L;
	private JTable jt;

	public ManagerTable(JScrollPane jp) {
		
		DefaultTableModel tabModel=new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override              
			public boolean isCellEditable(int row,int column){
				return false;              
			};
		};


		tabModel.addColumn("工号");
		tabModel.addColumn("姓名");
		tabModel.addColumn("身份");
		tabModel.addColumn("账号");
		tabModel.addColumn("电话");
		tabModel.addColumn("住址");
		tabModel.addColumn("邮箱");
		
		//初始化列明
		jt=new JTable(tabModel);	
		jt.setSelectionBackground(Color.green);
		JTableHeader th = jt.getTableHeader();
		th.setFont(new Font("宋体",3,15));
		th.setPreferredSize(new Dimension(jt.getWidth(), 30));
		jt.setRowHeight(20);
		jt.setFont(new Font("宋体",1,12));
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	       render.setHorizontalAlignment(SwingConstants.CENTER);
	       
	       jt.getColumn("工号").setCellRenderer(render);
	       jt.getColumn("姓名").setCellRenderer(render);
	       jt.getColumn("身份").setCellRenderer(render);
	       jt.getColumn("账号").setCellRenderer(render);
	       jt.getColumn("电话").setCellRenderer(render);
	       jt.getColumn("住址").setCellRenderer(render);
	       jt.getColumn("邮箱").setCellRenderer(render);
	       jt.getTableHeader().setReorderingAllowed(false); 
	       jt.getTableHeader().setResizingAllowed(false); 
//	        table.getTableHeader().setReorderingAllowed(false);   //不可整列移动   
//	     	table.getTableHeader().setResizingAllowed(false);   //不可拉动表格
		
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
        column.setPreferredWidth(10);
//        column = columnModel.getColumn(7);
//        column.setPreferredWidth(300);       

		
		jp.add(jt);
		jp.setViewportView(jt);
		
	}
//Object[] in = { "id", "name", "image", "length","status","ticketprice" "introduction"};

	public Employee getManager() {
		int rowSel=jt.getSelectedRow();
		System.out.println(rowSel);
		if(rowSel>=0){
			Employee stud = new Employee();
			stud.setEmp_id(Integer.parseInt(jt.getValueAt(rowSel, 0).toString()));
			stud.setEmp_name(jt.getValueAt(rowSel, 1).toString());
//			stud.setStatus(jt.getValueAt(rowSel, 2).toString());
//			stud.setAccount(jt.getValueAt(rowSel, 3).toString());
			stud.setEmp_tel_num(jt.getValueAt(rowSel, 4).toString());
			stud.setEmp_addr(jt.getValueAt(rowSel, 5).toString());
			stud.setEmp_email(jt.getValueAt(rowSel, 6).toString());
			return stud;
		}
		else{
			return null;
		}
			
	}
	
	// 创建JTable
	public void showManagerList(List<Employee> stuList) {
		try {
			DefaultTableModel tabModel = (DefaultTableModel) jt.getModel();
			tabModel.setRowCount(0);
//			  jt.getColumn("工号").setCellRenderer(render);
//		       jt.getColumn("姓名").setCellRenderer(render);
//		       jt.getColumn("身份").setCellRenderer(render);
//		       jt.getColumn("账号").setCellRenderer(render);
//		       jt.getColumn("电话").setCellRenderer(render);
//		       jt.getColumn("住址").setCellRenderer(render);
//		       jt.getColumn("邮箱").setCellRenderer(render);
			Iterator<Employee> itr = stuList.iterator();
			while (itr.hasNext()) {
				Employee stu = itr.next();
				Object data[] = new Object[7];
				data[0] = stu.getEmp_id();
				data[1] = stu.getEmp_name();
				data[2] =stu .getStatus();
				data[3] =stu .getAccount();
				data[4] =stu.getEmp_tel_num();
				data[5] =stu.getEmp_addr();
				data[6] =stu .getEmp_email();
				tabModel.addRow(data);;
			}
			jt.invalidate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class ManagerManageUI extends MainUITmpl {
	/**
	 * author 经理用户管理
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
	private JButton btnAdd, btnEdit, btnDel, btnQuery;
	
	ManagerTable tms; //显示经理用户列表


	public ManagerManageUI() {
		
	}

	// To be override by the detailed business block interface
	@Override
	protected void initContent() {
		Rectangle rect = contPan.getBounds();

		ca1 = new JLabel("用户管理", JLabel.CENTER);
		ca1.setBounds(0, 5, rect.width, 30);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		contPan.add(ca1);

		jsc = new JScrollPane();
		jsc.setBounds(0, 40, rect.width, rect.height - 100);
		contPan.add(jsc);

//		hint = new JLabel("请输入用户名称:", JLabel.RIGHT);
//		hint.setFont(new Font("",1,15));
//		hint.setBounds(60, rect.height - 50, 150, 30);
//		contPan.add(hint);
//
//		input = new JTextField();
//		input.setBounds(220, rect.height - 50, 200, 30);
//		contPan.add(input);

		String [] inqueryType = {"所有用户","经理","系统管理者","售票员","未注册用户"};
		inquery = new JComboBox(inqueryType);
		inquery.setBounds(60, rect.height - 50, 130, 30);
		contPan.add(inquery);

		// 查找 ，删除和编辑的按钮，其中含有相关的事件处理！
		btnQuery = new JButton("查找");
		btnQuery.setBounds(220, rect.height - 50, 60, 30);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQueryClicked();
			}
		});
		contPan.add(btnQuery);

		btnAdd = new JButton("添加");
		btnAdd.setBounds(rect.width - 220, rect.height - 50, 60, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnAddClicked();
			}
		});
		contPan.add(btnAdd);

		btnEdit = new JButton("修改");
		btnEdit.setBounds(rect.width - 150, rect.height - 50, 60, 30);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnModClicked();
			}
		});
		contPan.add(btnEdit);

		btnDel = new JButton("删除");
		btnDel.setBounds(rect.width - 80, rect.height - 50, 60, 30);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnDelClicked();
			}
		});
		contPan.add(btnDel);
		contPan.add(ca1);
		
		tms = new ManagerTable(jsc);
		
		showTable();
	}

	private void btnAddClicked() {
		
		ManagerAddUI addMUI = null;
		
		addMUI = new ManagerAddUI();
		addMUI.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addMUI.setWindowName("添加用户");
		addMUI.toFront();
		addMUI.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		addMUI.setVisible(true);
		if (addMUI.getReturnStatus()) {
			showTable();
		}
	}

	private void btnModClicked() {
		Employee emp = tms.getManager();
		if(null== emp){
			JOptionPane.showMessageDialog(null, "请选择要修改的用户");
			return; 
		} 
		UserEditUI userEditUI  = new UserEditUI(emp);
		userEditUI.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		userEditUI.setWindowName("修改剧目");
		userEditUI.initData(emp);
		userEditUI.toFront();
		userEditUI.setModal(true);
		userEditUI.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		userEditUI.setVisible(true);
		
		if (userEditUI.getReturnStatus()) {
			showTable();
		}	
	}

	private void btnDelClicked() {
		Employee emp = tms.getManager();
		if(null== emp){
			JOptionPane.showMessageDialog(null, "请选择要删除的用户");
			return; 
		} 		
		
		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			EmployeeSrv stuSrv = new EmployeeSrv();
			System.out.println(emp.getEmp_id());
			stuSrv.delete(emp.getEmp_id());
			showTable();
		}
	}

	private void btnQueryClicked() {
		showTable();
		
	}

	private void showTable() {
String status = inquery.getSelectedItem().toString();
		
		if(status.equals("经理")){
			List<Employee> empList = new EmployeeSrv().FetchAll();
			List<Employee> empList2 = new LinkedList<Employee>();
			for(Employee emp:empList){
				if(emp.getStatus()!=null&&emp.getStatus().compareTo("经理")==0){
					empList2.add(emp);
				}
			}
			tms.showManagerList(empList2);
		}else if(status.equals("系统管理者")){
			List<Employee> empList = new EmployeeSrv().FetchAll();
			List<Employee> empList2 = new LinkedList<Employee>();
			for(Employee emp:empList){
				if(emp.getStatus()!=null&&emp.getStatus().compareTo("系统管理者")==0){
					empList2.add(emp);
				}
			}
			tms.showManagerList(empList2);
			
		}else if(status.equals("售票员")){
			List<Employee> empList = new EmployeeSrv().FetchAll();
			List<Employee> empList2 = new LinkedList<Employee>();
			for(Employee emp:empList){
				if(emp.getStatus()!=null&&emp.getStatus().compareTo("售票员")==0){
					empList2.add(emp);
				}
			}
			tms.showManagerList(empList2);
		}else if(status.equals("未注册用户")){
			List<Employee> empList = new EmployeeSrv().FetchAll();
			List<Employee> empList2 = new LinkedList<Employee>();
			for(Employee emp:empList){
				if(emp.getStatus()==null){
					empList2.add(emp);
				}
			}
			tms.showManagerList(empList2);
		}else if(status.equals("所有用户")){
			List<Employee> empList = new EmployeeSrv().FetchAll();
			tms.showManagerList(empList);
		}
	}
	

	public static void main(String[] args) {
		ManagerManageUI frmStuMgr = new ManagerManageUI();
		frmStuMgr.setVisible(true);
	}
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
		new SystemMgUI().setVisible(true);
		this.dispose();
		
	}	
}


