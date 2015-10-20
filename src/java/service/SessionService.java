package service;

import bean.SessionBean;
import dao.ExceptionDAO;
import dao.SessionDAO;
import java.sql.SQLException;
import java.util.Date;

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

    // SERVICE 02 - checkForOpenSession()
    //              Executa DAO responsável por chegar a existência de uma sess-
    //              ion em aberto para o usuário informado.
    public boolean checkForOpenSession(int userId) throws SQLException, ExceptionDAO {
        boolean checkStatus = false;
        SessionDAO sessionDAO = new SessionDAO();

        try {
            checkStatus = sessionDAO.checkForOpenSession(userId);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][SESSIONSERVICE] ERRO: Não foi possíve"
                    + "l iniciar verifica a existência de session em aberto!");
        }
        
        return checkStatus;
    }
    
    // SERVICE 03 - loadOpenSession()
    //              Executa DAO responsável por carregar dados da session em ab-
    //              erto para o usuário informado.
    public SessionBean loadOpenSession(int userId) throws SQLException, ExceptionDAO {
        SessionBean recoveredSession = new SessionBean();
        SessionDAO sessionDAO = new SessionDAO();

        try {
            recoveredSession = sessionDAO.loadOpenSession(userId);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][SESSIONSERVICE] ERRO: Não foi possíve"
                    + "l carregar os dados da session em aberto para o usuário informado!");
        }
        
        return recoveredSession;
    }
    
    // SERVICE 04 - closedSession()
    //              Executa DAO responsável por encerrar a session em aberto pa-
    //              ra o usuário informado.
    public void closeSession(int userId, int sessionId, Date endTime) throws SQLException, ExceptionDAO {
        SessionDAO sessionDAO = new SessionDAO();
        try {
            sessionDAO.closeSession(userId, sessionId, endTime);
        } catch (SQLException ex) {
            System.out.println("[DATABASE][SESSIONSERVICE] ERRO: Não foi possíve"
                    + "l encerrar a session do usuário informado!");
        }        
    }
    
    
}
