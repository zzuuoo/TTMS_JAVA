package xupt.se.ttms.view.datadict;

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
import xupt.se.ttms.model.DataDict;
import xupt.se.ttms.service.DataDictSrv;
import xupt.se.ttms.view.studio.ImageJPanel;
import xupt.se.ttms.view.tmpl.*;
import xupt.se.ttms.view.tmpl.ImagePanel;

public class DataDictAddUI extends PopUITmpl implements ActionListener {

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel lblIndex, lblName, lblValue;
	protected JTextField txtIndex, txtName, txtValue;

	public DataDictAddUI() {
	}

	@Override
	protected void initContent(){
		this.setTitle("添加数据字典");

		lblIndex = new JLabel("排列顺序：");
		lblIndex.setBounds(60, 30, 80, 30);
		contPan.add(lblIndex);
		txtIndex = new JTextField();
		txtIndex.setBounds(150, 30, 120, 30);
		contPan.add(txtIndex);

		lblName = new JLabel("字典名称：");
		lblName.setBounds(60, 80, 50, 30);
		contPan.add(lblName);
		txtName = new JTextField();
		txtName.setBounds(150, 80, 120, 30);
		contPan.add(txtName);

		lblValue = new JLabel("字典值：");
		lblValue.setBounds(60, 130, 90, 30);
		contPan.add(lblValue);
		txtValue = new JTextField();
		txtValue.setBounds(150, 130, 120, 30);
		contPan.add(txtValue);

		btnSave = new JButton("保存");

		btnSave.addActionListener(this);
		btnSave.setBounds(60, 220, 60, 30);
		contPan.add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(180, 220, 60, 30);
		contPan.add(btnCancel);

		ImageJPanel imageJP = new ImageJPanel(new ImageIcon(
				"files/imgs/pencil.jpg").getImage());
		imageJP.setBounds(360, 160, 100, 100);
		imageJP.setLayout(null);
		this.add(imageJP);
	}
	
	
	public boolean getReturnStatus(){
		   return rst;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			rst=false;
			this.dispose();
			getParent().setVisible(true);

		} else if (e.getSource() == btnSave) {
			btnSaveClicked();
		}

	}
	
	protected void btnSaveClicked(){
		if (txtIndex.getText() != null && txtName.getText() != null
				&& txtValue.getText() != null) {
			DataDictSrv dictSrv = new DataDictSrv();
			DataDict ddict=new DataDict();
			ddict.setSuperId(0);
			ddict.setIndex(Integer.parseInt(txtIndex.getText()));
			ddict.setName(txtName.getText());
			ddict.setValue(txtValue.getText());

			dictSrv.add(ddict);
			this.setVisible(false);
			rst=true;
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
}
