package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.InventoryBean;
import bean.MoveAllFromToBean;
import db.ConnectionBuilder;

public class InventoryDAO {

    // DAO 01 - loadInventory()
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

    // DAO 06 - addNewInventoryItem()
    //          Adiciona novo item ao inventário.
    public void addNewInventoryItem(InventoryBean newInventoryItem) throws SQLException {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][INVENTORYDAO] Preparando para inserir novo item ao inventário...");

        String sql = "INSERT INTO inventory(items_PN,container_ALIAS,QUANTITY,ITEMCONDITION,PRICE,ITEM_STATUS,ITEM_FROM,OBS,FORM_STATUS) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, newInventoryItem.getPn());
        ps.setString(2, newInventoryItem.getContainerAlias());
        ps.setInt(3, newInventoryItem.getQuantity());
        ps.setString(4, newInventoryItem.getCondition());
        ps.setFloat(5, newInventoryItem.getPrice());
        ps.setString(6, newInventoryItem.getStatus());
        ps.setString(7, newInventoryItem.getFrom());
        ps.setString(8, newInventoryItem.getObs());
        ps.setBoolean(9, newInventoryItem.isForm_status());
        ps.execute();
        ps.close();
        conn.close();

        System.out.println("[DATABASE][INVENTORYDAO] item adicionado com sucesso!");
    }

    // DAO 07 - checkInventoryItemExistance()
    //          verifica existência do PN informado no banco de dados.
    public boolean checkInventoryItemExistance(InventoryBean item) throws SQLException {
        boolean checkStatus = false;
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][INVENTORYDAO] Buscando por item de PN '" + item.getPn() + "' de condição '" + item.getCondition() + "' no inventário...");

        String sql = "SELECT * FROM inventory WHERE items_PN = '" + item.getPn() + "' and ITEMCONDITION = '" + item.getCondition() + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("[DATABASE][INVENTORYDAO] O PN informado já existe no inventário com a condição '" + item.getCondition() + "'.");
            checkStatus = true;
        }

        System.out.println("[DATABASE][INVENTORYDAO] O PN '" + item.getPn() + "' não existe no inventário com a condição '" + item.getCondition() + "'.");

        return checkStatus;
    }

    // DAO 08 - moveAllFromTo()
    //          Move todos os itens de um Container A para um Container B.
    public void moveAllFromTo(MoveAllFromToBean moveInfo) throws SQLException {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][INVENTORYDAO] Preparando para mover items de '" + moveInfo.getContainerA()
                + "' para '" + moveInfo.getContainerB() + "' ...");

        String sql = "UPDATE inventory SET container_ALIAS=? WHERE container_ALIAS=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, moveInfo.getContainerB());
        ps.setString(2, moveInfo.getContainerA());

        System.out.println(ps);
        ps.execute();
        ps.close();

        conn.close();

        System.out.println("[DATABASE][INVENTORYDAO] Items movidos de '" + moveInfo.getContainerA() + "' para '" + moveInfo.getContainerB() + "' com sucesso!");

    }

    // DAO 09 - moveItemTo()
    //          Move item(s) para um Container selecionado.
    public void moveItemTo(InventoryBean item) throws SQLException {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][INVENTORYDAO] Preparando para mover item '" + item.getPn()
                + "' para '" + item.getMovealias() + "' ...");

        if (item.getCondition() == null) {
            String sql = "UPDATE inventory SET container_ALIAS = ? WHERE items_PN = ? AND ITEMCONDITION IS NULL";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, item.getMovealias());
            ps.setString(2, item.getPn());

            System.out.println(ps);
            ps.execute();
            ps.close();

            conn.close();

            System.out.println("[DATABASE][INVENTORYDAO] Item '" + item.getPn()
                    + "' movido para '" + item.getMovealias() + "' com sucesso!");
        } else {
            String sql = "UPDATE inventory SET container_ALIAS = ? WHERE items_PN = ? AND ITEMCONDITION = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, item.getMovealias());
            ps.setString(2, item.getPn());
            ps.setString(3, item.getCondition());

            System.out.println(ps);
            ps.execute();
            ps.close();

            conn.close();

            System.out.println("[DATABASE][INVENTORYDAO] Item '" + item.getPn()
                    + "' movido para '" + item.getMovealias() + "' com sucesso!");
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    // 10 - loadAvailableInventory()
    //      Carrega todos os itens disponíveis da tabela inventário.
    public List<InventoryBean> loadAvailableInventory() throws ExceptionDAO, SQLException {
        /* Cria lista que armazena items disponíveis no inventário */
        List<InventoryBean> availableInventory = new ArrayList<InventoryBean>();

        /* Inicia e configura conexão com banco de dados */
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DAO][INVENTORY][loadAvailableInventory] Iniciando...");

        /* Prepara SQL e carrega dados da VIEW 'inventory_standardview' */
        String sql = "SELECT * FROM inventory_standardview WHERE QUANTITY > 0";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("[DAO][INVENTORY][loadAvailableInventory] Executando pesquisa...");

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

            availableInventory.add(inventoryItem);
        }

        System.out.println("[DAO][INVENTORY][loadAvailableInventory] Pesquisa concluída com sucesso!");

        rs.close();
        ps.close();
        conn.close();

        return availableInventory;
    }
}
