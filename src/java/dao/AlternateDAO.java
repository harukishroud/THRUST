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

    // DAO 02 - checkAlternatePNExistance()
    //          Verifica a existência do alternado no banco de dados.
    public boolean checkAlternatePNExistance(AlternateBean altPNInfo) throws SQLException {
        boolean checkStatus = false;
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][ALTERNATEDAO] Verificando PN Alternado '" + altPNInfo.getAlt_pn() + "' "
                + "para PN '" + altPNInfo.getItems_pn() + "' no banco de dados...");

        String sql = "SELECT * FROM alternate WHERE ALT_PN = '" + altPNInfo.getAlt_pn() + "' "
                + "AND items_PN = '" + altPNInfo.getItems_pn() + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Caso o Alternado exista
        if (rs.next()) {
            System.out.println("[DATABASE][ALTERNATEDAO] O Alternado informado já existe.");
            checkStatus = true;
            // Se o Alternado informado não exista o valor de 'checkStatus' permanece como falso
        } else {
            System.out.println("[DATABASE][ALTERNATEDAO] O Alternado '" + altPNInfo.getAlt_pn() + "' para o PN '"
                    + altPNInfo.getItems_pn() + "' não existe no banco de dados.");
        }

        return checkStatus;
    }

    // DAO 03 - addAlternatePN()
    //          Adiciona PN alternado ao banco de dados.
    public void addAlternatePN(AlternateBean altPNInfo) throws SQLException {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][ALTERNATEDAO] Preparando para inserir novo PN Alternado no banco de dados...");
        System.out.println("[DATABASE][ALTERNATEDAO] Adicionando PN Alternado '" + altPNInfo.getAlt_pn() + "' para PN '" + altPNInfo.getItems_pn() + "'...");

        String sql = "INSERT INTO alternate(ALT_PN, items_PN) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        
        ps.setString(1, altPNInfo.getAlt_pn());
        ps.setString(2, altPNInfo.getItems_pn());
        ps.execute();
        ps.close();
        conn.close();
        
        System.out.println("[DATABASE][ALTERNATEDAO] PN Alternado '" + altPNInfo.getAlt_pn() + "' adicionado para o PN '" 
                + altPNInfo.getItems_pn() + "' no banco de dados com sucesso!");

    }

}
