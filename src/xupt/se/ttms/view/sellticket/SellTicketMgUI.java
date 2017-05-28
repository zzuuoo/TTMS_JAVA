package xupt.se.ttms.view.sellticket;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.login.Manager;
import xupt.se.ttms.view.login.Seller;
import xupt.se.ttms.view.login.SystemMgUI;
import xupt.se.ttms.view.tmpl.*;

class ScheduleTable {
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	private JTable jt;

	public ScheduleTable(JScrollPane jp) {
		
		DefaultTableModel tabModel=new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override              
			public boolean isCellEditable(int row,int column){
				return false;              
			};
		};
//		Object[] in = { "ID", "演出厅ID", "剧目ID", "演出时间", "票单价" };
		tabModel.addColumn("演出计划ID");
		tabModel.addColumn("演出厅");
		tabModel.addColumn("剧目");
		tabModel.addColumn("演出时间");
		tabModel.addColumn("票价");
		//初始化列明
		jt=new JTable(tabModel);	
		
		jt=new JTable(tabModel);
		jt.setSelectionBackground(Color.green);
		JTableHeader th = jt.getTableHeader();
		th.setFont(new Font("宋体",3,25));
		th.setPreferredSize(new Dimension(jt.getWidth(), 40));
		jt.setRowHeight(30);
		jt.setFont(new Font("宋体",1,20));
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	       render.setHorizontalAlignment(SwingConstants.CENTER);
	       
	       jt.getColumn("演出计划ID").setCellRenderer(render);
	       jt.getColumn("演出厅").setCellRenderer(render);
	       jt.getColumn("剧目").setCellRenderer(render);
	       jt.getColumn("演出时间").setCellRenderer(render);
	       jt.getColumn("票价").setCellRenderer(render);
		
		//设置各列的宽度
	    TableColumnModel columnModel = jt.getColumnModel();
	    
	    //隐藏ID这一列
        TableColumn column = columnModel.getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setWidth(0);
        column.setPreferredWidth(10);

        column = columnModel.getColumn(1);
        column.setPreferredWidth(10);
        column = columnModel.getColumn(2);
        column.setPreferredWidth(10);
        column = columnModel.getColumn(3);
        column.setPreferredWidth(200);
        column = columnModel.getColumn(4);
        column.setPreferredWidth(20);        

		
		jp.add(jt);
		jp.setViewportView(jt);
		
	}
	

	public Schedule getSchedule() {
		int rowSel=jt.getSelectedRow();
		if(rowSel>=0){
//			Object[] in = { "ID", "演出厅", "剧目", "演出时间", "票单价" };
			Schedule stud = new Schedule();
			stud.setSched_id(Integer.parseInt(jt.getValueAt(rowSel, 0)+""));
			stud.setPlay_id((new PlaySrv().FetchOneById("play_name = '"+jt.getValueAt(rowSel, 2)+"'")).getId());
			stud.setStudio_id((new StudioSrv().FetchOneById("studio_name = '"+jt.getValueAt(rowSel, 1)+"'")).getID());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			try {
				stud.setSched_time(sdf.parse(jt.getValueAt(rowSel,3)+""));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("???");
			}

			stud.setSched_ticket_price(Double.parseDouble(jt.getValueAt(rowSel, 4)+""));

			return stud;
		}
		else{
			return null;
		}
			
	}
	
	// 创建JTable
	public void showScheduleList(List<Schedule> stuList) {
		try {
			DefaultTableModel tabModel = (DefaultTableModel) jt.getModel();
			tabModel.setRowCount(0);
			
			Iterator<Schedule> itr = stuList.iterator();
			while (itr.hasNext()) {
				Schedule stu = itr.next();
				Object data[] = new Object[5];
				data[0] = Integer.toString(stu.getSched_id());
				data[1] = (new StudioSrv().FetchOneById("studio_id = "+stu.getStudio_id())).getName();
				data[2] = (new PlaySrv().FetchOneById("play_id = "+stu.getPlay_id())).getName();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
				data[3] = sdf.format(stu.getSched_time());
				data[4] = stu.getSched_ticket_price();
				tabModel.addRow(data);;
			}
			jt.invalidate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class SellTicketMgUI extends MainUITmpl {
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
	private JButton btnAdd,btnQuery;
	
	ScheduleTable tms; //显示演出厅列表


	public SellTicketMgUI() {
		
	}

	// To be override by the detailed business block interface
	@Override
	protected void initContent() {
		Rectangle rect = contPan.getBounds();

		ca1 = new JLabel("售票管理", JLabel.CENTER);
		ca1.setBounds(0, 5, rect.width, 30);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		contPan.add(ca1);

		jsc = new JScrollPane();
		jsc.setBounds(0, 40, rect.width, rect.height - 100);
		contPan.add(jsc);

		hint = new JLabel("请输入演出计划名称:", JLabel.RIGHT);
		hint.setFont(new Font("",1,15));
		hint.setBounds(60, rect.height - 50, 150, 30);
		contPan.add(hint);

		input = new JTextField();
		input.setBounds(220, rect.height - 50, 200, 30);
		contPan.add(input);

		// 查找 ，删除和编辑的按钮，其中含有相关的事件处理！
		btnQuery = new JButton("查找");
		btnQuery.setBounds(440, rect.height - 50, 60, 30);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQueryClicked();
			}
		});
		contPan.add(btnQuery);

		btnAdd = new JButton("选择座位");
		btnAdd.setBounds(rect.width - 220, rect.height - 50, 150, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnSelectSeat();
			}
		});
		contPan.add(btnAdd);

		tms = new ScheduleTable(jsc);
		
		showTable();
	}

	private void btnSelectSeat() {

		Schedule stud = tms.getSchedule();
		if(null== stud){
			JOptionPane.showMessageDialog(null, "请选择要修改的演出计划");
			return; 
		}
		SelectTicketUI stUI = new SelectTicketUI(stud);
		stUI.setVisible(true);
		this.dispose();
	}

	
	private void btnQueryClicked() {
		if (!input.getText().equals("")) {
			//请自行补充

		} else {
			JOptionPane.showMessageDialog(null, "请输入检索条件");
		}
	}

	private void showTable() {
		List<Schedule> stuList = new ScheduleSrv().FetchAll();
		tms.showScheduleList(stuList);
	}
	

	public static void main(String[] args) {
		SellTicketMgUI frmStuMgr = new SellTicketMgUI();
		frmStuMgr.setVisible(true);
	}
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
		new Seller().setVisible(true);
		this.dispose();
		
	}	
}