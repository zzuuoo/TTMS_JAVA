package xupt.se.ttms.view.datasale;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
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
import xupt.se.ttms.model.GlobalVariable;
import xupt.se.ttms.model.Sale;
import xupt.se.ttms.model.SaleItem;
import xupt.se.ttms.service.SaleItemSrv;
import xupt.se.ttms.view.tmpl.*;

class AllItemSaleTable {
	/**
	 * 表格绘制
	 */
	private static final long serialVersionUID = 1L;
	private JTable jt;

	public AllItemSaleTable(JScrollPane jp) {
		
		DefaultTableModel tabModel=new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override              
			public boolean isCellEditable(int row,int column){
				return false;              
			};
		};
//		private int id;
//		private int ticketId;
//		private int saleId;
//		private float price;
		tabModel.addColumn("ID");
		tabModel.addColumn("ticketId");
		tabModel.addColumn("saleId");
		tabModel.addColumn("Price");
		
		
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
	       
	       jt.getColumn("ID").setCellRenderer(render);
	       jt.getColumn("ticketId").setCellRenderer(render);
	       jt.getColumn("saleId").setCellRenderer(render);
	       jt.getColumn("Price").setCellRenderer(render);
		
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
//        column = columnModel.getColumn(4);
//        column.setPreferredWidth(10);    
//        column = columnModel.getColumn(5);
//        column.setPreferredWidth(10);
//        column = columnModel.getColumn(6);
//        column.setPreferredWidth(10);
//        column = columnModel.getColumn(7);
//        column.setPreferredWidth(300);       

		
		jp.add(jt);
		jp.setViewportView(jt);
		
	}

//	private int id;
//	private int ticketId;
//	private int saleId;
//	private float price;
	public SaleItem getSaleItem() {
		int rowSel=jt.getSelectedRow();
//		System.out.println(rowSel);
//		SimpleDateFormat sdf = new SimpleDateFormat("YY-MM-dd HH:mm");
		if(rowSel>=0){
			SaleItem stud = new SaleItem();
			stud.setId(Integer.parseInt(jt.getValueAt(rowSel, 0).toString()));
			stud.setTicketId(Integer.parseInt(jt.getValueAt(rowSel,1).toString()));
			stud.setSaleId(Integer.parseInt(jt.getValueAt(rowSel,2).toString()));
			stud.setPrice(Float.parseFloat(jt.getValueAt(rowSel, 3).toString()));
//			stud.setEmpId(Integer.parseInt(jt.getValueAt(rowSel,1).toString()));
//			try {
//				stud.setTime(sdf.parse(jt.getValueAt(rowSel, 2).toString()));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				stud.setTime(null);
//			}
//			stud.setPayment(Float.parseFloat(jt.getValueAt(rowSel, 3).toString()));
//			stud.setChange(Float.parseFloat(jt.getValueAt(rowSel, 4).toString()));
//			stud.setType(Integer.parseInt(jt.getValueAt(rowSel, 5).toString()));
//			stud.setStatus(Integer.parseInt(jt.getValueAt(rowSel, 6).toString()));
		
			return stud;
		}
		else{
			return null;
		}
			
	}
	
	// 创建JTable
	public void showSaleItemList(List<SaleItem> stuList) {
//		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
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
//			private int status;  // 0：待付款   1：已付款
			Iterator<SaleItem> itr = stuList.iterator();
			while (itr.hasNext()) {
				SaleItem stu = itr.next();
				Object data[] = new Object[4];
				data[0] = stu.getId();
				data[1] = stu.getTicketId();
				data[2] =stu.getSaleId();
				data[3] = stu.getPrice();
//				data[0] = Integer.toString(stu.getId());
//				data[1] = stu.getEmpId();
//				data[2] = sdf.format(stu.getTime());
//				data[3] = stu.getPayment();
//				data[4] = stu.getChange();
////				data[5] = stu.getType();
//				if(stu.getType()==1){
//					data[5]="销售单";
//				}else{
//					data[5]="退款单";
//				}
////				data[6] = stu.getStatus();
//				if(stu.getStatus()==0){
//					data[6]="待付款";
//				}else{
//					data[6]="已付款";
//				}
				tabModel.addRow(data);;
			}
			jt.invalidate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class ItemOfSale extends MainUITmpl {
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
	private Sale sale = null;
	
	ItemSaleTable tms; //显示演出厅列表


	public ItemOfSale() {
		
	}
	
	public ItemOfSale(Sale sale) {
		this.sale=sale;
		showTable();
		System.out.println(sale.getId());
	}

	// To be override by the detailed business block interface
	@Override
	protected void initContent() {
		Rectangle rect = contPan.getBounds();

		ca1 = new JLabel("详细销售数据", JLabel.CENTER);
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

//
//		btnEdit = new JButton("查看详细账单");
//		btnEdit.setBounds(rect.width - 200, rect.height - 50, 120, 30);
//		btnEdit.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnModClicked();
//			}
//		});
//		contPan.add(btnEdit);

		
		tms = new ItemSaleTable(jsc);
		
		showTable();
	}

	

//	private void btnModClicked() {
//		
//		SaleItem stud = tms.getSaleItem();
//		if(null== stud){
//			JOptionPane.showMessageDialog(null, "请选择要查看的账单");
//			return; 
//		}	
//		
//		
//
//	}


	private void btnQueryClicked() {
		if (!input.getText().equals("")) {
//			//请自行补充
		} else {
			JOptionPane.showMessageDialog(null, "请输入检索条件");
			showTable();
		}
	}

	private void showTable() {
		if(sale!=null){
			List<SaleItem> stuList = new SaleItemSrv().Fetch(" sale_ID = "+sale.getId());
		tms.showSaleItemList(stuList);
		}
	}
	

	public static void main(String[] args) {
		ItemSale frmStuMgr = new ItemSale();
		frmStuMgr.setVisible(true);
	}
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
		new AllDataSale().setVisible(true);
//		System.out.println(GlobalVariable.emp_id);
		this.dispose();
		
	}	
}
