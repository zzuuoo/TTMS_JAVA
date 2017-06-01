package xupt.se.ttms.idao;
import xupt.se.ttms.dao.*;

public class DAOFactory {
	public static iLogindeDAO creatLogindeDAO(){
		return new LogindeDAO();
	}
	
	public static iemployeeDAO creatEmployeeDAO(){
		return new employeeDAO();
	}

	
	public static IDataDictDAO creatDataDictDAO(){
		return new DataDictDAO();
	}
	
	public static iStudioDAO creatStudioDAO(){
		return new StudioDAO();
	}

	public static iScheduleDAO creatScheduleDAO(){
		return new ScheduleDAO();
	}
	
	public static iPlayDAO creatPlayDAO(){
		return new PlayDAO();
	}

	public static iSeatDAO creatSeatDAO(){
		return new SeatDAO();
	}

	public static iTicketDAO creatTicketDAO(){
		return new TicketDAO();
	}

	public static iSaleDAO creatSaleDAO(){
		return new SaleDAO();
	}
	public static iSaleItemDAO creatSaleItemDAO(){
		return new SaleItemDAO();
	}
}
