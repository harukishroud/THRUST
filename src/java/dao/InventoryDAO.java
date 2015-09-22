package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.InventoryBean;
import db.sql.QueryBuilder;
import db.ConnectionBuilder;

public class InventoryDAO {

    // DAO 01 - loadBasicInventory()
    //          Carrega todos os itens da tabela inventário com informações
    //          básicas.
    public List<InventoryBean> loadInventory() throws ExceptionDAO, SQLException {
        List<InventoryBean> inventory = new ArrayList<InventoryBean>();
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][INVENTORYDAO] Iniciando...");

        // Carrega dados da VIEW 'inventory_standardview'
        String sql = "SELECT * FROM inventory_standardview";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("[DATABASE][INVENTORYDAO] Executando pesquisa...");

        while (rs.next()) {
            InventoryBean inventoryItem = new InventoryBean();
            inventoryItem.setId(rs.getInt("ID"));
            inventoryItem.setPn(rs.getString("PN"));
            inventoryItem.setDescription(rs.getString("DESCRIPTION"));
            inventoryItem.setCondition(rs.getString("ITEMCONDITION"));
            inventoryItem.setQuantity(rs.getInt("QUANTITY"));
            inventoryItem.setContainerAlias(rs.getString("ALIAS"));
            inventoryItem.setPrice(rs.getFloat("PRICE"));
            inventoryItem.setStatus(rs.getString("ITEM_STATUS"));
            inventoryItem.setFrom(rs.getString("ITEM_FROM"));
            inventoryItem.setForm_link(rs.getString("FORM_LINK"));
            inventoryItem.setForm_status(rs.getBoolean("FORM_STATUS"));
            inventoryItem.setObs(rs.getString("OBS"));
            inventoryItem.setContainerfull_status(rs.getBoolean("CONTAINER_STATUS"));            
            inventoryItem.setOldpn(rs.getString("PN"));
            inventoryItem.setOldalias(rs.getString("ALIAS"));
            
            inventory.add(inventoryItem);
        }

        System.out.println("[DATABASE][INVENTORYDAO] Pesquisa concluída com sucesso!");

        rs.close();
        ps.close();
        conn.close();

        return inventory;
    }

    // DAO 02 - updateInventoryItem()
    //          Atualiza item no inventário.
    public InventoryBean updateInventoryItem(InventoryBean updatedInventoryItem)
            throws SQLException {

        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][INVENTORYDAO] Iniciando...");
        
        String sqlitems = "UPDATE items SET DESCRIPTION=? WHERE PN=?";

        System.out.println("[DATABASE][INVENTORYDAO] Atualizando tabela 'items'...");
        PreparedStatement psitems = conn.prepareStatement(sqlitems);       
        psitems.setString(1, updatedInventoryItem.getDescription());
        psitems.setString(2, updatedInventoryItem.getOldpn());
        psitems.execute();
        psitems.close();

        String sqlinventory = "UPDATE inventory SET ITEMCONDITION=?, QUANTITY=? "
                + ", PRICE=?, ITEM_STATUS=?, ITEM_FROM=?, OBS=?, container_ALIAS=?, items_PN=?, FORM_STATUS=? WHERE ID=? AND items_PN=? AND container_ALIAS=?";

        System.out.println("[DATABASE][INVENTORYDAO] Atualizando tabela 'inventory'...");
        PreparedStatement psinventory = conn.prepareStatement(sqlinventory);
        psinventory.setString(1, updatedInventoryItem.getCondition());
        psinventory.setInt(2, updatedInventoryItem.getQuantity());
        psinventory.setFloat(3, updatedInventoryItem.getPrice());
        psinventory.setString(4, updatedInventoryItem.getStatus());
        psinventory.setString(5, updatedInventoryItem.getFrom());
        psinventory.setString(6, updatedInventoryItem.getObs());
        psinventory.setString(7, updatedInventoryItem.getContainerAlias());
        psinventory.setString(8, updatedInventoryItem.getPn());
        psinventory.setBoolean(9, updatedInventoryItem.isForm_status());
        psinventory.setInt(10, updatedInventoryItem.getId());
        psinventory.setString(11, updatedInventoryItem.getOldpn());
        psinventory.setString(12, updatedInventoryItem.getOldalias());
        psinventory.execute();
        psinventory.close();        

        conn.close();

        System.out.println("[DATABASE][INVENTORYDAO] Item '" + updatedInventoryItem.getPn()
                + "' em '" + updatedInventoryItem.getContainerAlias() + "' atualizado com sucesso!");

        return updatedInventoryItem;
    }

    // DAO 03 - createListInventoryContainers()
    //          Busca por containers existentes no inventários.
    public List<InventoryBean> createListInventoryContainers() throws SQLException, ExceptionDAO {

        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        List<InventoryBean> inventoryContainerList = new ArrayList<InventoryBean>();

        System.out.println("[DATABASE][INVENTORYDAO] Iniciando busca por containers existentes...");

        String sql = "SELECT DISTINCT ALIAS FROM container";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            InventoryBean inventoryContainer = new InventoryBean();
            inventoryContainer.setContainerAlias(rs.getString("ALIAS"));
            inventoryContainerList.add(inventoryContainer);
        }

        rs.close();
        ps.close();
        conn.close();

        return inventoryContainerList;
    }
    
    // DAO 04 - countFaultInventoryItens()
    //          Conta items em falta no inventário.
    public List<InventoryBean> countFaultInventoryItens() throws SQLException, ExceptionDAO {

        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        List<InventoryBean> inventoryFaultList = new ArrayList<InventoryBean>();

        System.out.println("[DATABASE][INVENTORYDAO] Iniciando busca por items em falta...");

        String sql = "SELECT PN FROM inventory_standardview WHERE QUANTITY = 0";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            InventoryBean inventoryFaultItem = new InventoryBean();
            inventoryFaultItem.setPn(rs.getString("PN"));
            inventoryFaultList.add(inventoryFaultItem);
        }

        rs.close();
        ps.close();
        conn.close();

        return inventoryFaultList;
    } 
    
    // DAO 05 - createListInventoryOwner()
    //          Busca por proprietários existentes no inventários.
    public List<InventoryBean> createListInventoryOwners() throws SQLException, ExceptionDAO {

        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        List<InventoryBean> inventoryOwnerList = new ArrayList<InventoryBean>();

        System.out.println("[DATABASE][INVENTORYDAO] Iniciando busca por proprietários distintos ...");

        String sql = "SELECT DISTINCT ITEM_FROM FROM inventory_standardview";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            InventoryBean inventoryOwner = new InventoryBean();
            inventoryOwner.setFrom(rs.getString("ITEM_FROM"));
            inventoryOwnerList.add(inventoryOwner);
        }

        rs.close();
        ps.close();
        conn.close();

        return inventoryOwnerList;
    }   

}
