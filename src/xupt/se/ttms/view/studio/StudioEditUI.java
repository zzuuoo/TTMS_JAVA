package xupt.se.ttms.view.studio;

import javax.swing.JDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import view.studioUI.ImageJPanel;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.studio.StudioAddUI;;

public class StudioEditUI extends StudioAddUI{

	public StudioEditUI(Studio stu) {
		this.setTitle("修改演出厅");
		initData(stu);
	}

	private void initData(Studio stu) {
		txtName.setText(stu.getName());
		txtRow.setText(Integer.toString(stu.getRowCount()));
		txtColumn.setText(Integer.toString(stu.getColCount()));
	}

	@Override
	protected void btnSaveClicked(){
		if (txtName.getText() != null && txtRow.getText() != null
				&& txtColumn.getText() != null) {
			StudioSrv stuSrv = new StudioSrv();
			Studio stu=new Studio();
			stu.setName(txtName.getText());
			stu.setRowCount(Integer.parseInt(txtRow.getText()));
			stu.setColCount(Integer.parseInt(txtColumn.getText()));
			stu.setIntroduction("test");

			stuSrv.modify(stu);
			this.setVisible(false);
			rst=true;
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}

}
