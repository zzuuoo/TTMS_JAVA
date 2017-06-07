package xupt.se.ttms.view.user;

import javax.swing.JOptionPane;

import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Play;
import xupt.se.ttms.service.EmployeeSrv;
import xupt.se.ttms.service.PlaySrv;

public class UserEditUI  extends ManagerAddUI{
	private Employee employee;
	
	
	UserEditUI(Employee e){
		employee = e;
		initData(e);
	}

	
	public void initData(Employee e) {
		if(null== e){
			return;
		}
//		empid,name,status,account,tel,email;

//		empid.setText(e.getEmp_id()+"");
		name.setText(e.getEmp_name());
//		status.setText(e.getStatus());
//		account.setText(e.getAccount());
		tel.setText(e.getEmp_tel_num());
		email.setText(e.getEmp_email());
		addr.setText(e.getEmp_addr());
		employee=e;
		this.invalidate();
	}

	@Override
	protected void btnSaveClicked(){
		if (name.getText() != null&&tel.getText()!=null
				&&addr.getText()!=null&&email.getText()!=null) {
			EmployeeSrv empSrv = new EmployeeSrv();
			Employee emp=new Employee();
			
			emp.setEmp_name(name.getText());
			emp.setEmp_tel_num(tel.getText());
			emp.setEmp_addr(addr.getText());
			emp.setEmp_email(email.getText());

			emp.setEmp_id(employee.getEmp_id());
			System.out.println(emp.getEmp_id());
			empSrv.modify(emp);
			this.setVisible(false);
			this.dispose();
			rst=true;
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	
	}
	

}
