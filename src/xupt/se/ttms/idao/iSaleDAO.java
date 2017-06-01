/**
 * 
 */
package xupt.se.ttms.idao;
import xupt.se.ttms.model.Sale;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Ticket;

import java.util.List;

/**
 * @author Administrator
 *
 */
public interface iSaleDAO {
	public boolean doSale(List<Ticket> tickets);
	public boolean refund(List<Ticket> tickets);
	public List<Sale> select(String condt);
}
