package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.InventoryBean;
import bean.MoveAllFromToBean;
import dao.ExceptionDAO;
import dao.InventoryDAO;

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
    
    // SERVICE 06 - addNewInventoryItem()
    //              Executa o DAO responsável por adicionar novo item ao banco.
    public void addNewInventoryItem(InventoryBean newInventoryItem) throws SQLException {
        InventoryDAO inventoryDAO = new InventoryDAO();
        
        try {
            inventoryDAO.addNewInventoryItem(newInventoryItem);
            System.out.println("[DATABASE][INVENTORYSERVICE] Item adicionado com succeso ao inventário!");
        } catch (SQLException ex) {
            System.out.println("[DATABASE][INVENTORYSERVICE] ERRO: Não foi possível adicionar"
                    + "o item ao inventário.");
        }
    }
    
    // SERVICE 07 - checkInventoryItemExistance()
    //              Executa o DAO que busca pela existência do PN informado no -
    //              banco de dados.
    public boolean checkInventoryItemExistance(InventoryBean item) throws SQLException {
        boolean checkStatus = false;
        InventoryDAO inventoryDAO = new InventoryDAO();

        try {
            checkStatus = inventoryDAO.checkInventoryItemExistance(item);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][INVENTORYSERVICE] ERRO: Não foi possível executar"
                    + "a checagem do item informado.");
        }

        return checkStatus;
    }
    
    // SERVICE 08 - moveAllFromTo()
    //              Executa DAO que move todos os items do container A pra o co-
    //              ntainer B.
    public void moveAllFromTo(MoveAllFromToBean moveInfo) throws SQLException {
        InventoryDAO inventoryDAO = new InventoryDAO();
        
        try {
            inventoryDAO.moveAllFromTo(moveInfo);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][INVENTORYSERVICE] ERRO: Não foi possível realizar o procedimento.");            
        }
    }
    
    // SERVICE 09 - moveItemTo()
    //              Executa DAO que move o item indicado para um Container sele-
    //              cionado.
    public void moveItemTo(InventoryBean item) throws SQLException {
        InventoryDAO inventoryDAO = new InventoryDAO();
        
        try {
            inventoryDAO.moveItemTo(item);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][INVENTORYSERVICE] ERRO: Não foi possível realizar a mudança do item '" + item.getPn() + "'.");
        }
    }
    
}
