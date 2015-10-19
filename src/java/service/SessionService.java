package service;

import bean.SessionBean;
import dao.ExceptionDAO;
import dao.SessionDAO;
import java.sql.SQLException;

public class SessionService {
    
    // SERVICE 01 - newSession()
    //              Executa DAO responsável pelo início de uma nova session.
    public SessionBean newSession(SessionBean session) throws SQLException, ExceptionDAO {
        SessionBean currentSession = new SessionBean();
        SessionDAO sessionDAO = new SessionDAO();
        
        try {
            currentSession = sessionDAO.newSession(session);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][SESSIONSERVICE] ERRO: Não foi possível iniciar uma nova session!");
        }
        
        return currentSession;
    }
}
