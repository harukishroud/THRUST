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

    // DAO 01 - loadAlternatePNs()
    //          Carrega lista de alternados disponíveis para o PN selecionado.
    public List<AlternateBean> loadAlternatePNs(String PN) throws SQLException {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        List<AlternateBean> alternatePNs = new ArrayList<AlternateBean>();

        System.out.println("[DATABASE][ALTERNATEDAO] Iniciando busca de PNs alternados para '" + PN + "' ...");
        
        String sql = "SELECT * FROM pn_alternatelist WHERE items_PN = '" + PN + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            AlternateBean alternatePN = new AlternateBean();
            alternatePN.setAlt_pn(rs.getString("ALT_PN"));
            alternatePN.setItems_pn(rs.getString("items_PN"));
            alternatePNs.add(alternatePN);
        }
        
        rs.close();
        ps.close();
        conn.close();
        
        return alternatePNs;        
    }

}
