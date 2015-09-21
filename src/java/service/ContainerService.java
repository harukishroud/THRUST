package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.ContainerBean;
import dao.ExceptionDAO;
import dao.ContainerDAO;

public class ContainerService {

    // SERVICE 01 - loadContainerInfo()
    //              Executga o DAO que recupera as informações do container sel-
    //              ecionado.
    public ContainerBean loadContainerInfo(String containerAlias) throws SQLException {
        ContainerBean containerInfo = new ContainerBean();
        ContainerDAO containerDAO = new ContainerDAO();

        try {
            containerInfo = containerDAO.loadContainerInfo(containerAlias);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][CONTAINERSERVICE] ERRO: Não foi possível carregar"
                    + "as informações do container.");
        }
        
        return containerInfo;
    }
    
    // SERVICE 02 - countContainerTotalItens()
    //              Executa o DAO que calcula o total de itens no container sel-
    //              ecionado.
    public int countContainerTotalItens(String containerAlias) throws ExceptionDAO {
        ContainerDAO containerDAO = new ContainerDAO();
        int containerTotal = 0;
        try {
            containerTotal = containerDAO.countContainerTotalItens(containerAlias);
        } catch (SQLException ex) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.SEVERE, null, ex);
        }               
        return containerTotal;        
    }
    
    // SERVICE 03 - createListAvailableContainers()
    //              Executa o DAO que cria uma lista de containers existentes no
    //              inventário.
    public List<ContainerBean> createListAvailableContainers() throws ExceptionDAO {
        
        ContainerDAO containerDAO = new ContainerDAO();
        List<ContainerBean> inventoryAvailableContainerList = new ArrayList<ContainerBean>();        
        
        try {
            inventoryAvailableContainerList = containerDAO.createListAvailableContainers();
        } catch (SQLException ex) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.SEVERE, null, ex);
        }               
        return inventoryAvailableContainerList;        
    }

}
