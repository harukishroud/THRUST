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

}
