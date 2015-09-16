package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.InventoryBasicBean;
import db.sql.QueryBuilder;
import db.ConnectionBuilder;

public class InventoryDAO {

    public List<InventoryBasicBean> listBasicInventory() throws ExceptionDAO, SQLException {
        List<InventoryBasicBean> inventory = new ArrayList<InventoryBasicBean>();
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][INVENTORYDAO] Iniciando...");

        String sql = "SELECT * FROM inventory_basic";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        System.out.println("[DATABASE][INVENTORYDAO] Executando pesquisa...");

        while (rs.next()) {
            InventoryBasicBean inventoryItem = new InventoryBasicBean();
            inventoryItem.setPn(rs.getString("PN"));
            inventoryItem.setDescription(rs.getString("DESCRIPTION"));
            inventoryItem.setCondition(rs.getString("CONDITION"));
            inventoryItem.setQuantity(rs.getInt("QUANTITY"));
            inventoryItem.setContainerAlias(rs.getString("ALIAS"));
            
            // DEBUG
            System.out.println("[DATABASE][INVENTORYDAO] Encontrado: '" + inventoryItem.getPn() + 
                    "', '" + inventoryItem.getDescription() + "', '" + inventoryItem.getCondition() + 
                    "', '" + inventoryItem.getQuantity() + "', '" + inventoryItem.getContainerAlias() + "'.");
            // - - -

            inventory.add(inventoryItem);
        }

        System.out.println("[DATABASE][INVENTORYDAO] Pesquisa concluída com sucesso!");
        
        rs.close();
        ps.close();
        conn.close();
        
        return inventory;
    }

}
