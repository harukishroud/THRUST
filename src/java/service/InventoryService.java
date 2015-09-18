package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.InventoryBasicBean;
import dao.ExceptionDAO;
import dao.InventoryDAO;
import db.sql.QueryBuilder;
import db.sql.QueryGender;
import db.sql.QueryOperation;
import db.sql.QueryType;

public class InventoryService {

    public List<InventoryBasicBean> listBasicInventory() throws SQLException, ExceptionDAO {
        List<InventoryBasicBean> inventory = new ArrayList<InventoryBasicBean>();
        InventoryDAO inventoryDAO = new InventoryDAO();

        try {
            inventory = inventoryDAO.listBasicInventory();
        } catch (SQLException ex) {
            System.out.println("[DATABASE][INVENTORYSERVICE] ERROR!");
        }

        return inventory;
    }

}
