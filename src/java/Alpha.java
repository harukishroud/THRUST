
import java.sql.SQLException;
import dao.InventoryDAO;
import service.InventoryService;
import bean.InventoryBasicBean;
import dao.ExceptionDAO;
import java.util.ArrayList;
import java.util.List;

public class Alpha {

    InventoryBasicBean inventoryItem = new InventoryBasicBean();
    List<InventoryBasicBean> inventory = new ArrayList<InventoryBasicBean>();

    public static void main(String[] args) throws SQLException, ExceptionDAO {

        InventoryService inventoryService = new InventoryService();
        
        inventoryService.listBasicInventory();

    }

}
