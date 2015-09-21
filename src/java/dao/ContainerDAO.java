package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bean.ContainerBean;
import db.sql.QueryBuilder;
import db.ConnectionBuilder;

public class ContainerDAO {

    // DAO 01 - loadContainerInfo()
    //          Carrega informações do container para exibição.
    public ContainerBean loadContainerInfo(String containerAlias) throws SQLException {
        ContainerBean containerInfo = new ContainerBean();
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        
        System.out.println("[DATABASE][CONTAINERDAO] Iniciando...");
        
        String sql = "SELECT * FROM container WHERE ALIAS = '" + containerAlias + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        System.out.println("[DATABASE][INVENTORYDAO] Carregando informações do container...");
        
        while (rs.next()) {            
            containerInfo.setAlias(rs.getString("ALIAS"));
            containerInfo.setColor(rs.getString("COLOR"));
            containerInfo.setFlag(rs.getInt("FLAG"));
            containerInfo.setFrom(rs.getString("FROM"));
            containerInfo.setFull_status(rs.getBoolean("FULL_STATUS"));
            containerInfo.setOldAlias(rs.getString("ALIAS"));
            containerInfo.setTo(rs.getString("TO"));
            containerInfo.setType(rs.getString("TYPE"));
        }
        
        System.out.println("[DATABASE][CONTAINERDAO] As informações foram carregadas com sucesso!");
        
        rs.close();
        ps.close();
        conn.close();
        
        return containerInfo;
    }

}
