package dao;

import bean.LogBean;
import db.ConnectionBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogDAO {

    ////////////////////////////////////////////////////////////////////////////
    // 01 - newLog()
    //      Adiciona um novo registro de atividade no banco de dados.
    public void newLog(LogBean log) throws SQLException {
        /* Inicia e configura conexão com banco de dados */
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DAO][LOG][newLog] Preparando SQL para inserir registro de atividade...");

        /* Prepara SQL e define dados do registro */
        String sql = "INSERT INTO log (TYPE,HEADER,DETAILS,TIME,session_SESSIONID,session_users_ID)"
                + " VALUES (?,?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, log.getType());
        ps.setString(2, log.getHeader());
        ps.setString(3, log.getDetails());
        try {
            ps.setTimestamp(4, new java.sql.Timestamp(log.getTime().getTime()));
        } catch (NullPointerException e) {
            ps.setTimestamp(4, null);
        }
        ps.setInt(5, log.getSession_id());
        ps.setInt(6, log.getUser_id());
        
        /* Executa SQL */
        ps.execute();
        
        /* Encerra SQL e conexão */
        ps.close();
        conn.close();

        System.out.println("[DAO][LOG][newLog] Registro de atividade inserido com sucesso no banco de dados!");
        ////////////////////////////////////////////////////////////////////////
    }

}
