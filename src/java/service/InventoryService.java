package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.InventoryBean;
import dao.ExceptionDAO;
import dao.InventoryDAO;
import db.sql.QueryBuilder;
import db.sql.QueryGender;
import db.sql.QueryOperation;
import db.sql.QueryType;

public class InventoryService {

    // SERVICE 01 - loadInventory()
    //              Executa o DAO para listar todos os itens existentes na tabe-
    //              la de inventário.
    public List<InventoryBean> loadInventory() throws SQLException, ExceptionDAO {
        List<InventoryBean> inventory = new ArrayList<InventoryBean>();
        InventoryDAO inventoryDAO = new InventoryDAO();

        try {
            inventory = inventoryDAO.loadInventory();
        } catch (SQLException ex) {
            System.out.println("[DATABASE][INVENTORYSERVICE] ERRO: Não foi possível carregar"
                    + "as informações de inventário.");
        }

        return inventory;
    }

    // SERVICE 02 - updateInventoryItem()
    //              Executa o DAO que atualiza as informações do item no invent-
    //              ário.
    public InventoryBean updateInventoryItem(InventoryBean updatedInventoryItem) {
        InventoryDAO inventoryDAO = new InventoryDAO();

        try {
            return inventoryDAO.updateInventoryItem(updatedInventoryItem);
        } catch (SQLException ex) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    // SERVICE 03 - createListInventoryContainers()
    //              Executa o DAO que cria uma lista de containers existentes no
    //              inventário.
    public List<InventoryBean> createListInventoryContainers() throws ExceptionDAO {
        
        InventoryDAO inventoryDAO = new InventoryDAO();
        List<InventoryBean> inventoryContainerList = new ArrayList<InventoryBean>();        
        
        try {
            inventoryContainerList = inventoryDAO.createListInventoryContainers();
        } catch (SQLException ex) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.SEVERE, null, ex);
        }               
        return inventoryContainerList;        
    }
    
    // SERVICE 04 - countInventoryFaultItens()
    //              Executa o DAO que calcula o número de items em falta no inv-
    //              entário.
    public List<InventoryBean> countInventoryFaultItens() throws ExceptionDAO {
        
        InventoryDAO inventoryDAO = new InventoryDAO();
        List<InventoryBean> inventoryFaultList = new ArrayList<InventoryBean>();        
        
        try {
            inventoryFaultList = inventoryDAO.countFaultInventoryItens();
        } catch (SQLException ex) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.SEVERE, null, ex);
        }               
        return inventoryFaultList;        
    }
        
    // SERVICE 05 - createListInventoryOwners()
    //              Executa o DAO que busca por proprietários distintos no inve-
    //              ntário.
    public List<InventoryBean> createListInventoryOwners() throws ExceptionDAO {
        
        InventoryDAO inventoryDAO = new InventoryDAO();
        List<InventoryBean> inventoryOwnerList = new ArrayList<InventoryBean>();        
        
        try {
            inventoryOwnerList = inventoryDAO.createListInventoryOwners();
        } catch (SQLException ex) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.SEVERE, null, ex);
        }               
        return inventoryOwnerList;        
    }   
    
    
}