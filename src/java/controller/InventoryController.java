package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

import dao.InventoryDAO;
import bean.InventoryBasicBean;
import dao.ExceptionDAO;
import java.sql.SQLException;
import service.InventoryService;

@ManagedBean(name = "inventoryController")
@ViewScoped

public class InventoryController {
    
    private InventoryBasicBean inventoryBasicBean = new InventoryBasicBean();
    private List<InventoryBasicBean> inventory = new ArrayList<InventoryBasicBean>();
    
    public void loadAllInventory() throws SQLException, ExceptionDAO {
        InventoryService inventoryService = new InventoryService();
        inventory = inventoryService.listBasicInventory();
    }
    
    public InventoryController() throws SQLException, ExceptionDAO {
        loadAllInventory();
    }

    public InventoryBasicBean getInventoryBasicBean() {
        return inventoryBasicBean;
    }

    public void setInventoryBasicBean(InventoryBasicBean inventoryBasicBean) {
        this.inventoryBasicBean = inventoryBasicBean;
    }

    public List<InventoryBasicBean> getInventory() {
        return inventory;
    }

    public void setInventory(List<InventoryBasicBean> inventory) {
        this.inventory = inventory;
    }
    
}
