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

        while (rs.next()) {
            currentSession.setStartTime(rs.getDate("STARTTIME"));
            currentSession.setStatus(rs.getBoolean("STATUS"));
            currentSession.setSession_id(rs.getInt("SESSIONID"));
            currentSession.setUser_id(rs.getInt("users_ID"));
        }

        rs.close();
        conn.close();

        System.out.println("[DATABASE][SESSIONDAO] Session iniciada com ID '" + session.getSession_id() + "' para usuário de ID '" + session.getUser_id()
                + "' em '" + session.getStartTime() + "'.");

        return currentSession;
    }

    // DAO 02 - checkForOpenSession()
    //          Verifica se existe session em aberto para o usuário informado.
    public boolean checkForOpenSession(int userId) throws SQLException {
        boolean checkStatus = false;
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        
        System.out.println("[DATABASE][SESSIONDAO] Verificando existência de session em aberto para o ID '" + userId + "' ...");
        
        String sql = "SELECT * FROM session WHERE users_ID = " + userId + " AND STATUS = false";
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            checkStatus = true;
            System.out.println("[DATABASE][SESSIONDAO] Encontrado sessiom em aberto para o ID '" + userId + "'.");
        }
        
        rs.close();
        ps.close();
        conn.close();
        
        return checkStatus;      
    }
    
    // DAO 03 - loadOpenSession()
    //          Carrega dados da session em aberto para o usuário informado.
    public SessionBean loadOpenSession(int userId) throws SQLException {
        SessionBean recoveredSession = new SessionBean();
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        
        System.out.println("[DATABASE][SESSIONDAO] Carregando dados da session em aberto para o ID '" + userId + "' ...");
        
        String sql = "SELECT * FROM session WHERE users_ID = " + userId + " AND STATUS = false";
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            recoveredSession.setUser_id(rs.getInt("users_ID"));
            recoveredSession.setStartTime(rs.getDate("STARTTIME"));
            recoveredSession.setStatus(rs.getBoolean("STATUS"));
            recoveredSession.setSession_id(rs.getInt("SESSIONID"));
            recoveredSession.setFlag(rs.getInt("FLAG"));
        }
        
        rs.close();
        ps.close();
        conn.close();  
        
        return recoveredSession;
    }
    
    // DAO 04 - closeSession()
    //          Encerra session para usuário informado.
    public void closeSession(int userId, int sessionId, Date endTime) throws SQLException {
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        
        System.out.println("[DATABASE][SESSIONDAO] Encerrando session '" + sessionId + "' do usuário de ID '" + userId + "' ...");
        
        String sql = "UPDATE session SET STATUS = true, ENDTIME = ? WHERE SESSIONID = " + sessionId + " AND users_ID = " + userId + " AND STATUS = false";
        PreparedStatement ps = conn.prepareStatement(sql);
        try {
            ps.setTimestamp(1, new java.sql.Timestamp(endTime.getTime()));
        } catch (NullPointerException e) {
            ps.setTimestamp(1, null);
        }
        ps.execute();
        ps.close();
        conn.close();
        
        System.out.println("[DATABASE][SESSIONDAO] Session '" + sessionId + "' encerrada.");
    }
    
}
