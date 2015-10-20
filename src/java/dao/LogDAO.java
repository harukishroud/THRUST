package dao;

import bean.LogBean;
import db.ConnectionBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogDAO {

    // DAO 01 - newLog()
    //          Adiciona um novo registro de atividade (log) no banco de dados.
    public void newLog(LogBean log) throws SQLException {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][LOGDAO] Preparando para registrar LOG...");

        String sql = "INSERT INTO log (TYPE,DETAILS,TIME,session_SESSIONID,session_users_ID)"
                + " VALUES (?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, log.getType());
        ps.setString(2, log.getDetails());
        try {
            ps.setTimestamp(3, new java.sql.Timestamp(log.getTime().getTime()));
        } catch (NullPointerException e) {
            ps.setTimestamp(3, null);
        }
        ps.setInt(4, log.getSession_id());
        ps.setInt(5, log.getUser_id());
        ps.execute();
        ps.close();
        conn.close();

        System.out.println("[DATABASE][LOGDAO] LOG registrado com sucesso!");
    }

}
