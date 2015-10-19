package dao;

import bean.SessionBean;
import db.ConnectionBuilder;
import java.sql.Connection;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAO {

    // DAO 01 - newSession()
    //          Inicia nova session no banco de dados.
    public SessionBean newSession(SessionBean session) throws SQLException {
        SessionBean currentSession = new SessionBean();
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();

        System.out.println("[DATABASE][SESSIONDAO] Preparando para iniciar nova session...");
        
        /* Cria nova session no banco de dados */

        String sql = "INSERT INTO session (STATUS,STARTTIME,users_ID)"
                + " VALUES (?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);        
        ps.setBoolean(1, session.isStatus());       
        try {
            ps.setTimestamp(2, new java.sql.Timestamp(session.getStartTime().getTime()));
        } catch (NullPointerException e) {
            ps.setTimestamp(2, null);
        }
        ps.setInt(3, session.getUser_id());
        ps.execute();
        ps.close();
        
        /* Carrega dados da session criada */
        
        String sqlb = "SELECT * FROM session WHERE users_ID = ? AND STATUS = false";
        
        PreparedStatement psb = conn.prepareStatement(sqlb);
        psb.setInt(1, session.getUser_id());
        ResultSet rs = psb.executeQuery();
        
        while(rs.next()) {
            currentSession.setStartTime(rs.getDate("STARTTIME"));
            currentSession.setStatus(rs.getBoolean("STATUS"));
            currentSession.setSession_id(rs.getInt("SESSIONID"));
            currentSession.setUser_id(rs.getInt("users_ID"));
        }
        
        conn.close();
        
        System.out.println("[DATABASE][SESSIONDAO] Session iniciada com ID '" + session.getSession_id() + "' para usuário de ID '" + session.getUser_id() + 
                "' em '" + session.getStartTime() + "'.");   
        
        return currentSession;
    }
    
    // DAO 02 - checkForOpenSession()
    //          Verifica se existe session em aberto.
    

}
