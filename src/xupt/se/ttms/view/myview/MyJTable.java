package xupt.se.ttms.view.myview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class MyJTable extends JTable {  
//    boolean[] columnEditables = { false, false, false, false };  
//    public MyJTable(boolean[] columnEditables) {  
//        this.columnEditables = columnEditables;  
//    }  
    public MyJTable() {  
        super();  
    }  
    @Override  
    public JTableHeader getTableHeader() {  
        // TODO Auto-generated method stub  
        JTableHeader tableHeader = super.getTableHeader();  
        // 表格列不可移被移动  
        tableHeader.setReorderingAllowed(false);  
        // 设置表头的背景颜色  
        tableHeader.setBackground(new Color(224, 255, 255));  
        // 设置表头的高度  
        tableHeader.setPreferredSize(new Dimension(this.getWidth(), 30));  
        // 设置表头的字体  
        tableHeader.setFont(new Font("楷体", Font.BOLD, 18));  
        DefaultTableCellRenderer hr = (DefaultTableCellRenderer) tableHeader  
                .getDefaultRenderer();  
        // 列名居中  
        hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);  
        return tableHeader;  
    }  
//    @Override  
//    public boolean isCellEditable(int row, int column) {  
//        // TODO Auto-generated method stub  
//        return columnEditables[column];  
//    }  
  
}  