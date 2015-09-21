package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.ConnectionBuilder;
import bean.AlternateBean;
import java.util.ArrayList;
import java.util.List;

public class AlternateDAO {

    // DAO 01 - loadAlternateItems()
    //          Carrega lista de alternados disponíveis para o PN selecionado.
    public List<AlternateBean> loadAlternateItems(String pn) throws SQLException {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        List<AlternateBean> alternateItems = new ArrayList<AlternateBean>();

        System.out.println("[DATABASE][ALTERNATE DAO] Iniciando busca por PNs alternados...");
        
        String sql = "SELECT * FROM pn_alternatelist WHERE items_PN = '" + pn + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            AlternateBean alternateItem = new AlternateBean();
            alternateItem.setAlt_pn(rs.getString("ALT_PN"));
            alternateItem.setItems_pn(rs.getString("items_PN"));
            alternateItems.add(alternateItem);
        }
        
        rs.close();
        ps.close();
        conn.close();
        
        return alternateItems;
        
    }

}
