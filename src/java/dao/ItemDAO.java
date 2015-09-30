package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.ItemBean;
import db.sql.QueryBuilder;
import db.ConnectionBuilder;

public class ItemDAO {

    // DAO 01 - loadItemInfo()
    //          Carrega detalhes do PN selecionado.
    public ItemBean loadItemInfo(String pn) throws SQLException {
        ItemBean itemInfo = new ItemBean();
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][ITEMDAO] Iniciando...");

        String sql = "SELECT * FROM items WHERE PN = '" + pn + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("[DATABASE][INVENTORYDAO] Carregando informações do PN...");

        while (rs.next()) {
            itemInfo.setPn(rs.getString("PN"));
            itemInfo.setDescription(rs.getString("DESCRIPTION"));
            itemInfo.setSize_d(rs.getFloat("SIZE_D"));
            itemInfo.setSize_w(rs.getFloat("SIZE_W"));
            itemInfo.setSize_h(rs.getFloat("SIZE_H"));
            itemInfo.setWeight(rs.getFloat("WEIGHT"));
        }

        System.out.println("[DATABASE][ITEMDAO] As informações foram carregadas com sucesso!");

        rs.close();
        ps.close();
        conn.close();

        return itemInfo;
    }

    // DAO 02 - checkItemExistance()
    //          verifica existência do PN informado no banco de dados.
    public boolean checkItemExistance(String pn) throws SQLException {
        boolean checkStatus = false;
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][ITEMDAO] Buscando por PN '" + pn + "' no banco de dados...");

        String sql = "SELECT * FROM items WHERE PN = '" + pn + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("[DATABASE][ITEMDAO] O PN informado já existe.");
            checkStatus = true;
        } else {
            System.out.println("[DATABASE][ITEMDAO] O PN '" + pn + "' não existe no banco de dados.");
        }

        return checkStatus;
    }

    // DAO 03 - addNewItem()
    //          Adiciona novo PN ao banco de dados.
    public void addNewItem(ItemBean newItem) throws SQLException {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][ITEMDAO] Preparando para inserir novo PN no banco de dados...");
        System.out.println("[DATABASE][ITEMDAO] Adicionando PN '" + newItem.getPn() + "', DESC. '" + newItem.getDescription() + "'...");

        String sql = "INSERT INTO items(PN, DESCRIPTION, SIZE_H, SIZE_W, SIZE_D, WEIGHT) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, newItem.getPn());
        ps.setString(2, newItem.getDescription());
        ps.setFloat(3, newItem.getSize_h());
        ps.setFloat(4, newItem.getSize_w());
        ps.setFloat(5, newItem.getSize_d());
        ps.setFloat(6, newItem.getWeight());
        ps.execute();
        ps.close();
        conn.close();

        System.out.println("[DATABASE][ITEMDAO] PN '" + newItem.getPn() + "', de DESC. '" + newItem.getDescription() + "' adicionado com sucesso!");

    }

    // DAO 04 - loadItems()
    //          Carrega todos os PNs cadastrados no banco de dados.
    public List<ItemBean> loadItems() throws ExceptionDAO, SQLException {
        List<ItemBean> PNs = new ArrayList<ItemBean>();
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        // Carrega dados da VIEW 'pn_standardview'
        String sql = "SELECT * FROM pn_standardview";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ItemBean pn = new ItemBean();
            pn.setPn(rs.getString("PN"));
            pn.setDescription(rs.getString("DESCRIPTION"));
            pn.setSize_d(rs.getFloat("SIZE_D"));
            pn.setSize_h(rs.getFloat("SIZE_H"));
            pn.setSize_w(rs.getFloat("SIZE_W"));
            pn.setWeight(rs.getFloat("WEIGHT"));

            PNs.add(pn);
        }

        rs.close();
        ps.close();
        conn.close();
        
        return PNs;
    }

}
