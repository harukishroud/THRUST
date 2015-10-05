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

    // DAO 01 - loadPNDetails()
    //          Carrega detalhes do PN selecionado.
    public ItemBean loadPNDetails(String PN) throws SQLException {
        ItemBean pnDetails = new ItemBean();
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][ITEMDAO] Iniciando...");

        String sql = "SELECT * FROM items WHERE PN = '" + PN + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("[DATABASE][INVENTORYDAO] Carregando detalhes do PN '" + PN + "' ...");

        while (rs.next()) {
            pnDetails.setPn(rs.getString("PN"));
            pnDetails.setDescription(rs.getString("DESCRIPTION"));
            pnDetails.setSize_d(rs.getFloat("SIZE_D"));
            pnDetails.setSize_w(rs.getFloat("SIZE_W"));
            pnDetails.setSize_h(rs.getFloat("SIZE_H"));
            pnDetails.setWeight(rs.getFloat("WEIGHT"));
        }

        System.out.println("[DATABASE][ITEMDAO] Os detalhes do PN '" + PN + "' foram carregadas com sucesso!");

        rs.close();
        ps.close();
        conn.close();

        return pnDetails;
    }

    // DAO 02 - checkPNExistance()
    //          verifica existência do PN informado no banco de dados.
    public boolean checkPNExistance(String PN) throws SQLException {
        boolean checkStatus = false;
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][ITEMDAO] Buscando por PN '" + PN + "' no banco de dados...");

        String sql = "SELECT * FROM items WHERE PN = '" + PN + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Caso o PN informado exista
        if (rs.next()) {
            System.out.println("[DATABASE][ITEMDAO] O PN informado já existe.");
            checkStatus = true;
        // Se o PN informado não existe o valor de 'checkStatus' permanece como falso
        } else {
            System.out.println("[DATABASE][ITEMDAO] O PN '" + PN + "' não existe no banco de dados.");
        }

        return checkStatus;
    }

    // DAO 03 - addPN()
    //          Adiciona novo PN ao banco de dados.
    public void addPN(ItemBean newPN) throws SQLException {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][ITEMDAO] Preparando para inserir novo PN no banco de dados...");
        System.out.println("[DATABASE][ITEMDAO] Adicionando PN '" + newPN.getPn() + "' de DESC. '" + newPN.getDescription() + "'...");

        String sql = "INSERT INTO items(PN, DESCRIPTION, SIZE_H, SIZE_W, SIZE_D, WEIGHT) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, newPN.getPn());
        ps.setString(2, newPN.getDescription());
        ps.setFloat(3, newPN.getSize_h());
        ps.setFloat(4, newPN.getSize_w());
        ps.setFloat(5, newPN.getSize_d());
        ps.setFloat(6, newPN.getWeight());
        ps.execute();
        ps.close();
        conn.close();

        System.out.println("[DATABASE][ITEMDAO] PN '" + newPN.getPn() + "' de DESC. '" 
                + newPN.getDescription() + "' adicionado com sucesso ao banco de dados!");
    }

    // DAO 04 - loadPNs()
    //          Carrega todos os PNs cadastrados no banco de dados.
    public List<ItemBean> loadPNs() throws ExceptionDAO, SQLException {
        List<ItemBean> PNs = new ArrayList<ItemBean>();
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][ITEMDAO] Carregando todos os PNs do banco de dados...");
        
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
        
        System.out.println("[DATABASE][ITEMDAO] Lista de PNs criada com sucesso...");
        
        rs.close();
        ps.close();
        conn.close();

        return PNs;
    }

    // DAO 05 - updatePN()
    //          Atualiza dados do PN editado.
    public ItemBean updatePN(ItemBean updatedPN) throws SQLException {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][ITEMDAO] Iniciando...");

        String sql = "UPDATE items SET DESCRIPTION=?, SIZE_H=?, SIZE_W=?,"
                + "SIZE_D=?, WEIGHT=? WHERE PN=?";

        System.out.println("[DATABASE][ITEMDAO] Atualizando PN '" + updatedPN.getPn() + "' ...");
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, updatedPN.getDescription());
        ps.setFloat(2, updatedPN.getSize_h());
        ps.setFloat(3, updatedPN.getSize_w());
        ps.setFloat(4, updatedPN.getSize_d());
        ps.setFloat(5, updatedPN.getWeight());
        ps.setString(6, updatedPN.getPn());
        ps.execute();
        ps.close();

        conn.close();

        System.out.println("[DATABASE][ITEMDAO] PN '" + updatedPN.getPn() + "' atualizado com sucesso!");
        
        return updatedPN;
    }

}
